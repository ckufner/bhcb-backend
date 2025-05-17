package com.example.bhcbbackend.repositories;

import com.example.bhcbbackend.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends SlicedJpaRepository<User, Long>, UserSearchRepository
{
    List<User> getByTeamId(long id);

    Slice<User> getByVisible(boolean visible, Pageable pageable);
}
