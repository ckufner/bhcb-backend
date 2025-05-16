package com.example.bhcbbackend.api.rest.facade;

import com.example.bhcbbackend.api.rest.dtos.UserDto;
import com.example.bhcbbackend.api.rest.dtos.UserSliceDto;
import com.example.bhcbbackend.api.rest.mapper.UserMapper;
import com.example.bhcbbackend.api.rest.parameters.PagingRequestParameter;
import com.example.bhcbbackend.services.users.UserService;
import com.example.bhcbbackend.services.util.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserFacadeImpl implements UserFacade
{
    private final TransactionService transactionService;
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public UserDto getById(final String id)
    {
        return this.transactionService.read(() ->
        {
            return this.userMapper.asDto(
                    this.userService.getById(Long.valueOf(id))
            );
        });
    }

    @Override
    public UserSliceDto getUsers(final String query, final PagingRequestParameter pagingRequestParameter)
    {
        final var pageable = pagingRequestParameter.asPageable();

        return this.transactionService.read(() ->
        {
            return this.userMapper.asDto(
                    this.userService.getUsers(query, pageable)
            );
        });
    }

    @Override
    public List<UserDto> getByTeamId(final String id)
    {
        return this.transactionService.read(() ->
        {
            return this.userMapper.asDto(
                    this.userService.getByTeamId(Long.valueOf(id))
            );
        });
    }
}
