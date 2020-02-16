package ru.jurfed.websocket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.jurfed.websocket.domain.Hero;

import java.util.List;
import java.util.Optional;

/**
 * repository for managing hero
 */
@Repository("hero")
public interface HeroRepository extends JpaRepository<Hero, Integer> {

    List<Hero> findAll();

    Optional<Hero> findByName(String name);

}
