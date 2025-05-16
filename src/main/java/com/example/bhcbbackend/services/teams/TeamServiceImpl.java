package com.example.bhcbbackend.services.teams;

import com.example.bhcbbackend.entities.Team;
import com.example.bhcbbackend.exceptions.NotFoundException;
import com.example.bhcbbackend.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
class TeamServiceImpl implements TeamService
{
    private final TeamRepository repository;

    @Override
    public Team getById(final long id)
    {
        return this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("could not find team"));
    }
}
