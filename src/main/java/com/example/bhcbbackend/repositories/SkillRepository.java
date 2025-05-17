package com.example.bhcbbackend.repositories;

import com.example.bhcbbackend.entities.Skill;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SkillRepository extends SlicedJpaRepository<Skill, Long>
{
    List<Skill> findAllByNameIn(Collection<String> names);
}
