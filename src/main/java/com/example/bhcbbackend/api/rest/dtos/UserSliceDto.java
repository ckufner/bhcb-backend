package com.example.bhcbbackend.api.rest.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(name = "UsersSlice", description = "A slice of users")
@Data
@EqualsAndHashCode(callSuper = false)
public final class UserSliceDto extends SliceDto<UserDto>
{
}
