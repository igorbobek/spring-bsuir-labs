package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.Game;
import com.frenzi.firstSpring.Model.History;
import com.frenzi.firstSpring.Model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HistoryService {
    void save(Long userId, Bet bet, Game game);
    Set<History> getAll();

}
