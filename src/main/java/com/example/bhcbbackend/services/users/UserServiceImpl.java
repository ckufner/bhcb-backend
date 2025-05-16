package com.example.bhcbbackend.services.users;

import com.example.bhcbbackend.entities.User;
import com.example.bhcbbackend.exceptions.NotFoundException;
import com.example.bhcbbackend.repositories.TeamRepository;
import com.example.bhcbbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public User getById(final long id)
    {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("could not find user"));
    }

    @Override
    public Slice<User> getUsers(final String query, final Pageable pageable)
    {
        if (StringUtils.hasText(query))
        {
            return this.userRepository.search(query, pageable);
        }

        return userRepository.getSliced(pageable);
    }

    @Override
    public List<User> getByTeamId(final long id)
    {
        if (!this.teamRepository.existsById(id))
        {
            throw new NotFoundException("could not find team");
        }

        return this.userRepository.getByTeamId(id);
    }
}
