package com.caniksea.poll.spandigital.autopoint;

import com.caniksea.poll.spandigital.autopoint.entity.PlayerData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caniksea
 *
 * Calculates ranking given games.
 * Game data is provided in a file and content must follow format:
 *  Lions 3, Snakes 3
 *  Tarantulas 1, FC Awesome 0
 *  Lions 0, FC Awesome 0
 *  Tarantulas 3, Snakes 1
 *  Lions 4, Grouches 0
 *
 * File must be in the resources folder.
 */
public class App {

    private Map<String, Integer> pointMap;

    /**
     * Initialize instance variable(s)
     */
    private void initialize() {
        this.pointMap = new TreeMap<>();
    }

    /**
     * Given filename, return Path
     * @param fileName
     * @return
     * @throws URISyntaxException
     */
    private Path getFilePath(String fileName) throws URISyntaxException {
        return Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
    }

    /**
     * Process data in line
     * @param line
     */
    private void processLine(String line) {
        String data[] = line.split(",");
        if (data.length < 2 || data.length > 2)
            throw new RuntimeException("Invalid game entry!");
        String playerOne = data[0];
        String playerTwo = data[1];
        PlayerData playerOneData = getPlayerData(playerOne);
        PlayerData playerTwoData = getPlayerData(playerTwo);
        int playerOnePoints = 0, playerTwoPoints = 0;
        if (playerOneData.getScore() > playerTwoData.getScore()) {
            playerOnePoints = 3;
        } else if (playerOneData.getScore() == playerTwoData.getScore()) {
            playerOnePoints = playerTwoPoints = 1;
        } else {
            playerTwoPoints = 3;
        }
        updatePoints(playerOneData, playerOnePoints);
        updatePoints(playerTwoData, playerTwoPoints);
    }

    /**
     * Update points for player
     * @param playerData
     * @param points
     */
    private void updatePoints(PlayerData playerData, int points) {
        int oldPoints = this.pointMap.getOrDefault(playerData.getName(), 0);
        if (points >= oldPoints) this.pointMap.put(playerData.getName(), points + oldPoints);
    }

    /**
     * Sort map and display output.
     */
    private void displayResult() {
        AtomicInteger count = new AtomicInteger();
        this.pointMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(record -> System.out.println(String.format("%d. %s, %d pts", count.incrementAndGet(), record.getKey(), record.getValue())));
    }

    /**
     * Start/entry point
     * @param fileName
     */
    public void start(String fileName) {
        if (StringUtils.isEmpty(fileName)) throw new RuntimeException("No filename provided!");
        initialize();
        try {
            Path path = getFilePath(fileName);
            Files.lines(path).forEach(line -> processLine(line));
            displayResult();
        } catch (URISyntaxException | IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get player score given data string
     * @param playerData
     * @return
     */
    public int getPlayerScore(String playerData) {
        String score = StringUtils.substringAfterLast(playerData, " ");
        if (NumberUtils.isParsable(score))
            return Integer.parseInt(score);
        throw new NumberFormatException("Invalid score!");
    }

    /**
     * Get player name given data string
     * @param playerData
     * @return
     */
    public String getPlayerName(String playerData) {
        return StringUtils.chop(playerData).trim();
    }

    /**
     * build player data object from string.
     * @param player
     * @return
     */
    public PlayerData getPlayerData(String player) {
        String playerName = getPlayerName(player);
        int score = getPlayerScore(player);
        return new PlayerData(playerName, score);
    }
}
