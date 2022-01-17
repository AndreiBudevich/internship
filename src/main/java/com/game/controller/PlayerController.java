package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;


import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;


    @GetMapping(path = "/rest/players")
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
        List<Player> players = playerService.listAll();

        return players;
    }

    @GetMapping(path = "/rest/players/count")
    public String getAll(
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
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageSize == null) pageSize = 3;
        if (pageNumber == null) pageNumber = 0;
        if (order == null) order = PlayerOrder.ID;

        List<Player> playerList = playerService.listAllsort((Pageable) PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()))).stream()
                        .filter(player -> name == null || player.getName().contains(name))
                        .filter(player -> title == null || player.getTitle().contains(title))
                        .filter(player -> race == null || player.getRace().equals(race))
                        .filter(player -> profession == null || player.getProfession().equals(profession))
                        .filter(player -> after == null || player.getBirthday().getTime() > after)
                        .filter(player -> before == null || player.getBirthday().getTime() < before)
                        .filter(player -> banned == null || player.getBanned().equals(banned))
                        .filter(player -> minExperience == null || player.getExperience() >= minExperience)
                        .filter(player -> maxExperience == null || player.getExperience() <= maxExperience)
                        .filter(player -> minLevel == null || player.getLevel() >= minLevel)
                        .filter(player -> maxLevel == null || player.getLevel() <= maxLevel)
                        .collect(Collectors.toList());
        return "redirect:/";
    }


    @PostMapping("/rest/players")
    public ResponseEntity сreatePlayer(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "title", required = false) String title,
                                       @RequestParam(value = "race", required = false) Race race,
                                       @RequestParam(value = "profession", required = false) Profession profession,
                                       @RequestParam(value = "banned", required = false) Boolean banned,
                                       @RequestParam(value = "experience", required = false) Integer experience,
                                       @RequestParam(value = "birthday", required = false) Long birthday) {
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);

        if (birthday < 0L) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (name == null) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (title == null) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (race == null) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (profession == null) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);

        if (name.length() > 12) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (name.length() > 30) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        if (name.equals("")) responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        banned = banned == null ? false : banned;
        Integer level = (int) Math.round((Math.sqrt(2500 + 200 * experience) - 50) / 100);
        Integer untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
        playerService.saveAndFlush(new Player(name, title, race, profession, new Date(birthday),
                banned, experience, level, untilNextLevel));
        return responseEntity;
    }


    @DeleteMapping("/rest/players/{id}")
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

















