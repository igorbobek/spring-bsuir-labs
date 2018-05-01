package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Dao.HistoryDao;
import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.Game;
import com.frenzi.firstSpring.Model.History;
import com.frenzi.firstSpring.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;
    @Autowired
    BetService betService;
    @Autowired
    HistoryDao historyDao;

    @Override
    public void save(Long userId, Bet bet, Game game) {
        User user = userService.findById(userId);
        History history = new History();
        history.setGame(game);
        history.setBet(bet);
        history.setUser(user);
        historyDao.save(history);
    }

    @Override
    public Set<History> getAll() {

        return historyDao.findAll();
    }
}
