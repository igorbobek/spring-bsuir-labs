package com.frenzi.firstSpring.Controller;

import com.frenzi.firstSpring.Model.User;
import com.frenzi.firstSpring.Service.GameService;
import com.frenzi.firstSpring.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    GameService gameService;

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        user.setBalance(0);
        modelAndView.setViewName("signup");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signup(@RequestParam("login") String login,
                               @RequestParam("password") String password,
                               @RequestParam("repeat_password") String repeatPassword,
                               @RequestParam("email") String email,
                               ModelAndView modelAndView){
        User user = new User(login,email, password);
        if (!user.getPassword().equals(repeatPassword)){
            modelAndView.addObject("error", "ERROR");
            return modelAndView;
        }
        try{
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.save(user);
            modelAndView = new ModelAndView(new RedirectView("/"));
            return modelAndView;
        }catch (Exception e){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", "ERROR");
            System.err.println(e.getMessage());
        }
        return modelAndView;
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
        modelAndView.addObject("games", gameService.getGamesOfUser(user.getId()).subList((page-1)*10, (page-1)*10+10));
        modelAndView.addObject("pagesCount", gameService.getGamesOfUser(user.getId()).size()/10);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/crash"));
        return modelAndView;
    }

}
