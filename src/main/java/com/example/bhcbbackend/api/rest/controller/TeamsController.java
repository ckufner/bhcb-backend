package com.example.bhcbbackend.api.rest.controller;

import com.example.bhcbbackend.api.rest.facade.TeamFacade;
import com.example.bhcbbackend.api.rest.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Teams")
//TODO: add security
//@SecurityRequirement(name = "basic-auth")
@RequestMapping(value = "/api/teams")
@RequiredArgsConstructor
class TeamsController
{
    private final TeamFacade teamFacade;
    private final UserFacade userFacade;

    @GetMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    @Operation(summary = "get team by id")
    public ResponseEntity<Object> getById(
            @PathVariable String id
    )
    {
        return ResponseEntity.ok().body(this.teamFacade.getById(id));
    }

    @GetMapping(
            value = "/{id}/users",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    @Operation(summary = "get users by team id")
    public ResponseEntity<Object> getUsersByTeamId(
            @PathVariable String id
    )
    {
        return ResponseEntity.ok().body(this.userFacade.getByTeamId(id));
    }
}
