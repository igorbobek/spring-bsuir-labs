package com.frenzi.firstSpring.Dao;


import com.frenzi.firstSpring.Model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Transactional
@RepositoryRestResource(collectionResourceRel = "bet", path = "bet")
public interface GameDao extends CrudRepository<Game, Long> {
    Game findById(long id);
    Set<Game> getAllByOrderByDateDesc();
    Game findFirstByOrderByIdDesc();
}
