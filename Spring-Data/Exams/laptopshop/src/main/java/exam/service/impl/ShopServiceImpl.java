package exam.service.impl;

import exam.model.dtos.ShopSeedRootDto;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.service.ShopService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ShopServiceImpl implements ShopService {

    private static final String SHOPS_FILE_PATH = "src/main/resources/files/xml/shops.xml";

    private final ShopRepository shopRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public ShopServiceImpl(ShopRepository shopRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.shopRepository = shopRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOPS_FILE_PATH));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(SHOPS_FILE_PATH,ShopSeedRootDto.class)
                .getShops()
                .stream()
                .filter(shopSeedDto -> {
                    boolean isValid = validationUtil.isValid(shopSeedDto)
                            && !this.shopRepository.existsByName(shopSeedDto.getName());

                    sb
                            .append(isValid
                            ? String.format("Successfully imported Shop %s - %s",shopSeedDto.getName(), shopSeedDto.getIncome())
                                    : "Invalid Shop")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(shopSeedDto -> {
                    Shop shop = modelMapper.map(shopSeedDto,Shop.class);
                    Town town = this.townService.findTownByName(shopSeedDto.getTown().getName());
                    shop.setTown(town);

                    return shop;
                })
                .forEach(shopRepository::save);

        return sb.toString();
    }

    @Override
    public Shop getShopByName(String name) {

        return this.shopRepository.findShopByName(name).orElse(null);
    }
}
