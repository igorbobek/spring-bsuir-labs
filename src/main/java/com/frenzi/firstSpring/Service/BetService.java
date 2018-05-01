package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Model.Bet;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface BetService {

    @Secured("ROLE_ADMIN")
    List<Bet> getAllBets();

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    List<Bet> getBetsOfUser(Long userId);

    List<Bet> getBetsOfGame(Long gameId);

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    boolean makeBet(Long userId, Bet bet);
}
