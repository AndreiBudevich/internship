package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>, CrudRepository <Player, Long> {


}
