package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;



import javax.validation.constraints.NotBlank;

public class EmployeeCardSeedDto {

    @Expose
    private String number;

    public EmployeeCardSeedDto() {
    }

    @NotBlank
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
