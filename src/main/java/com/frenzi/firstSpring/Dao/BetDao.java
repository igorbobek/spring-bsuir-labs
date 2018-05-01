package com.frenzi.firstSpring.Dao;

import com.frenzi.firstSpring.Model.Bet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BetDao extends CrudRepository<Bet, Long>{
}
