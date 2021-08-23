package softuni.exam.service;

import softuni.exam.domain.entities.Team;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface TeamService {

    String importTeams() throws JAXBException, FileNotFoundException;

    boolean areImported();

    String readTeamsXmlFile() throws IOException;

    boolean findTeamByName(String name);

    Team getTeamByName(String name);


}
