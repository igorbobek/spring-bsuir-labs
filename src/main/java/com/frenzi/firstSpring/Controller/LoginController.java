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
public class LoginController {

    @Autowired
    UserServiceImpl userService;

    @Secured("ROLE_ANONYMOUS")
    @RequestMapping(value = "/login")
    public ModelAndView login(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("user", userService.findByLogin(auth.getName()));
        return modelAndView;
    }

    @ResponseBody
    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/login")
    public ModelAndView login(Model model, Error errors){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByLogin(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }



}
