package com.frenzi.firstSpring.Dao;

import com.frenzi.firstSpring.Model.Bet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BetDao extends CrudRepository<Bet, Long>{
}
