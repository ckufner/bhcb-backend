package com.example.bhcbbackend.api.rest.mapper;

import com.example.bhcbbackend.entities.Skill;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Set;

@Mapper
public interface SkillMapper
{
    default ArrayList<String> asDto(Set<Skill> models)
    {
        var result = new ArrayList<String>(models.size());

        for (var model : models)
        {
            result.add(model.getName());
        }

        result.sort(String::compareTo);

        return result;
    }
}
