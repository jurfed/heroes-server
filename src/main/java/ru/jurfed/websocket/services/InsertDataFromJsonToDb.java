package ru.jurfed.websocket.services;

/**
 * Interface for filling DB after launching application
 */
public interface InsertDataFromJsonToDb {
    void insertIntoDatabase() throws Throwable;
}
