package com.example.bhcbbackend.api.rest.mapper;

import com.example.bhcbbackend.api.rest.dtos.UserDto;
import com.example.bhcbbackend.api.rest.dtos.UserSliceDto;
import com.example.bhcbbackend.entities.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {
        TeamMapper.class,
        SkillMapper.class,
        SocialLinkMapper.class
})
public interface UserMapper
{
    UserDto asDto(User model);

    ArrayList<UserDto> asDto(List<User> models);

    default UserSliceDto asDto(Slice<User> models)
    {
        final var dto = new UserSliceDto();
        SliceDtoUtil.map(models, dto);
        dto.setItems(asDto(models.getContent()));

        return dto;
    }
}
