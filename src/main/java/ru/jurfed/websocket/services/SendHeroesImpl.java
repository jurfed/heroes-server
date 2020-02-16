package ru.jurfed.websocket.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.jurfed.websocket.domain.Hero;
import ru.jurfed.websocket.domain.WayPoint;
import ru.jurfed.websocket.repositories.HeroRepository;

import java.io.IOException;
import java.util.List;

/**
 * Class for sending wayPoints to the client
 */
public class SendHeroesImpl implements SendHeroes {

    private static final Logger logger = LogManager.getLogger(SendHeroesImpl.class);

    /**
     * Hero repository
     */
    private HeroRepository heroRepository;

    /**
     * Web Socket Session
     */
    private WebSocketSession session;

    public SendHeroesImpl(WebSocketSession session, HeroRepository heroRepository) {
        this.session = session;
        this.heroRepository = heroRepository;
        logger.info(startMovingHeroes());
    }

    /**
     * Method for calculating the time of sending messages to the client
     */
    public String startMovingHeroes() {
        String message = "messages was generated";
        List<Hero> heroes = this.heroRepository.findAll();
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            List<WayPoint> wayPoints = hero.getWayPoints();

            for (int j = 0; j < wayPoints.size(); j++) {
                WayPoint wayPoint = wayPoints.get(j);
                hero.setCurrentPosition(wayPoint);
                if (session.isOpen()) {
                    SendWayPoint sendWayPoint = new SendWayPoint(1000 * j, hero.toString());
                    Thread run = new Thread(sendWayPoint);
                    run.start();
                } else {
                    message = "session was closed";
                    break;
                }

            }
            if (!session.isOpen()) {
                message = "session was closed";
                break;
            }
        }
        return message;
    }


    /**
     * Method for sending next way point on the client
     *
     * @param heroAndHiswayPoint
     */
    public synchronized void move(String heroAndHiswayPoint) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(heroAndHiswayPoint));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A class that calls a method for sending messages at a specific time
     */
    class SendWayPoint implements Runnable {

        private int seconsd;
        private String heroAndHiswayPoint;

        public SendWayPoint(int seconsd, String heroAndHiswayPoint) {
            this.seconsd = seconsd;
            this.heroAndHiswayPoint = heroAndHiswayPoint;
        }

        @Override
        public synchronized void run() {
            try {
                Thread.sleep(seconsd); //1000 - 1 с
                move(heroAndHiswayPoint);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
