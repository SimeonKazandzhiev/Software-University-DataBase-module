package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class TeamSeedDto {

    @Expose
    private String name;
    @Expose
    private String stadiumName;
    @Expose
    private Integer fanBase;
    @Expose
    private String history;
    @Expose
    private String town;

    public TeamSeedDto() {
    }

    @Size(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 3)
    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    @Min(1000)
    public Integer getFanBase() {
        return fanBase;
    }

    public void setFanBase(Integer fanBase) {
        this.fanBase = fanBase;
    }

    @Size(min = 10)
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }


    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
