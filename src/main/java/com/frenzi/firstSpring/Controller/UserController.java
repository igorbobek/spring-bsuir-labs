package com.frenzi.firstSpring.Controller;

import com.frenzi.firstSpring.Entity.ContentMsg;
import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.User;
import com.frenzi.firstSpring.Service.GameService;
import com.frenzi.firstSpring.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    GameService gameService;

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }


    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("user/history/{page}")
    public ModelAndView userHistory(@PathVariable(value="page") String pageValue){
        int page = 0;

        try {
            page = Integer.parseInt(pageValue);
        }catch (NumberFormatException e){
            System.out.println(e);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByLogin(auth.getName());
        ModelAndView modelAndView = new ModelAndView("crash");
        modelAndView.addObject("template", "history");
        if(gameService.getGamesOfUser(user.getId()).size() > 9) {
            modelAndView.addObject("games", gameService.getGamesOfUser(user.getId()).subList((page - 1) * 10, (page - 1) * 10 + 10));
            modelAndView.addObject("pagesCount", gameService.getGamesOfUser(user.getId()).size()/10);
        }else{
            modelAndView.addObject("games", gameService.getGamesOfUser(user.getId()));
            modelAndView.addObject("pagesCount", 1);
        }
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/crash"));
        return modelAndView;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/crash/getUser")
    @ResponseBody
    public ResponseEntity<?> getBalance(){
        System.out.println("asdasd");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(200).body(userService.findByLogin(auth.getName()).getBalance());
    }

}
