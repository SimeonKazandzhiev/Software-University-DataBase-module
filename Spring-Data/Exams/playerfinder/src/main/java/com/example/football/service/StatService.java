package com.example.football.service;


import com.example.football.models.entity.Stat;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface StatService {
    boolean areImported();

    String readStatsFileContent() throws IOException;

    String importStats() throws JAXBException, FileNotFoundException;

    boolean findStatById(Long id);

    Stat getStatById(Long id);

}
