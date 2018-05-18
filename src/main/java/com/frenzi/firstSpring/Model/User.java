package com.frenzi.firstSpring.Model;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String name;

    private String password;
    private String email;
    private double balance;

    private String image;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_wallet",
        joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_wallet"))
    private Set<Wallet> wallets = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

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
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(Set<Wallet> wallets) {
        this.wallets = wallets;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }

        if(obj instanceof User){
            User user = (User)obj;
            if (user.name.equals(this.name) && user.email.equals(this.email)) {
                return true;
            }else{
                return false;
            }
        }
        return false;

    }

    @Override
    public int hashCode() {
        return this.email.hashCode() + this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Email: " + this.email + ", Password: " + this.password;
    }
}
