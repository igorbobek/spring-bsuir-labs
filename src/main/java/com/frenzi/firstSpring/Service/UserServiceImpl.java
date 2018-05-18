package com.frenzi.firstSpring.Service;

import com.frenzi.firstSpring.Dao.BetDao;
import com.frenzi.firstSpring.Dao.GameDao;
import com.frenzi.firstSpring.Dao.RoleDao;
import com.frenzi.firstSpring.Dao.UserDao;
import com.frenzi.firstSpring.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BetDao betDao;

    @Autowired
    private GameService gameService;



    @Override
    public void save(User user) throws Exception{
        if(findByLogin(user.getName()) == null && findByEmail(user.getEmail()) == null){
            Role role = roleDao.findByRole("USER");
            user.setRoles(new HashSet<>(Arrays.asList(role)));
            userDao.save(user);
        }else{throw new Exception("Exists");}
    }
    @Override
    public void update(User user){
        userDao.save(user);
    }

    @Override
    public void changeBalance(String name, double balance){
        User user = findByLogin(name);
        if (user.getBalance() + balance < 0){
            return;
        }
        double newBalance = Double.parseDouble(String.format("%.2f", user.getBalance()+balance));
        user.setBalance(newBalance);
        update(user);
    }

    @Override
    public User findById(Long id) {
        if(userDao.findById(id).isPresent()){
            return userDao.findById(id).get();
        }
        return null;
    }

    @Nullable
    @Override
    public List<User> getUsersOfGame(Long gameId) {
        Game game = gameService.findById(gameId);
        if (game != null){
            List<User> users = new ArrayList<>();
            for (History item:game.getHistory()
                 ) {
                users.add(item.getUser());
            }
            return users;
        }
        return null;
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByName(login);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

}
