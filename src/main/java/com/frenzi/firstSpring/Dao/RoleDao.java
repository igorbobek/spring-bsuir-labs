package com.frenzi.firstSpring.Dao;

import com.frenzi.firstSpring.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoleDao extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
