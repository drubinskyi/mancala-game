package com.bol.mancala.player;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.UUID;

@Getter
@KeySpace("players")
public class Player {
    @Id
    private UUID id;

    public Player() {
        id = UUID.randomUUID();
    }
}
