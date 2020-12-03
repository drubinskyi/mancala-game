package com.bol.mancala.game;

import com.bol.mancala.game.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface GameRepository extends CrudRepository<Game, UUID> {
}
