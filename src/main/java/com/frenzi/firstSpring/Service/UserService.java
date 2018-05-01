package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Model.Game;
import com.frenzi.firstSpring.Model.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface UserService {
    void save(User user) throws Exception;
    User findByLogin(String login);
    User findByEmail(String email);
    User findById(Long id);
    void changeBalance(String name, double balance);
    void update(User user);
    List<User> getUsersOfGame(Long gameId);
}
