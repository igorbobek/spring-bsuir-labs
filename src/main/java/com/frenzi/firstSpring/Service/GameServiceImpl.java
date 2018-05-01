package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Dao.GameDao;
import com.frenzi.firstSpring.Dao.UserDao;
import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.Game;
import com.frenzi.firstSpring.Model.History;
import com.frenzi.firstSpring.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private HistoryService historyService;


    @Autowired
    private UserService userService;

    @Override
    public void save(Game game, String hash) {
        game.setDate(new Date());
        game.setHash(hash);
        gameDao.save(game);
    }

    @Nullable
    @Override
    public List<Game> getAllGames() {
        List<Game> game = new ArrayList<>();
        gameDao.findAll().forEach(game::add);
        return game;
    }

    @Nullable
    @Override
    public List<Game> getGamesOfUser(Long idUser) {
        List<Game> game = null;
        if(userService.findById(idUser)!= null) {
            game = new ArrayList<>();
            for (History element:userService.findById(idUser).getHistory()
                 ) {
                game.add(element.getGame());
            }
        }
        return game;
    }

    @Override
    public Game findById(Long gameId) {
        return gameDao.findById(gameId).isPresent() ? gameDao.findById(gameId).get() : null;
    }

    @Nullable
    @Override
    public Map<User, Bet> getInfoGame(Long gameId) {
        Game game = findById(gameId);
        Map<User,Bet> result = new HashMap<>();
        game.getHistory().forEach((item)->{
            result.put(item.getUser(),item.getBet());
        });
        return result.size() != 0 ? result : null;
    }
}
