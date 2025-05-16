package com.example.bhcbbackend.repositories;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SlicedJpaRepository<T, ID> extends BaseJpaRepository<T, ID>
{
    Slice<T> getSliced(
            @NonNull Pageable pageable
    );
}
