import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.*;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

// reference : https://github.com/xSAVIKx/openweathermap-java-api/blob/development/api-examples/src/main/java/org/openweathermap/api/example/CurrentWeatherOneLocationExample.java
// reference : https://www.youtube.com/watch?v=og5h5ppwXgU

public class weatherApiCall {

    private static final String API_KEY = "e463778d070d9fc0b7f0661a6657cdb3";

    public static void main(String[] args) {
  //  String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    String LOCATION = "London,uk";
    String urlString = "http://api.openweathermap.org/data/2.5/weather?lang=en&units=metric&q=Kharkiv,UA&type=accurate&appid=e463778d070d9fc0b7f0661a6657cdb3&mode=json" ;
            //""https://openweathermap.org/data/2.5/weather?q="+ LOCATION +"&appid=" + API_KEY;

    System.out.println(urlString);

    try {
        StringBuilder res = new StringBuilder();
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null){
            res.append(line);
        }
        System.out.println(res);
    }
   catch(Exception e) {
        System.out.println("exception caught" + e);
    } // end of message fetching



        //part 2 : using the example from opwnWeatherMap Api -
        DataWeatherClient client = new UrlConnectionDataWeatherClient(API_KEY);
        CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick()
                .currentWeather()                   // get current weather
                .oneLocation()                      // for one location
                .byCityName("Kharkiv")              // for Kharkiv city
                .countryCode("UA")                  // in Ukraine
                .type(Type.ACCURATE)                // with Accurate search
                .language(Language.ENGLISH)         // in English language
                .responseFormat(ResponseFormat.JSON)// with JSON response format
                .unitFormat(UnitFormat.METRIC)      // in metric units
                .build();
        CurrentWeather currentWeather = client.getCurrentWeather(currentWeatherOneLocationQuery);
        System.out.println(prettyPrint(currentWeather));

    }

    // function to pretty print the json api output
    private static String prettyPrint(CurrentWeather currentWeather) {
        return String.format(
                "Current weather in %s(%s):\ntemperature: %.1f ℃\nhumidity: %.1f %%\npressure: %.1f hPa\nWind: %s",
                currentWeather.getCityName(), currentWeather.getSystemParameters().getCountry(),
                currentWeather.getMainParameters().getTemperature(),
                currentWeather.getMainParameters().getHumidity(),
                currentWeather.getMainParameters().getPressure(),
                currentWeather.getWind().getDirection()
        );
    }
}
