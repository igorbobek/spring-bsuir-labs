package com.frenzi.firstSpring.Service;


import com.frenzi.firstSpring.Dao.UserDao;
import com.frenzi.firstSpring.Entity.ContentMsg;
import com.frenzi.firstSpring.Model.Bet;
import com.frenzi.firstSpring.Model.Game;
import com.frenzi.firstSpring.Model.User;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Integer.parseInt;


@Component
public class CrashService extends Thread{


    private double tickValue = 1.0d;
    private double crashPoint = 1.5d;
    private boolean allowForBet = false;
    private static final String KEY = "0000000000000000013cb58ab997c4dc4c76726b7de2f0c6dbacc4b26d434458";
    private String hash = "5092d18f536b817e0ce69d60dd03ffe945f85b954f6c2f6d550266dd882c5a26";

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    HistoryServiceImpl historyService;
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;


    @PostConstruct
    public void init(){
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("game-thread").build();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(this);
        executor.shutdown();

    }


    private Map<Long, Bet> bets = new HashMap<>();

    public double getTickValue() {
        return tickValue;
    }

    public double getCrashPoint() {
        return crashPoint;
    }

    public Map<Long, Bet> getBets() {
        return bets;
    }

    public void run(){
        while(true){
            try {
                generateCrashPoint();
                tick();
                pause();
                tickValue = 1.0d;

            }catch (InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
    }

    private void pause() throws InterruptedException{
        notification();
        saveGame();
        sleep(3000);
        allowForBet = true;
        double timer = 5.0d;
        while (timer > 0) {
            sendMessage("/game/crash", "tick:" + String.format("%.1f", timer));
            timer -= 0.1d;
            sleep(100);
        }
        allowForBet = false;
    }

    private  void tick() throws InterruptedException{
        sendMessage("/game/crash", "tick:"+String.format("%.2f", 1.00d));
        sleep(200);
        long t;
        while(tickValue < crashPoint){
            tickValue += 0.01d;
            sendMessage("/game/crash", "tick:"+String.format("%.2f", tickValue));
            t = (long)(101-100*tickValue/Math.sqrt(Math.pow(tickValue,2)+ 32));
            sleep(t);
        }
    }

    public void addPlayer(Long userId, Bet bet){
        if(!bets.containsKey(userId)) {
            bets.put(userId, bet);
        }
    }

    @Transactional
    protected synchronized void saveGame(){
        Game game = new Game();
        game.setDate(new Date());
        game.setHash(hash);
        game.setMultiplier(crashPoint);
        bets.forEach((user, bet)->{
            historyService.save(user , bet,game);
        });
        bets.clear();
    }

    private void sendMessage(String channel, String message){
        template.convertAndSend(channel, new ContentMsg(HtmlUtils.htmlEscape(message)));
    }

    @Transactional
    protected void notification(){
        Map<Long, Bet> notif = new HashMap<>(bets);
        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("notification").build();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            try {
                notif.forEach((user, bet)->{
                    if(bet.getMultiplier() <= crashPoint) {
                        User curUser = userService.findById(user);
                        userService.changeBalance(curUser.getLogin(), bet.getBet()*bet.getMultiplier());
                        template.convertAndSendToUser("admin", "/game/crash", new ContentMsg(HtmlUtils.htmlEscape(String.format("You won: %.2f coins.", bet.getBet()*crashPoint))));
                    }
                });
            }catch (Exception e){
                System.err.println(e);
            }

        });
        executor.shutdown();
    }

    public boolean isAllowForBet() {
        return allowForBet;
    }

    private void generateCrashPoint(){
        HashFunction hf = Hashing.hmacSha256(KEY.getBytes(com.google.common.base.Charsets.UTF_8));
        HashCode hc = hf.newHasher().putString(hash, Charsets.UTF_8).hash();
        hash = hc.toString();
        Double point;

        if (divisible(hash, 25)) {
            point = 1.00;
        }else{
            Long h = Long.parseLong(hc.toString().substring(0, 52/4), 16);
            Double e = Math.pow(2,52);
            point = (((e-(double)h/50.0f)/(e-(double)h))*100.0f)/100.0f;
        }
        crashPoint = point;
    }

    private boolean divisible(String hash1,int mod) {
        int val = 0;
        int o = hash.length() % 4;
        for (int i = o > 0 ? o - 4 : 0; i < hash.length(); i += 4) {
            val = ((val << 16) + parseInt(hash.substring(i, i+4), 16)) % mod;
        }
        return val == 0;
    }

}
