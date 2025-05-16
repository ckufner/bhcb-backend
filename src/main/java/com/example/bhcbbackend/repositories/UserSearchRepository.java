package com.example.bhcbbackend.repositories;

import com.example.bhcbbackend.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface UserSearchRepository
{
    Slice<User> search(String query, Pageable pageable);
}
