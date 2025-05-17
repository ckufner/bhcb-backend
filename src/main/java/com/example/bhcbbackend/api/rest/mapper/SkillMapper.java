package com.example.bhcbbackend.api.rest.mapper;

import com.example.bhcbbackend.entities.Skill;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    default HashSet<Skill> asModels(List<String> dtos)
    {
        var result = new HashSet<Skill>(dtos.size());

        for (var dto : dtos)
        {
            var skill = new Skill();
            skill.setName(dto);

            result.add(skill);
        }

        return result;
    }
}
