package com.example.bhcbbackend.api.rest.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Schema(name = "User", description = "User")
@Data
public final class UserDto implements Serializable
{
    @Schema(description = "Id of user")
    private String id;

    @Schema(description = "E-Mail of user")
    private String email;

    @Schema(description = "Visibility of user user")
    private boolean visible;

    @Schema(description = "Name of user")
    private String name;

    @Schema(name = "Team", description = "Team")
    private TeamDto team;

    @Schema(description = "Description of user")
    private String description;

    @Schema(description = "ImageURL of user")
    private String imageUrl;

    @Schema(description = "Skills of user")
    private ArrayList<String> skills;

    @Schema(description = "Social links of user")
    private ArrayList<String> socialLinks;
}
