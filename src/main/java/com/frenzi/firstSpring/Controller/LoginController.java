package com.frenzi.firstSpring.Controller;

import com.frenzi.firstSpring.Model.User;
import com.frenzi.firstSpring.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
@Controller
@Secured("ROLE_ANONYMOUS")
public class LoginController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/login")
    public ModelAndView login(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

}
