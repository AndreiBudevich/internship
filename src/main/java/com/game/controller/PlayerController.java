package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;


import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/rest")
public class PlayerController {
    @Autowired
    private PlayerService playerService;


    @GetMapping(path = "/players")
    public List<Player> getAllPlayers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false) PlayerOrder order,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        pageSize = 3;
        List<Player> players = playerService.listAll();

        return players;
    }





    @DeleteMapping({"/players/{id}"})
    public ResponseEntity delete(@PathVariable(value = "id", required = false) Long id) {
        ResponseEntity responseEntity;
        if (id <= 0) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        else if (!playerService.existsById(id)) responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        else {
            playerService.delete(id);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }
}

















