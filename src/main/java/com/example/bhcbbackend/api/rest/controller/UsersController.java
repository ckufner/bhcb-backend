package com.example.bhcbbackend.api.rest.controller;

import com.example.bhcbbackend.api.rest.facade.UserFacade;
import com.example.bhcbbackend.api.rest.parameters.PagingRequestParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Users")
//TODO: add security
//@SecurityRequirement(name = "basic-auth")
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
class UsersController
{
    private final UserFacade userFacade;

    @GetMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    @Operation(summary = "get user by id")
    public ResponseEntity<Object> getById(
            @PathVariable String id
    )
    {
        return ResponseEntity.ok().body(this.userFacade.getById(id));
    }

    @GetMapping(
            value = "/",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    @Operation(summary = "search users")
    public ResponseEntity<Object> getUsers(
            @RequestParam String query,
            PagingRequestParameter pagingRequestParameter
    )
    {
        return ResponseEntity.ok().body(this.userFacade.getUsers(query, pagingRequestParameter));
    }
}
