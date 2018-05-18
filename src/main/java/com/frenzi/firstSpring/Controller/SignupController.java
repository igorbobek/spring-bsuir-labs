package com.frenzi.firstSpring.Controller;

import com.frenzi.firstSpring.Model.User;
import com.frenzi.firstSpring.Model.Wallet;
import com.frenzi.firstSpring.Service.UserService;
import com.frenzi.firstSpring.Utils.ResponseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@Controller
@Secured("ROLE_ANONYMOUS")
public class SignupController {

    @Autowired
    UserService userService;


    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/signup")
    @ResponseBody
    public ModelAndView signup(@ModelAttribute("user") @Valid User userForm, @RequestParam("matchingPassword") String matchPass, ModelAndView modelAndView){

        if(userForm.getName().equals("") || userForm.getEmail().equals("") || userForm.getPassword().equals("") || userForm.getImage().equals("") || userForm.getPassword().length() < 6){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", "Вводимые поля не должны быть пустыми!");
            return modelAndView;
        }

        if(!userForm.getName().matches("\\b[a-zA-Z][a-zA-Z0-9\\-._]{4,}\\b")){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", "Логин должен быть длиною больше четырех символов, а также содержать буквы и цифры");
            return modelAndView;
        }

        if (!userForm.getPassword().equals(matchPass)){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", "Оба введенных пароля должны быть идентичны!");
            return modelAndView;
        }
        try{

            Map<String,String> resultMap = ResponseMap.getResponse("https://api.blockcypher.com/v1/btc/main/addrs", "GET");

            if (resultMap.isEmpty()){
                modelAndView.setViewName("signup");
                modelAndView.addObject("error", "В данный момент регистрация не доступна.");
                return modelAndView;
            }

            Wallet wallet = new Wallet(resultMap.get("address"),0, resultMap.get("private"), resultMap.get("public"), resultMap.get("wif"));
            userForm.getWallets().add(wallet);

            userForm.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            userService.save(userForm);
            modelAndView = new ModelAndView(new RedirectView("/"));
            return modelAndView;
        }catch (Exception e){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", e.getMessage());
            System.err.println(e.getMessage());
        }

        return modelAndView;
    }
}
