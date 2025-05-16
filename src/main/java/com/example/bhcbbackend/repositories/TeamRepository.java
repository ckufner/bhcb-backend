package com.example.bhcbbackend.repositories;

import com.example.bhcbbackend.entities.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends SlicedJpaRepository<Team, Long>
{
}
