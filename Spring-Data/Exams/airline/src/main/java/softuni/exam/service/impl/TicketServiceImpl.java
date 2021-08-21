package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TicketSeedRootDto;
import softuni.exam.models.entities.Ticket;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TicketServiceImpl implements TicketService {

    private static final String TICKETS_FILE_PATH = "src/main/resources/files/xml/tickets.xml";

    private final TicketRepository ticketRepository;
    private final TownService townService;
    private final PlaneService planeService;
    private final PassengerService passengerService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public TicketServiceImpl(TicketRepository ticketRepository, TownService townService, PlaneService planeService, PassengerService passengerService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.ticketRepository = ticketRepository;
        this.townService = townService;
        this.planeService = planeService;
        this.passengerService = passengerService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {

       StringBuilder sb = new StringBuilder();


       xmlParser.fromFile(TICKETS_FILE_PATH, TicketSeedRootDto.class)
               .getTickets()
               .stream()
               .filter(ticketSeedDto -> {
                   boolean isValid = validationUtil.isValid(ticketSeedDto)
                           && !isEntityExist(ticketSeedDto.getSerialNumber());

                   sb.append(isValid
                           ? String.format("Successfully imported Ticket %s - %s",
                           ticketSeedDto.getFromTown(),ticketSeedDto.getToTown())
                           : "Invalid ticket")
                           .append(System.lineSeparator());

                   return isValid;
               })
               .map(ticketSeedDto -> {
                   Ticket ticket = modelMapper.map(ticketSeedDto, Ticket.class);
                   ticket.setFromTown(townService.findTownByName(ticketSeedDto.getFromTown().getName()));
                   ticket.setToTown(townService.findTownByName(ticketSeedDto.getToTown().getName()));
                   ticket.setPassenger(passengerService.findPassengerByEmail(ticketSeedDto.getPassenger().getEmail()));
                   ticket.setPlane(planeService.findPlaneByRegNumber(ticketSeedDto.getPlane().getRegisterNumber()));

                   return ticket;
               })
               .forEach(ticketRepository::save);

        return sb.toString();
    }

    private boolean isEntityExist(String registerNumber) {
        return this.ticketRepository.existsBySerialNumber(registerNumber);
    }
}
