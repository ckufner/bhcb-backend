package com.example.bhcbbackend.repositories;

import com.example.bhcbbackend.entities.SocialLink;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLinkRepository extends SlicedJpaRepository<SocialLink, Long>
{
}
