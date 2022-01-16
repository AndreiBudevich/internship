package com.game.service;

import com.game.entity.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerService extends CrudRepository<Player, Long> {

    List<Player> findAll () ;
}
