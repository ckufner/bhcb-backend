package com.example.bhcbbackend.api.rest.mapper;

import com.example.bhcbbackend.api.rest.dtos.TeamDto;
import com.example.bhcbbackend.entities.Team;
import org.mapstruct.Mapper;

@Mapper
public interface TeamMapper
{
    TeamDto asDto(Team model);

    Team asModel(TeamDto dto);
}
