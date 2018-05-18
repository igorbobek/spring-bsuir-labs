package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.Game;
import com.frenzi.firstSpring.Model.User;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Map;
public interface GameService {
    void save(Game game, String hash);
    void save(Game game);
    List<Game> getAllGames();
    List<Game> getGamesOfUser(Long idUser);
    Game findById(Long gameId);
    Map<User,Bet> getInfoGame(Long gameId);
    List<Game> getAllByOrderByDate();
}
