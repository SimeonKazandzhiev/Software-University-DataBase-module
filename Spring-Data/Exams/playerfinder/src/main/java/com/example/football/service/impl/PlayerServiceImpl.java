package com.example.football.service.impl;

import com.example.football.models.dto.PlayerExportDto;
import com.example.football.models.dto.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final StatService statService;
    private final TownService townService;
    private final TeamService teamService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PlayerServiceImpl(PlayerRepository playerRepository, StatService statService, TownService townService, TeamService teamService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.playerRepository = playerRepository;
        this.statService = statService;
        this.townService = townService;
        this.teamService = teamService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

         xmlParser
                .fromFile(PLAYERS_FILE_PATH,PlayerSeedRootDto.class)
                 .getPlayers()
                 .stream()
                 .filter(playerSeedDto -> {
                     boolean isValid = validationUtil.isValid(playerSeedDto)
                              && !playerRepository.existsByEmail(playerSeedDto.getEmail());



                     sb.append(isValid
                     ? String.format("Successfully imported Player %s %s - %s",
                             playerSeedDto.getFirstName(),playerSeedDto.getLastName(), playerSeedDto.getPosition())
                             : "Invalid player")
                             .append(System.lineSeparator());

                     return isValid;
                 })
                 .map(playerSeedDto -> {
                     Player player = modelMapper.map(playerSeedDto,Player.class);
                     Stat stat = this.statService.getStatById(playerSeedDto.getStat().getId());
                     Town town = this.townService.findTownByName(playerSeedDto.getTeam().getName());
                     Team team = this.teamService.findByName(playerSeedDto.getTeam().getName());

                     player.setStat(stat);
                     player.setTown(town);
                     player.setTeam(team);

                     return player;
                 })
                 .forEach(playerRepository::save);


        return sb.toString();
    }


    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();

        List<Player> players = playerRepository
                .exportTheBestPlayers(LocalDate.of(1995, 1, 1),
                        LocalDate.of(2003, 1, 1));

        Arrays.stream(modelMapper.map(players, PlayerExportDto[].class))
                .forEach(playerExportDto -> sb.append(playerExportDto.toString()).append(System.lineSeparator()));

        return sb.toString();
    }

    @Override
    public boolean existWithEmail(String email) {
        return this.playerRepository.existsByEmail(email);
    }
}
