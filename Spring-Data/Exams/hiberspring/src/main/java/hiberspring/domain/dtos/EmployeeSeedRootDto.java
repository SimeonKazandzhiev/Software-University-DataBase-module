package hiberspring.domain.dtos;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeSeedRootDto {

    @XmlElement(name = "employee")
    private List<EmployeeSeedDto> employees;


    public List<EmployeeSeedDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeSeedDto> employees) {
        this.employees = employees;
    }
}
