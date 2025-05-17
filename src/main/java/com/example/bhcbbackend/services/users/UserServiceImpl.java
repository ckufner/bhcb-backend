package com.example.bhcbbackend.services.users;

import com.example.bhcbbackend.entities.Skill;
import com.example.bhcbbackend.entities.Team;
import com.example.bhcbbackend.entities.User;
import com.example.bhcbbackend.exceptions.NotFoundException;
import com.example.bhcbbackend.repositories.SkillRepository;
import com.example.bhcbbackend.repositories.TeamRepository;
import com.example.bhcbbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final SkillRepository skillRepository;

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

        return userRepository.getByVisible(true, pageable);
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

    @Override
    public User update(final long id, final User updateModel)
    {
        var existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("could not find user"));

        existingUser.setEmail(updateModel.getEmail());
        existingUser.setVisible(updateModel.getVisible());
        existingUser.setName(updateModel.getName());
        existingUser.setDescription(updateModel.getDescription());
        existingUser.setImageUrl(updateModel.getImageUrl());

        if (updateModel.getTeam() == null)
        {
            existingUser.getTeam().removeUser(existingUser);
        }
        else
        {
            var team = this.teamRepository.findByName(updateModel.getTeam().getName());
            if (team == null)
            {
                team = new Team();
                team.setName(updateModel.getTeam().getName());
                team.addUser(existingUser);
                this.teamRepository.persist(team);
            }
            else
            {
                team.addUser(existingUser);
                this.teamRepository.update(team);
            }
        }

        if (updateModel.getSocialLinks() == null || updateModel.getSocialLinks().isEmpty())
        {
            existingUser.removeAllSocialLinks();
        }
        else
        {
            existingUser.replaceSocialLinks(updateModel.getSocialLinks());
        }

        existingUser.removeAllSkills();
        if (updateModel.getSkills() != null || !updateModel.getSkills().isEmpty())
        {
            var skillsFromUpdateModel = updateModel.getSkills().stream().collect(Collectors.toMap(Skill::getName, Function.identity()));
            var existingSkills = this.skillRepository.findAllByNameIn(skillsFromUpdateModel.keySet());

            var skillsForUpdate = new ArrayList<Skill>();
            var skillsForPersist = new ArrayList<Skill>();

            for (var existingSkill : existingSkills)
            {
                existingUser.addSkill(existingSkill);
                skillsFromUpdateModel.remove(existingSkill.getName());

                skillsForUpdate.add(existingSkill);
            }

            for (var newSkill : skillsFromUpdateModel.values())
            {
                existingUser.addSkill(newSkill);

                skillsForPersist.add(newSkill);
            }

            if (!skillsForUpdate.isEmpty())
            {
                this.skillRepository.updateAll(skillsForUpdate);
            }

            if (!skillsForPersist.isEmpty())
            {
                this.skillRepository.persistAll(skillsForPersist);
            }
        }

        return this.userRepository.update(existingUser);
    }
}
