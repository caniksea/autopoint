package com.caniksea.poll.spandigital.autopoint;

import com.caniksea.poll.spandigital.autopoint.entity.PlayerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private String fileName, playerData;
    private App app;

    @BeforeEach void setup() {
        this.fileName = "input.txt";
        this.playerData = "Township 8";
        this.app = new App();
    }

    @Test void getPlayerScore() {
        int score = this.app.getPlayerScore(this.playerData);
        assertNotNull(score);
        assertEquals(8, score);
    }

    @Test void getPlayerName() {
        String name = this.app.getPlayerName(this.playerData);
        assertNotNull(name);
        assertEquals("Township", name);
    }

    @Test void getPlayerData() {
        PlayerData playerData = this.app.getPlayerData(this.playerData);
        assertAll(
                () -> assertNotNull(playerData),
                () -> assertEquals("Township", playerData.getName()),
                () -> assertEquals(8, playerData.getScore())
        );
    }

    @Test void testStart() {
        this.app.start(this.fileName);
    }
}