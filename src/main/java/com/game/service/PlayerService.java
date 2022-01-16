package com.game.service;


import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Service
@Transactional
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> listAll() {
        return (List<Player>) playerRepository.findAll();
    }

    public void save(Player player) {
        playerRepository.save(player);
    }

    public void delete(Long id) {
        playerRepository.deleteById(id);
    }




}
