package com.example.bhcbbackend.configurations.jpa;


import com.example.bhcbbackend.repositories.SlicedJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        value = "com.example.bhcbbackend.repositories",
        repositoryBaseClass = SlicedJpaRepositoryImpl.class
)
class JpaConfiguration
{
}
