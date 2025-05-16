package com.example.bhcbbackend.api.rest.facade;

import com.example.bhcbbackend.api.rest.dtos.TeamDto;

public interface TeamFacade
{
    TeamDto getById(String id);
}
