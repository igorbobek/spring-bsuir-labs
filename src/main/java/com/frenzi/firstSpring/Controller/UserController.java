package com.frenzi.firstSpring.Controller;

import com.frenzi.firstSpring.Model.User;
import com.frenzi.firstSpring.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("/signup")
    public ModelAndView signup(User user){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signup(@RequestParam("repeat_password") String repeatPassword, ModelAndView modelAndView, @Valid User user){
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
    @GetMapping("/logout")
    public ModelAndView logout(){
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/crash"));
        return modelAndView;
    }

}
