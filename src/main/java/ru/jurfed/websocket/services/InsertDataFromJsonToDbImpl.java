package ru.jurfed.websocket.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jurfed.websocket.ServerMainClass;
import ru.jurfed.websocket.config.HeroesReaderConfig;
import ru.jurfed.websocket.domain.Hero;
import ru.jurfed.websocket.repositories.HeroRepository;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Service for filling DB after launching application
 */
@Service
public class InsertDataFromJsonToDbImpl implements InsertDataFromJsonToDb {

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private HeroesReaderConfig heroesConfig;

    private List<String> heroesList;

    private String heroData;

    private static final Logger logger = LogManager.getLogger(ServerMainClass.class);

    /**
     * Method for reading heroes resources (Arya_Stark.json, Bran_Stark.json, ...) and putting them into db
     */
    public void insertIntoDatabase() {
        String resourcesDirectory = heroesConfig.getResourcesDirectory();
        String osSeparator = heroesConfig.getOsSeparator();

        heroesList = heroesConfig.getHeroes();
        heroesList.forEach(heroFile -> {
            Optional<InputStream> currentHeroOptional = Optional.ofNullable(getClass().getClassLoader().getResourceAsStream(resourcesDirectory + osSeparator + heroFile));
            if(currentHeroOptional.isPresent()){
                Scanner scanner = new Scanner(currentHeroOptional.get());
                heroData = new String();
                while (scanner.hasNextLine()) {
                    heroData += scanner.nextLine();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Hero hero = gson.fromJson(heroData, Hero.class);
                heroRepository.saveAndFlush(hero);
                logger.info(heroFile + " Data is recorded in the database");
            }else{
                logger.warn(heroFile + " file not found");
            }
        });
    }

}
