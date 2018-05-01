package com.frenzi.firstSpring.Dao;


import com.frenzi.firstSpring.Model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface GameDao extends CrudRepository<Game, Long> {
    Game findById(long id);
}
