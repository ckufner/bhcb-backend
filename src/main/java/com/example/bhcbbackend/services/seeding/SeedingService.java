package com.example.bhcbbackend.services.seeding;

import com.example.bhcbbackend.entities.Skill;
import com.example.bhcbbackend.entities.SocialLink;
import com.example.bhcbbackend.entities.Team;
import com.example.bhcbbackend.entities.User;
import com.example.bhcbbackend.repositories.TeamRepository;
import com.example.bhcbbackend.repositories.UserRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class SeedingService implements ApplicationListener<ContextRefreshedEvent>
{
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        if(userRepository.count() > 0){
            log.info("users already seeded");
            return;
        }

        try
        {
            var file = ResourceUtils.getFile("classpath:seed.csv");
            List<CsvLine> lines;
            try (var bufferedReader = new BufferedReader(new FileReader(file)))
            {
                lines = new CsvToBeanBuilder(bufferedReader).withType(CsvLine.class).withSkipLines(1).build().parse();
            }

            var teams = new HashMap<String, Team>();
            var users = new HashMap<String, User>();
            var skills = new HashMap<String, Skill>();

            for (var line : lines)
            {
                var user = new User();
                user.setEmail(line.getEmail());
                users.put(user.getEmail(), user);

                user.setName(line.getName());
                user.setDescription(line.getDescription());

                for (var social : line.getSocial())
                {
                    var socialLink = new SocialLink();
                    socialLink.setUrl(social);
                    ;

                    user.addSocialLink(socialLink);
                }

                Team team;
                if (teams.containsKey(line.getTeam()))
                {
                    team = teams.get(line.getTeam());
                }
                else
                {
                    team = new Team();
                    team.setName(line.getTeam());

                    teams.put(team.getName(), team);
                }

                team.addUser(user);

                for (var skillCandidate : line.getSkills())
                {
                    Skill skill;
                    if (skills.containsKey(skillCandidate))
                    {
                        skill = skills.get(skillCandidate);
                    }
                    else
                    {
                        skill = new Skill();
                        skill.setName(skillCandidate);
                        skills.put(skill.getName(), skill);
                    }

                    user.addSkill(skill);
                }
            }

            if(!teams.isEmpty()){
                teamRepository.persistAll(teams.values());
            }

            userRepository.persistAll(users.values());

        }
        catch (FileNotFoundException e)
        {
            log.warn("seeding file not found");
        }
        catch (IOException ex)
        {
            log.error("{}", ex.getMessage(), ex);
        }
    }
}
