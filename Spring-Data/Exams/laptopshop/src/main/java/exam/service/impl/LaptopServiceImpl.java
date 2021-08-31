package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.LaptopSeedDto;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class LaptopServiceImpl implements LaptopService {

    private static final String LAPTOPS_FILE_PATH = "src/main/resources/files/json/laptops.json";

    private final LaptopRepository laptopRepository;
    private final ShopService shopService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopService shopService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.laptopRepository = laptopRepository;
        this.shopService = shopService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOPS_FILE_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readLaptopsFileContent(), LaptopSeedDto[].class))
                .filter(laptopSeedDto -> {
                    boolean isValid = validationUtil.isValid(laptopSeedDto)
                            && !this.laptopRepository.existsByMacAddress(laptopSeedDto.getMacAddress());

                    sb
                            .append(isValid
                                    ? String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                    laptopSeedDto.getMacAddress(), laptopSeedDto.getCpuSpeed(), laptopSeedDto.getRam(),
                                    laptopSeedDto.getStorage())
                                    : "Invalid Laptop")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(laptopSeedDto -> {
                    Laptop laptop = modelMapper.map(laptopSeedDto, Laptop.class);
                    Shop shop = this.shopService.getShopByName(laptopSeedDto.getShop().getName());
                    laptop.setShop(shop);

                    return laptop;
                })
                .forEach(laptopRepository::save);

        return sb.toString();
    }

    @Override
    public String exportBestLaptops() {
        StringBuilder sb = new StringBuilder();

        this.laptopRepository.findBestLaptops()
                .forEach(laptop -> {

                    sb.append(String.format("Laptop - %s\n" +
                                    "*Cpu speed - %.2f\n" +
                                    "**Ram - %d\n" +
                                    "***Storage - %d\n" +
                                    "****Price - %s\n" +
                                    "#Shop name - %s\n" +
                                    "##Town - %s", laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(),
                            laptop.getStorage(), laptop.getPrice(), laptop.getShop().getName(),
                            laptop.getShop().getTown().getName()))
                            .append(System.lineSeparator());

                });

        return sb.toString();
    }
}
