package com.example.bhcbbackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity(
        name = "SocialLink"
)
@Table(
        name = "social_links"
)
public class SocialLink
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

    @Column(length = 2048)
    @Getter
    @Setter
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
    @Getter
    @Setter
    private User user;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof SocialLink))
        {
            return false;
        }
        return id != null && id.equals(((SocialLink) o).getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
