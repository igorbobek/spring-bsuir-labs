package com.frenzi.firstSpring.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private String email;
    private double balance;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

   /* @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "history", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_game", referencedColumnName = "id"))
    private Set<Game> games = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "history", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_bet", referencedColumnName = "id"))
    private Set<Bet> bets = new HashSet<>();*/
   @OneToMany(mappedBy = "user",fetch = FetchType.EAGER )
   private Set<History> history = new HashSet<>();

    public Set<History> getHistory() {
        return history;
    }

    public void setHistory(Set<History> history) {
        this.history = history;
    }

    public User(){}

    public User(String name, String email, String password){
        this.login = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }

        if(obj instanceof User){
            User user = (User)obj;
            if (user.getLogin().equals(this.getLogin())) {
                return true;
            }else{
                return false;
            }
        }
        return false;

    }

    @Override
    public int hashCode() {
        return this.email.hashCode() + this.login.hashCode();
    }

    @Override
    public String toString() {
        return "Name: " + this.login + ", Email: " + this.email + ", Password: " + this.password;
    }
}
