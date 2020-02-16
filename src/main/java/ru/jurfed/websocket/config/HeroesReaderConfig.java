package ru.jurfed.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Class for reading heroes parameters from application.yml:
 * - resources folder
 * - os separator
 * - heroes json - files
 */
@Configuration
@ConfigurationProperties("spring")
public class HeroesReaderConfig {

    /**
     * List of files with hero's names in json format
     */
    private List<String> heroes;

    /**
     * The directory where the files with the names of heroes are located
     * By default, the directory is specified as "heroes"
     */
    private String resourcesDirectory;

    /**
     * The separator used in the correct operating system
     * By default, the separator is set for windows "/"
     */
    private String osSeparator;

    public List<String> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<String> heroes) {
        this.heroes = heroes;
    }

    public String getResourcesDirectory() {
        return resourcesDirectory;
    }

    public void setResourcesDirectory(String resourcesDirectory) {
        this.resourcesDirectory = resourcesDirectory;
    }

    public String getOsSeparator() {
        return osSeparator;
    }

    public void setOsSeparator(String osSeparator) {
        this.osSeparator = osSeparator;
    }
}
