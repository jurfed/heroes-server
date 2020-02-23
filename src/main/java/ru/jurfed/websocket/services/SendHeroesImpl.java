package ru.jurfed.websocket.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.jurfed.websocket.domain.Hero;
import ru.jurfed.websocket.domain.WayPoint;
import ru.jurfed.websocket.repositories.HeroRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            List<WayPoint> wayPoints = hero.getWayPoints();

            for (int j = 0; j < wayPoints.size(); j++) {
                WayPoint wayPoint = wayPoints.get(j);
                hero.setCurrentPosition(wayPoint);
                if (session.isOpen()) {
                    service.schedule(new MoveThread(hero.toString()), 1 * j, TimeUnit.SECONDS);
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
     * class that calls a method for sending messages at a specific time
     */
    class MoveThread implements Runnable{

        private String heroAndHiswayPoint;

        public MoveThread(String heroAndHiswayPoint) {
            this.heroAndHiswayPoint = heroAndHiswayPoint;
        }

        @Override
        public void run() {
            move(heroAndHiswayPoint);
        }
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


}
