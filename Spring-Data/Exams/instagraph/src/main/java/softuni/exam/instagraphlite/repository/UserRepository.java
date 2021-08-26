package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT distinct u FROM User u JOIN FETCH u.posts p ORDER BY SIZE(p) DESC, u.id ")
    List<User> findAllByCountPostsOrderByCountDescThenByUserId();

}
