package com.frenzi.firstSpring.Dao;

import com.frenzi.firstSpring.Model.Game;
import com.frenzi.firstSpring.Model.History;
import com.frenzi.firstSpring.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Transactional
@RepositoryRestResource(collectionResourceRel = "history", path = "history")
public interface HistoryDao extends CrudRepository<History, Long> {
    Set<History> findByUser(User user);
    Set<History> findByGame(Game game);
    Set<History> getAllByUser(User user);
    Set<History> findAll();
}
