package com.example.bhcbbackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(
        name = "Team"
)
@Table(
        name = "teams"
)
@Indexed
public class Team
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "id",
            updatable = false,
            nullable = false
    )
    @Getter
    @Setter(value = AccessLevel.PACKAGE)
    private Long id;

    @FullTextField
    @NaturalId
    @Getter
    @Setter
    private String name;

    @OneToMany(mappedBy = "team")
    @Getter
    @Setter(value = AccessLevel.PACKAGE)
    private Set<User> users = new HashSet<>();

    public Team addUser(User user)
    {
        this.users.add(user);
        user.setTeam(this);

        return this;
    }

    public Team removeUser(User user)
    {
        this.users.remove(user);
        user.setTeam(null);

        return this;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }
}
