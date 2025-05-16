package com.example.bhcbbackend.repositories;

import com.example.bhcbbackend.entities.Skill;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends SlicedJpaRepository<Skill, Long>
{
}
