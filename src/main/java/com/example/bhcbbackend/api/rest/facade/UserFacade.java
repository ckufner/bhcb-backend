package com.example.bhcbbackend.api.rest.facade;

import com.example.bhcbbackend.api.rest.dtos.UserDto;
import com.example.bhcbbackend.api.rest.dtos.UserSliceDto;
import com.example.bhcbbackend.api.rest.parameters.PagingRequestParameter;

import java.util.List;

public interface UserFacade
{
    UserDto getById(String id);

    UserSliceDto getUsers(String query, PagingRequestParameter pagingRequestParameter);

    List<UserDto> getByTeamId(String id);
}
