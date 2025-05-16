package com.example.bhcbbackend.api.rest.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(name = "Team", description = "Team")
@Data
public final class TeamDto implements Serializable
{
    @Schema(description = "Id of team")
    private String id;

    @Schema(description = "Name of team")
    private String name;
}
