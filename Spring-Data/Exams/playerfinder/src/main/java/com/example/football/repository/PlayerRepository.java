package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

    boolean existsByEmail(String email);

    @Query("select p from Player p " +
            "where p.birthDate > :lower and p.birthDate < :upper " +
            " order by p.stat.shooting desc, p.stat.passing desc, p.stat.endurance desc," +
            "p.lastName ")
    List<Player> exportTheBestPlayers(@Param(value = "lower") LocalDate lower, @Param(value = "upper") LocalDate upper);
}
