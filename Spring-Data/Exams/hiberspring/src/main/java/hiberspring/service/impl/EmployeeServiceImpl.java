package hiberspring.service.impl;

import hiberspring.domain.dtos.EmployeeSeedRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEES_FILE_PATH = "src/main/resources/files/employees.xml";


    private final EmployeeRepository employeeRepository;
    private final EmployeeCardService employeeCardService;
    private final BranchService branchService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCardService employeeCardService, BranchService branchService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.employeeCardService = employeeCardService;
        this.branchService = branchService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser.parseXml(EmployeeSeedRootDto.class, EMPLOYEES_FILE_PATH)
                .getEmployees()
                .stream()
                .filter(employeeSeedDto -> {

                    boolean isValid = validationUtil.isValid(employeeSeedDto);
                            if(this.employeeCardService.findCardByNumber(employeeSeedDto.getCard()) != null){

                                sb
                                        .append(isValid
                                                ? String.format("Successfully imported Employee %s %s."
                                                ,employeeSeedDto.getFirstName(),employeeSeedDto.getLastName())
                                                : "Invalid data.")
                                        .append(System.lineSeparator());
                            }


                    return isValid;

                })
                .map(employeeSeedDto -> {
                    Employee employee = modelMapper.map(employeeSeedDto,Employee.class);
                    Branch branch = this.branchService.findBranchByName(employeeSeedDto.getBranch());
                    EmployeeCard employeeCard = this.employeeCardService.findCardByNumber(employeeSeedDto.getCard());
                    employee.setCard(employeeCard);
                    employee.setBranch(branch);

                    return employee;
                })
                .forEach(employeeRepository::save);


        return sb.toString();
    }


    @Override
    public String exportProductiveEmployees() {


        return this.employeeRepository.findAllByWithMoreThanOneProduct()
                .stream()
                .map(employee -> {

                    return String.format("%nName: %s %s\n" +
                            "Position: %s\n" +
                            "Card Number: %s\n",employee.getFirstName(),employee.getLastName(),
                            employee.getPosition(),employee.getCard().getNumber());

                }).collect(Collectors.joining("--------------"));
    }

}
