package org.izv.clashroyale.model.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CardList {

    public String getEmpty(String urlC) {
        try
        {
            URL url = new URL(urlC);
            URLConnection uc = url.openConnection();
            uc.setDoInput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            StringBuilder a = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                a.append(inputLine);
            in.close();
            return a.toString();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
}