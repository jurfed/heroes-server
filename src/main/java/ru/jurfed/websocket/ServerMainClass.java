package ru.jurfed.websocket;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.jurfed.websocket.services.InsertDataFromJsonToDb;


import javax.annotation.PostConstruct;

/**
 * Main class
 */
@EnableJpaRepositories
@SpringBootApplication
public class ServerMainClass {

    public static void main(String[] args) {
        SpringApplication.run(ServerMainClass.class, args);
    }
	private static final Logger logger = LogManager.getLogger(ServerMainClass.class);

	/**
	 * After launching the application, we fill the database with the data of heroes
	 */
	@PostConstruct
    void insertData() {
		try {
			logger.info("The server is deployed");
			insertDataFromJsonToDb.insertIntoDatabase();
			logger.info("The database is filled with data");

		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	/**
	 * Instance of Service for filling database
	 */
	@Autowired
	InsertDataFromJsonToDb insertDataFromJsonToDb;

}

