package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAMS_FILE_PATH = "src/main/resources/files/xml/teams.xml";


    private final TeamRepository teamRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;

    public TeamServiceImpl(TeamRepository teamRepository, PictureService pictureService, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

         xmlParser.fromFile(TEAMS_FILE_PATH,TeamSeedRootDto.class)
                 .getTeams()
                 .stream()
                 .filter(teamSeedDto -> {
                     boolean isValid = validatorUtil.isValid(teamSeedDto)
                             && isPictureExist(teamSeedDto.getPicture().getUrl());

                     sb
                             .append(isValid
                                     ? String.format("Successfully imported - %s",teamSeedDto.getName())
                                     : "Invalid team")
                             .append(System.lineSeparator());

                    return isValid;
                 })
                 .map(teamSeedDto -> {
                     Team team = modelMapper.map(teamSeedDto, Team.class);
                     team.setPicture(pictureService.findPictureByUrl(teamSeedDto.getPicture().getUrl()));
                     return team;
                 })
                 .forEach(teamRepository::save);


        return sb.toString();
    }

    private boolean isPictureExist(String pictureByUrl) {
        return this.pictureService.existByUrl(pictureByUrl);
    }

    @Override
    public boolean areImported() {

        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {

        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public boolean findTeamByName(String name) {
        return this.teamRepository.findByName(name);
    }

    @Override
    public Team getTeamByName(String name) {
        return this.teamRepository.findTeamByName(name).orElse(null);
    }
}
