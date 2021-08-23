package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

    Player findByFirstNameAndLastName(String firstName, String lastName);

    List<Player> findAllByTeam(Team team);

    List<Player> findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal salary);
}
