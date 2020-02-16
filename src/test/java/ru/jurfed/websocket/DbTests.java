package ru.jurfed.websocket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.jurfed.websocket.domain.Hero;
import ru.jurfed.websocket.domain.WayPoint;
import ru.jurfed.websocket.repositories.HeroRepository;
import ru.jurfed.websocket.services.InsertDataFromJsonToDb;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Tests for reading data from a database
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DbTests {


    @Autowired(required = true)
    private HeroRepository heroRepository;

    @Autowired(required = true)
    InsertDataFromJsonToDb insertDataFromJsonToDb;

    /**
     * Insert all data into db
     */
    @Before
    public void init() {
        try {
            insertDataFromJsonToDb.insertIntoDatabase();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * read house
     */
    @Test
    public void getHouse() {
        Hero hero = heroRepository.findByName("Margaery Tyrell").get();
        String heroesName = hero.getHouse();
        assertTrue(heroesName.equals("Tyrell"));
    }

    /**
     * read Heroes size
     */
    @Test
    public void getAllHeroes() {
        List<Hero> heroes = heroRepository.findAll();
        assertTrue(heroes.size() == 19);
    }

    /**
     * read wayPoints size for Oberyn
     */
    @Test
    public void getWayPoints() {
        Hero hero = heroRepository.findByName("Oberyn Martell").get();
        List<WayPoint> wayPoints = hero.getWayPoints();
        assertTrue(wayPoints.size() == 6);
    }

}

