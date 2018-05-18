package com.frenzi.firstSpring.Controller;

import com.frenzi.firstSpring.Dao.GameDao;
import com.frenzi.firstSpring.Dao.UserDao;
import com.frenzi.firstSpring.Model.User;
import com.frenzi.firstSpring.Utils.XmlUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    @Autowired
    GameDao gameDao;
    @Autowired
    UserDao userDao;

    @GetMapping("/admin/getXmlGames")
    public void getXmlGames(HttpServletResponse response) throws IOException {
        returnXml(gameDao.findAll(), response );
    }

    @GetMapping("/admin/getXmlUsers")
    public void getXmlUsers(HttpServletResponse response) throws IOException {
        returnXml(userDao.findAll(), response );
    }

    @PostMapping("/deleteGame")
    public void deleteGame(@RequestParam("gameId") String idStr, HttpServletResponse response) throws IOException{
        long id;
        try{
            id = Long.parseLong(idStr);
            gameDao.deleteById(id);
            response.sendRedirect("/user/history/1");

        }catch (NumberFormatException e){
            System.err.println(e.getMessage());
            response.sendRedirect("/crash");
        }
    }

    @GetMapping("/admin/changeBalance")
    public ModelAndView changeBalance(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("change_balance");
        modelAndView.addObject("userNew", new User());
        modelAndView.addObject("user", userDao.findByName(auth.getName()));
        return modelAndView;
    }

    @PostMapping("/admin/changeBalance")
    public ModelAndView changeBalance(@ModelAttribute(value = "user") @Valid User userForm){
        User user = userDao.findByName(userForm.getName());
        if(user == null){
            new ModelAndView("redirect:/");
        }else{
            user.setBalance(userForm.getBalance());
            userDao.save(user);
        }
        return new ModelAndView("redirect:/");
    }

    private void returnXml(Iterable<?> iterable, HttpServletResponse response) throws IOException{
        String xml = XmlUtil.xmlString(iterable);
        byte[] documentBody = xml.getBytes();
        InputStream is = new ByteArrayInputStream(documentBody);
        IOUtils.copy(is, response.getOutputStream());
        response.setContentType("text/xml");
        response.flushBuffer();
    }

}
