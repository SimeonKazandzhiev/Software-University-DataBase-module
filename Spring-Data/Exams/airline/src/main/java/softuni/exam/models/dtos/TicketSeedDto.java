package softuni.exam.models.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {

    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeoff;
    @XmlElement(name = "from-town")
    private TicketFromTownDto fromTown;
    @XmlElement(name = "to-town")
    private TicketToTownDto toTown;
    @XmlElement(name = "passenger")
    private PassengerEmailDto passenger;
    @XmlElement(name = "plane")
    private PlaneRegNumberDto plane;


    @Size(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }

    @NotNull
    public PassengerEmailDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEmailDto passenger) {
        this.passenger = passenger;
    }
    @NotNull
    public PlaneRegNumberDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneRegNumberDto plane) {
        this.plane = plane;
    }


    public TicketToTownDto getToTown() {
        return toTown;
    }

    public void setToTown(TicketToTownDto toTown) {
        this.toTown = toTown;
    }


    public TicketFromTownDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(TicketFromTownDto fromTown) {
        this.fromTown = fromTown;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
