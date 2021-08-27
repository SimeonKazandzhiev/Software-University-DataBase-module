package com.example.football.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDto {

    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    private String position;
    private PlayerTownNameDto town;
    private PlayerTeamNameDto team;
    private PlayerStatIdDto stat;

    public PlayerSeedDto() {
    }

    public PlayerSeedDto(String firstName, String lastName, String email, String birthDate, String position, PlayerTownNameDto town, PlayerTeamNameDto team, PlayerStatIdDto stat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.position = position;
        this.town = town;
        this.team = team;
        this.stat = stat;
    }

    @Size(min = 2)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Size(min = 2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPosition() {
        return position;
    }


    public PlayerTownNameDto getTown() {
        return town;
    }

    public void setTown(PlayerTownNameDto town) {
        this.town = town;
    }

    public PlayerTeamNameDto getTeam() {
        return team;
    }

    public void setTeam(PlayerTeamNameDto team) {
        this.team = team;
    }

    public PlayerStatIdDto getStat() {
        return stat;
    }

    public void setStat(PlayerStatIdDto stat) {
        this.stat = stat;
    }
}
