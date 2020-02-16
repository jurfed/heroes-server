package ru.jurfed.websocket.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity for table hero
 */
@Entity(name = "Hero")
@Table(name = "hero")
public class Hero {

    public Hero() {
    }

    @Id
    @Expose(serialize = true, deserialize = true)
    @Column(name = "name")
    private String name;

    @Expose(serialize = true, deserialize = true)
    @Column(name = "house")
    private String house;


    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = false, fetch = FetchType.EAGER
    )
    @JoinColumn(name = "waypoint_hero", referencedColumnName = "name")
    private List<WayPoint> wayPoints = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public List<WayPoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<WayPoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    @Transient
    @Expose(serialize = true, deserialize = true)
    WayPoint wayPoint;

    public void setCurrentPosition(WayPoint currentPosition) {
        wayPoint = currentPosition;

    }

    @Override
    public String toString() {
        return "{\n" +
                "   \"hero\": \"" + name + "\",\n" +
                "   \"house\": \"" + house + "\",\n" +
                "   \"x\": \"" + wayPoint.getX() + "\",\n" +
                "   \"y\": \"" + wayPoint.getY() + "\"\n" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return Objects.equals(name, hero.name) &&
                Objects.equals(house, hero.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, house);
    }
}
