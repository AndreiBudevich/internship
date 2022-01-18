package com.game.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.*;
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


    @GetMapping(path = "a/rest/players/{id}")
    @ResponseBody
    public ResponseEntity<String> getPlayer2(@PathVariable(value = "id", required = false) Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players = playerService.listAll();
        String jsonString = null;
        if (id <= 0) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else if (!playerService.existsById(id)) return new ResponseEntity(HttpStatus.NOT_FOUND);
        else {
            Player player = players.get(Math.toIntExact(id));
            jsonString = mapper.writeValueAsString(player);
            System.out.println(jsonString);
            return ResponseEntity.ok(jsonString);
        }


    }


    @GetMapping(path = "/rest/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable(value = "id", required = false) Long id) {
        if (id < 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!playerService.existsById(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Player result = playerService.getPlayerById(id);
        return ResponseEntity.ok(result);
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
    public ResponseEntity<Player> сreatePlayer(@RequestBody Player player) {
        if (player.getExperience() == null) {
            player.setExperience(0);
        }
        if (isnotValidtitle(player.getTitle())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (isnotValidName(player.getName())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (player.getRace() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (isnotValidBirthday(player.getBirthday())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (isnotValidExperience(player.getExperience())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (player.getProfession() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        player.setBanned(player.getBanned() == null ? false : player.getBanned());


        Integer level = (int) Math.round((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        Integer untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();

        Player playernew = new Player(player.getName(), player.getTitle(), player.getRace(), player.getProfession(), player.getBirthday(),
                player.getBanned(), player.getExperience(), level, untilNextLevel);
        playerService.saveAndFlush(playernew);

        Player playernew2 = playerService.findPlayer(playernew);

        System.out.println(playernew2);

        return ResponseEntity.ok(playernew2);
    }

    public Boolean isnotValidExperience(Integer experience) {
        if (experience < 0 && experience > 10_000_000) return true;
        return false;
    }

    public Boolean isnotValidtitle(String title) {
        if (title == null && title.length() > 30) return true;
        return false;
    }

    public Boolean isnotValidName(String name) {
        if (name == null && name.length() > 12 && name.equals("")) return true;
        return false;
    }

    public Boolean isnotValidBirthday(Date birthday) {
        Calendar date2000 = new GregorianCalendar(2000, 01, 01, 0, 0, 0);
        Calendar date3000 = new GregorianCalendar(3001, 01, 01, 0, 0, 0);
        Date date2 = date2000.getTime();
        Date date3 = date2000.getTime();
        if (birthday.getTime() < date2.getTime() && !(birthday.getTime() < date3.getTime())) return false;
        if (birthday.getTime() < 0) return false;
        return true;
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

    @PostMapping("/rest/players/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {

        Player playerResult = playerService.getPlayerById(player.getId());

        if (player.getName() != null) {playerResult.setName(player.getName());}
        if (player.getTitle() != null) {playerResult.setTitle(player.getTitle());}
        if (player.getRace() != null) {playerResult.setRace(player.getRace());}


        return ResponseEntity.ok(playerResult);
}



}

















