package com.example.bhcbbackend.api.rest.mapper;

import com.example.bhcbbackend.entities.SocialLink;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SocialLinkMapper
{
    default ArrayList<String> asDto(List<SocialLink> models)
    {
        var result = new ArrayList<String>(models.size());

        for (var model : models)
        {
            result.add(model.getUrl());
        }

        return result;
    }

    default ArrayList<SocialLink> asModels(List<String> dtos)
    {
        var result = new ArrayList<SocialLink>(dtos.size());

        for (var dto : dtos)
        {
            var link = new SocialLink();
            link.setUrl(dto);

            result.add(link);
        }

        return result;
    }
}
