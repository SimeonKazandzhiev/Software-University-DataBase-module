package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PlayerSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYERS_FILE_PATH = "src/main/resources/files/json/players.json";

    private final PlayerRepository playerRepository;
    private final PictureService pictureService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    public PlayerServiceImpl(PlayerRepository playerRepository, PictureService pictureService, TeamService teamService, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.pictureService = pictureService;
        this.teamService = teamService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }


    @Override
    public String importPlayers() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readPlayersJsonFile(), PlayerSeedDto[].class))
                .filter(playerSeedDto -> {
                    boolean isValid = validatorUtil.isValid(playerSeedDto)
                            && pictureService.existByUrl(playerSeedDto.getPicture().getUrl());

                    sb
                            .append(isValid
                                    ? String.format("Successfully imported player: %s %s",
                                    playerSeedDto.getFirstName(), playerSeedDto.getLastName())
                                    : "Invalid player")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    Team team = this.teamService.getTeamByName(playerSeedDto.getTeam().getName());
                    Picture picture = this.pictureService.findPictureByUrl(playerSeedDto.getPicture().getUrl());

                    player.setPicture(picture);
                    player.setTeam(team);
                    return player;
                })
                .forEach(playerRepository::save);

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .forEach(player -> {
                    sb
                            .append(String.format("Player name: %s %s \n" +
                                            "\tNumber: %d\n" +
                                            "\tSalary: %s\n" +
                                            "\tTeam: %s", player.getFirstName(), player.getLastName(),
                                    player.getNumber(), player.getSalary(), player.getTeam().getName()))
                            .append(System.lineSeparator());

                });

        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();

        this.playerRepository.findAllByTeam(teamService.getTeamByName("North Hub"))
                .forEach(player -> {
                    sb.append(String.format("Team: %s\n" +
                                    "\tPlayer name: %s %s - %s\n" +
                                    "\tNumber: %d\n", player.getTeam().getName(), player.getFirstName(), player.getLastName(),
                            player.getPosition(), player.getNumber()
                    ))
                            .append(System.lineSeparator());

                });

        return sb.toString();
    }
}
