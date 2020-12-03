package com.bol.mancala.player;

import com.bol.mancala.error.exception.PlayerNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository repository;
    @InjectMocks
    private PlayerService service;

    @Test
    void testCreatePlayer() {
        service.createPlayer();
        Mockito.verify(repository).save(any());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Player player = new Player();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(player));

        Assertions.assertEquals(service.findById(id), player);
    }

    @Test
    void testFindByIdShouldReturnNotFoundException() {
        Assertions.assertThrows(PlayerNotFoundException.class, () -> {
            service.findById(UUID.randomUUID());
        });
    }

    @Test
    void testDeletePlayer() {
        UUID id = UUID.randomUUID();
        Player player = new Player();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(player));
        service.deleteById(id);

        Mockito.verify(repository).delete(player);
    }
}
