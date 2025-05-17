package com.example.bhcbbackend.repositories;

import com.example.bhcbbackend.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
class UserSearchRepositoryImpl implements UserSearchRepository
{
    @NonNull
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    @Override
    public Slice<User> search(final String query, final Pageable pageable)
    {

        var searchSession = Search.session(this.entityManager);

        var searchResult = searchSession.search(User.class)
                .where(f ->
                        f.bool()
                                .must(f.match().fields("name", "team.name", "skills.name").matching(query))
                                .mustNot(f.match().field("visible").matching(Boolean.FALSE))
                )
                .sort(f -> f.field("id").asc())
                .fetch(
                        pageable.getPageNumber() * pageable.getPageSize(),
                        pageable.getPageSize() + 1
                );

        var resultList = searchResult.hits();
        var pageSize = pageable.isPaged() ? pageable.getPageSize() : 0;
        var hasNext = pageable.isPaged() && resultList.size() > pageSize;

        return new SliceImpl<>(
                hasNext ? resultList.subList(0, pageSize) : resultList,
                pageable,
                hasNext
        );
    }
}
