package com.example.automappingexercise.models.dtos;


import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;


public class GameAddDto {

    private String title;
    private BigDecimal price;
    private Double size;
    private String trailer;
    private String imageThumbnail;
    private String description;
    private LocalDate releaseDate;

    public GameAddDto() {
    }

    public GameAddDto(String title, BigDecimal price, Double size, String trailer, String imageThumbnail, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.imageThumbnail = imageThumbnail;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Pattern(regexp = "[A-Z][a-z]{6,100}",message = "The title is incorrect")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
//    @Min(0)
    @DecimalMin(value = "0",message = "The price is incorrect")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
//   @Min(0)
    @Positive
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
    @Size(min = 11,max = 11,message = "The size is incorrect")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    @Pattern(regexp = "(https?).+",message = "The thumbnail is incorrect")
    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String thumbnail) {
        this.imageThumbnail = thumbnail;
    }
    @Size(min = 20,message = "The description is incorrect")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
