package com.example.bhcbbackend.services.users;

import com.example.bhcbbackend.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserService
{
    User getById(long id);

    Slice<User> getUsers(String query, Pageable pageable);

    List<User> getByTeamId(long id);
}
