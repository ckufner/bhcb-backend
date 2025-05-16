package com.example.bhcbbackend.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity(
        name = "User"
)
@Table(
        name = "users"
)
@Indexed
public class User
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

    @NaturalId
    @Getter
    @Setter
    private String email;

    @FullTextField
    @Getter
    @Setter
    private String name;

    @FullTextField
    @Getter
    @Setter
    private String description;

    @Column(length = 2048)
    @Getter
    @Setter
    private String imageUrl;

    @IndexedEmbedded
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "fk_team_id")
    @Getter
    @Setter
    private Team team;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @Getter
    @Setter(value = AccessLevel.PACKAGE)
    private List<SocialLink> socialLinks = new ArrayList<>();

    @IndexedEmbedded
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "users_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    @Getter
    @Setter(value = AccessLevel.PACKAGE)
    private Set<Skill> skills = new HashSet<>();

    public User addSocialLink(SocialLink socialLink)
    {
        this.socialLinks.add(socialLink);
        socialLink.setUser(this);

        return this;
    }

    public User removeSocialLink(SocialLink socialLink)
    {
        this.socialLinks.remove(socialLink);
        socialLink.setUser(null);

        return this;
    }

    public User addSkill(Skill skill)
    {
        this.skills.add(skill);
        skill.getUsers().add(this);

        return this;
    }

    public User removeSkill(Skill skill)
    {
        this.skills.remove(skill);
        skill.getUsers().remove(this);

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
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(email);
    }
}
