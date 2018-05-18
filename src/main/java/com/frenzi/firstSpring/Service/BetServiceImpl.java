package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Dao.BetDao;
import com.frenzi.firstSpring.Dao.GameDao;
import com.frenzi.firstSpring.Dao.HistoryDao;
import com.frenzi.firstSpring.Dao.UserDao;
import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.History;
import com.frenzi.firstSpring.Model.User;
import org.springframework.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BetServiceImpl implements BetService{

    @Autowired
    private BetDao betDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    HistoryDao historyDao;

    @Autowired
    UserService userService;

    @Autowired
    private CrashService crashService;

    @Nullable
    @Override
    public List<Bet> getAllBets() {
        List<Bet> bets = new ArrayList<>();
        betDao.findAll().forEach(bets::add);
        return bets.size() == 0 ? null : bets;
    }

    @Nullable
    @Override
    public List<Bet> getBetsOfUser(Long userId) {
        List<Bet> bets = null;

        if(userDao.findById(userId).isPresent()) {
            bets = new ArrayList<>();
            Set<History> histories = historyDao.findByUser(userDao.findById(userId).get());
            for (History history: histories){
                bets.add(history.getBet());
            }
        }

        return bets;
    }

    @Nullable
    @Override
    public List<Bet> getBetsOfGame(Long gameId) {

        final List<Bet> bets = new ArrayList<>();
        if(gameDao.findById(gameId).isPresent()) {
            historyDao.findByGame(gameDao.findById(gameId).get()).forEach(
                (element)->{
                    bets.add(element.getBet());
                }
            );
        }

        return bets.size() == 0 ? null : bets;
    }

    @Override
    public boolean makeBet(Long userId, Bet bet){
        if(userDao.findById(userId).isPresent()){
            User user = userDao.findById(userId).get();
            if(crashService.isAllowForBet() && userDao.findById(userId).get().getBalance() >= bet.getBet() && !crashService.getBets().containsKey(user.getId()) && bet.getMultiplier() >= 1.1){
                userService.changeBalance(user.getName(), bet.getBet()*-1);
                crashService.addPlayer(userId, bet);
                return true;
            }
        }
        return false;
    }
}
