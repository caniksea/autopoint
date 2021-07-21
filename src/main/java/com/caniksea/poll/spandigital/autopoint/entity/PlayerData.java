package com.caniksea.poll.spandigital.autopoint.entity;

/**
 * POJO to hold player data.
 */
public class PlayerData {

    private String name;
    private int score;

    public PlayerData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
