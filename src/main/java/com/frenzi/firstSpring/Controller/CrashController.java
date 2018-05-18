package com.frenzi.firstSpring.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frenzi.firstSpring.Dao.GameDao;
import com.frenzi.firstSpring.Entity.ContentMsg;
import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.User;
import com.frenzi.firstSpring.Service.*;
import com.google.common.collect.ImmutableSet;

import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.*;


@Controller
public class CrashController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    CrashService crashService;
    @Autowired
    HistoryService historyService;
    @Autowired
    GameService gameService;
    @Autowired
    GameDao gameDao;
    @Autowired
    BetService betService;

    @GetMapping("/crash")
    public ModelAndView crash(Principal principal) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("crash");
        Map<String, Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("template", "game");
        modelAndView.addObject("games", gameService.getAllByOrderByDate().subList(0,10));
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            modelAndView.addObject("user", userService.findByLogin(auth.getName()));
        }
        modelAndView.addObject("model", new User());
        System.out.println(crashService.getTickValue());
        return modelAndView;
    }


    @GetMapping("/crash/history/{page}")
    @ResponseBody
    public ModelAndView history(@PathVariable(value="page") String pageValue,HttpServletResponse httpServletResponse)throws IOException{
        ModelAndView modelAndView = null;
        try {
            int page = Integer.parseInt(pageValue);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            modelAndView = new ModelAndView();

            if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
                modelAndView.addObject("user", userService.findByLogin(auth.getName()));
            }

            modelAndView.setViewName("crash");
            modelAndView.addObject("template", "history");
            modelAndView.addObject("games", gameService.getAllGames().subList((page-1)*10, (page-1)*10+10));
            modelAndView.addObject("pagesCount", gameService.getAllGames().size()/10);

        }catch (NumberFormatException e){
            System.err.println(e.getMessage());
        }

        return modelAndView;
    }


    @Secured("ROLE_USER")
    @GetMapping("/crash/")
    public void crash(HttpServletResponse httpServletResponse) throws IOException{
        httpServletResponse.sendRedirect("/crash");
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/crash/bet")
    @ResponseBody
    public ResponseEntity<?> crash(@Valid @RequestBody Bet bet){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(bet.getBet());
        try {
            if(betService.makeBet(userService.findByLogin(auth.getName()).getId(), bet)){
                return ResponseEntity.ok(new ContentMsg("SUCCESS"));
            }else{
                return ResponseEntity.status(200).body(new ContentMsg("ERROR"));
            }
        }catch (NullPointerException e){
            return ResponseEntity.status(200).body(new ContentMsg("ERROR"));
        }
    }


    @GetMapping("/crash/games/{id}")
    @ResponseBody
    public ModelAndView getInfoGame(@PathVariable("id") String strId )throws NumberFormatException{
        ModelAndView modelAndView = new ModelAndView();
        Long id = Long.parseLong(strId);
        modelAndView.addObject("info",gameService.getInfoGame(id));
        modelAndView.addObject("game",gameService.findById(id));
        modelAndView.addObject("template", "game_history");
        modelAndView.setViewName("crash");
        modelAndView.addObject("user", userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));

        return modelAndView;
    }

    @PostMapping("/")
    public void index(HttpServletResponse response)throws IOException{
        response.sendRedirect("/");
    }

}
