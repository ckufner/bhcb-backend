package com.example.bhcbbackend.repositories;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

public class SlicedJpaRepositoryImpl<T, ID> extends BaseJpaRepositoryImpl<T, ID> implements SlicedJpaRepository<T, ID>
{
    @NonNull
    @PersistenceContext
    private final EntityManager entityManager;

    public SlicedJpaRepositoryImpl(final JpaEntityInformation<T, ?> entityInformation, final EntityManager entityManager)
    {
        super(entityInformation, entityManager);

        this.entityManager = entityManager;
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    @Override
    public Slice<T> getSliced(@NonNull final Pageable pageable)
    {
        final var criteriaBuilder = this.entityManager.getCriteriaBuilder();
        final var criteriaQuery = criteriaBuilder.createQuery(this.getDomainClass());

        final var root = criteriaQuery.from(this.getDomainClass());

        return doGetSliced(
                this.entityManager,
                criteriaQuery,
                pageable,
                () -> QueryUtils.toOrders(
                        pageable.getSort(),
                        root,
                        criteriaBuilder
                )
        );
    }

    private static <E> Slice<E> doGetSliced(
            @NonNull final EntityManager entityManager,
            @NonNull final CriteriaQuery<E> criteriaQuery,
            @NonNull final Pageable pageable,
            final Supplier<List<Order>> orderSupplier
    )
    {
        if (pageable.isPaged() && orderSupplier != null)
        {
            criteriaQuery.orderBy(orderSupplier.get());
        }

        final var typedQuery = entityManager.createQuery(criteriaQuery);

        var pageSize = 0;
        if (pageable.isPaged())
        {
            pageSize = pageable.getPageSize();

            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageSize + 1);
        }

        final var resultList = typedQuery.getResultList();

        var hasNext = pageable.isPaged() && resultList.size() > pageSize;

        return new SliceImpl<>(
                hasNext ? resultList.subList(0, pageSize) : resultList,
                pageable,
                hasNext
        );
    }
}
