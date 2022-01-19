package com.game.service;


import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.*;

@Service
@Transactional
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;


    public List<Player> listAll() {

        return playerRepository.findAll();
    }

    public List<Player> listAllsort(Pageable pageable) {

        return playerRepository.findAll((Sort) pageable);
    }




    public Boolean existsById(Long id) {

        return playerRepository.existsById(id);
    }

    public Player getPlayerById(Long id) {

        return playerRepository.findById(id).get();
    }

    public Player savePlayer (Player player) {

        playerRepository.save(player);
        return player;
    }

    public Integer findPlayerCount() {

        return playerRepository.findAll().size();
    }




    public void delete(Long id) {

        playerRepository.deleteById(id);
    }


}
