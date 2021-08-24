package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.BranchSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class BranchServiceImpl implements BranchService {

    private static final String BRANCHES_FILE_PATH = "src/main/resources/files/branches.json";

    private final BranchRepository branchRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public BranchServiceImpl(BranchRepository branchRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.branchRepository = branchRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(BRANCHES_FILE_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readBranchesJsonFile(), BranchSeedDto[].class))
                .filter(branchSeedDto -> {
                    boolean isValid = validationUtil.isValid(branchSeedDto);

                    sb.append(isValid
                            ? String.format("Successfully imported Branch %s.", branchSeedDto.getName())
                            : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(branchSeedDto -> {
                    Branch branch = modelMapper.map(branchSeedDto,Branch.class);
                    branch.setTown(townService.findTownByName(branchSeedDto.getTown()));
                    return branch;
                })
               .forEach(branchRepository::save);

        return sb.toString();
    }

    @Override
    public Branch findBranchByName(String branchName) {
        return this.branchRepository.findByName(branchName).orElse(null);
    }

    @Override
    public boolean isBranchExist(String name) {
        return this.branchRepository.existsByName(name);
    }
}
