package com.frenzi.firstSpring.Dao;

import com.frenzi.firstSpring.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {

        User findByEmail(String email);
        User findByLogin(String login);
        Optional<User> findById(Long id);

}
