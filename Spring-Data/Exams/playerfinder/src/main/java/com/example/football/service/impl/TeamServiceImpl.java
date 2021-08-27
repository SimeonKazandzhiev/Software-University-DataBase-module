package com.example.football.service.impl;

import com.example.football.models.dto.TeamSeedDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAMS_FILE_PATH = "src/main/resources/files/json/teams.json";

    private final TeamRepository teamRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TeamServiceImpl(TeamRepository teamRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.teamRepository = teamRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readTeamsFileContent(), TeamSeedDto[].class))
                .filter(team -> {
                    boolean valid = validationUtil.isValid(team) && !isTeamExist(team.getName());

                    sb.append(valid ? String.format("Successfully imported Team %s - %d",
                            team.getName(),team.getFanBase())
                            : "Invalid Team")
                            .append(System.lineSeparator());

                    return valid;
                })
                .map(teamSeedDto -> {
                    Team mappedTeam = modelMapper.map(teamSeedDto, Team.class);
                    Town town = this.townService.findTownByName(teamSeedDto.getTown());
                    mappedTeam.setTown(town);
                    return mappedTeam;
                })
                .forEach(teamRepository::save);


        return sb.toString();
    }

    @Override
    public Team findByName(String name) {
        return this.teamRepository.findTeamByName(name);
    }

    private boolean isTownExist(String townName) {
       return this.townService.isTownExist(townName);
    }

    private boolean isTeamExist(String name) {
        return this.teamRepository.existsByName(name);
    }
}
