package com.game.service;


import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return (List<Player>) playerRepository.findAll();
    }

    public Long maxId() {
        List<Player> playerList = playerRepository.findAll();
        Long maxId = 0L;
        for (Player player : playerList) {
            if (player.getId() > maxId) maxId = player.getId();
        }
        return maxId;
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


    public void save(Player player) {
        playerRepository.save(player);
    }

    public Player findPlayer(Player player) {
        Player playernew = null;
        for (Player playerBD : playerRepository.findAll()) {
            if (playerBD.equals(player)) playernew = playerBD;
        }
        return playernew;
    }

    public void saveAndFlush(Player player) {
        playerRepository.saveAndFlush(player);
    }


    public void delete(Long id) {
        playerRepository.deleteById(id);
    }


}
