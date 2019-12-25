import com.google.gson.JsonObject;
import org.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//ref : https://www.youtube.com/watch?v=h5VLKYOQOjM
// using http://api.ipinfodb.com/
//http://api.ipinfodb.com/v3/ip-city/?key=d8b8a275ffec129cae9b57256b1f85348d4ab9f4618f7102fb2c749adc703353&ip=192.168.0.10&format=json

public class javaJsonExample {
    public static void main(String[] args)  {
        System.out.println("java json example");

        try {
            String ip = "";
            String key = "";
           // String url = "http://api.ipinfodb.com/v3/ip-city/?key=d8b8a275ffec129cae9b57256b1f85348d4ab9f4618f7102fb2c749adc703353&ip=192.168.0.10&format=json";
            String url = "http://api.openweathermap.org/data/2.5/weather?lang=en&units=metric&q=Kharkiv,UA&type=accurate&appid=e463778d070d9fc0b7f0661a6657cdb3&mode=json" ;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent","Mozilla/5.0");
            BufferedReader in = new BufferedReader(
              new InputStreamReader(con.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();
            while((inputline = in.readLine()) != null){
                response.append(inputline);
            }
            in.close();
            System.out.println(response.toString());
            JSONObject obj_json = new JSONObject(response.toString());
            System.out.println(obj_json.getString("weather.id"));

        }
        catch (Exception e){
            System.out.println(e);
        } //
    }
}
