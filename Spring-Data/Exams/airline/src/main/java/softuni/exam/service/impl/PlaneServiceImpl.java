package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PlaneSeedRootDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlaneServiceImpl implements PlaneService {

    private static final String PLANES_FILE_PATH = "src/main/resources/files/xml/planes.xml";

    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANES_FILE_PATH));
    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(PLANES_FILE_PATH, PlaneSeedRootDto.class)
                .getPlanes()
                .stream()
                .filter(planeSeedDto -> {
                    boolean isValid = validationUtil.isValid(planeSeedDto);

                    sb.append(isValid
                            ? String.format("Successfully imported Plane %s", planeSeedDto.getRegisterNumber())
                            : "Invalid Plane")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(planeSeedDto -> modelMapper.map(planeSeedDto, Plane.class))
                .forEach(planeRepository::save);

        return sb.toString();
    }

    @Override
    public Plane findPlaneByRegNumber(String registerNumber) {
        return planeRepository.findPlaneByRegisterNumber(registerNumber).orElse(null);
    }
}
