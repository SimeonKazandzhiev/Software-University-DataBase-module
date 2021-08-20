package com.example.automappingexercise.services;

import com.example.automappingexercise.models.dtos.GameAddDto;
import com.example.automappingexercise.models.entities.Game;
import com.example.automappingexercise.repositories.GameRepository;
import com.example.automappingexercise.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper mapper, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void addGame(GameAddDto gameAddDto) {

        Set<ConstraintViolation<GameAddDto>> violations =
                validationUtil.violations(gameAddDto);

        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Game game = mapper.map(gameAddDto, Game.class);

        gameRepository.save(game);
        System.out.println("Added game " + gameAddDto.getTitle());

    }

    @Override
    @Modifying
    @Transactional
    public void deleteGame(long parseLong) {
        Game game = this.gameRepository.findGameById(parseLong);

        if (game == null) {
            System.out.println("Game doesn't exist");
            return;
        }

        this.gameRepository.delete(game);
        System.out.println("Deleted " + game.getTitle());
    }

    @Override
    @Modifying
    @Transactional
    public void editGame(Long gameId, BigDecimal price, Double size) {
        Game game = gameRepository.findById(gameId).orElse(null);

        if (game == null) {
            System.out.println("Id is not exist");
            return;
        }

        game.setPrice(price);
        game.setSize(size);

        gameRepository.save(game);
        System.out.println("Edited " + game.getTitle());
    }
}
