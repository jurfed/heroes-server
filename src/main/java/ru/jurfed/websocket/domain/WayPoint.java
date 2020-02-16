package ru.jurfed.websocket.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity for table waypoint
 */
@Entity(name = "Waypoint")
@Table(name = "waypoint")
public class WayPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private int id;

    @Expose(serialize = true, deserialize = true)
    @Column(name = "x")
    private float x;

    @Expose(serialize = true, deserialize = true)
    @Column(name = "y")
    private float y;

    @Expose(serialize = true, deserialize = true)
    @Column(name = "velocity")
    private int velocity;

    @Expose(serialize = true, deserialize = true)
    @Column(name = "delaymillis")
    private int delayMillis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayPoint wayPoint = (WayPoint) o;
        return id == wayPoint.id &&
                Float.compare(wayPoint.x, x) == 0 &&
                Float.compare(wayPoint.y, y) == 0 &&
                velocity == wayPoint.velocity &&
                delayMillis == wayPoint.delayMillis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, velocity, delayMillis);
    }
}
