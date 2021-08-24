package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.EmployeeCardSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private static final String EMPLOYEE_CARDS_FILE_PATH = "src/main/resources/files/employee-cards.json";

    private final EmployeeCardRepository employeeCardRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_CARDS_FILE_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();


                Arrays.stream(gson.fromJson(readEmployeeCardsJsonFile(),EmployeeCardSeedDto[].class))
                        .filter(employeeCardSeedDto -> {
                            boolean isValid = validationUtil.isValid(employeeCardSeedDto)
                                    && !isCardExist(employeeCardSeedDto.getNumber());

                            sb
                                    .append(isValid
                                            ? String.format("Successfully imported Employee Card %s.",employeeCardSeedDto.getNumber())
                                            : "Error: Invalid data.")
                                    .append(System.lineSeparator());

                            return isValid;
                        })
                        .map(employeeCardSeedDto -> modelMapper.map(employeeCardSeedDto, EmployeeCard.class))
                        .forEach(employeeCardRepository::save);

        return sb.toString();
    }
    @Override
    public boolean isCardExist(String number) {
      return  this.employeeCardRepository.existsByNumber(number);
    }

    @Override
    public EmployeeCard findCardByNumber(String number) {
        return this.employeeCardRepository.findByNumber(number);
    }


}
