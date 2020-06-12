/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherbot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Mikhail
 */
public class Weather {

    public static String getWeather(String message, Model model) throws MalformedURLException, IOException {
        //9397cae1d6b7bd176537d50a9c9731b1
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=9397cae1d6b7bd176537d50a9c9731b1");
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));
        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for(int i=0;i<getArray.length();i++){
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }
        return "City: "+model.getName()+ "\n" +
                "Temprature: "+ model.getTemp() + "C" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Main: " + model.getMain() + "\n" +
                "https://openweathermap.org/img/w/" + model.getIcon() +  ".png";

    }
}
