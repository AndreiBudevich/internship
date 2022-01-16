package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;


@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/rest/players")
    public ResponseEntity<List<Player>> getAllPlayer(){

        List<Player> players = playerService.listAll();
        return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
    }



}



