package com.frenzi.firstSpring.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bet")
public class Bet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double bet;
    private Double multiplier;

    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinTable(name = "history", joinColumns = @JoinColumn(name = "id_bet", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"))


    //private Set<User> users = new HashSet<>();


    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinTable(name = "history", joinColumns = @JoinColumn(name = "id_bet", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_game", referencedColumnName = "id"))
    @OneToMany(mappedBy = "bet",fetch = FetchType.EAGER )
    private Set<History> history = new HashSet<>();
    //private Set<Game> games = new HashSet<>();

    public Set<History> getHistory() {
        return history;
    }

    public void setHistory(Set<History> history) {
        this.history = history;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBet() {
        return bet;
    }

    public void setBet(Double bet) {
        this.bet = bet;
    }
    /*
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }*/
}
