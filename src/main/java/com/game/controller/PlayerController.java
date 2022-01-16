package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;


@Controller
public class PlayerController {
    private PlayerServiceImpl playerServiceImpl;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView model = new ModelAndView("index");
        List<Player> playerList = playerServiceImpl.findAll();
        model.addObject("List", playerList);
        return model;
    }


}



