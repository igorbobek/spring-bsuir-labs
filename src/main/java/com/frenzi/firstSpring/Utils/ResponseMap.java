package com.frenzi.firstSpring.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResponseMap {

    @SuppressWarnings("unchecked")
    public static Map<String, String> getResponse(String url, String method) throws IOException{
        HttpURLConnection con;

        URL myurl = new URL(url);
        con = (HttpURLConnection) myurl.openConnection();

        con.setRequestMethod("POST");

        StringBuilder content;

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {

            String line;
            content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }

        return new ObjectMapper().readValue(content.toString(), HashMap.class);
    }
}
