package com.example.bhcbbackend.api.rest.facade;

import com.example.bhcbbackend.api.rest.dtos.TeamDto;
import com.example.bhcbbackend.api.rest.mapper.TeamMapper;
import com.example.bhcbbackend.services.teams.TeamService;
import com.example.bhcbbackend.services.util.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TeamFacadeImpl implements TeamFacade
{
    private final TransactionService transactionService;
    private final TeamMapper teamMapper;
    private final TeamService teamService;

    @Override
    public TeamDto getById(final String id)
    {
        return this.transactionService.read(() ->
        {
            return this.teamMapper.asDto(
                    this.teamService.getById(Long.valueOf(id))
            );
        });
    }
}
