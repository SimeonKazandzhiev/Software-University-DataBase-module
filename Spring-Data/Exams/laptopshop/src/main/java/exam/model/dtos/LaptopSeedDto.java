package exam.model.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class LaptopSeedDto {

    @Expose
    private String macAddress;
    @Expose
    private double cpuSpeed;
    @Expose
    private Integer ram;
    @Expose
    private Integer storage;
    @Expose
    private String description;
    @Expose
    private BigDecimal price;
    @Expose
    private String warrantyType;
    @Expose
    private ShopNameDto shop;

    public LaptopSeedDto() {
    }

    @Size(min = 9)
    @NotBlank
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    @Positive
    @NotNull
    public double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }
    @Min(8)
    @Max(128)
    @NotNull
    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }
    @Min(128)
    @Max(1024)
    @NotNull
    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }
    @Size(min = 10)
    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Positive
    @NotNull
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @NotBlank
    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopNameDto getShop() {
        return shop;
    }

    public void setShop(ShopNameDto shop) {
        this.shop = shop;
    }
}
