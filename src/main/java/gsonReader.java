import com.google.gson.Gson;

// ref ; https://www.youtube.com/watch?v=y96VcLgOJqA
//created automatically - using jsontoJsonSchema link
//http://www.jsonschema2pojo.org/


import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class gsonReader {
    public static void main(String[] args) {

        System.out.println("hello gson");
        Gson gson = new Gson();
        BufferedReader br = null;

     /*   try {
            br = new BufferedReader(new FileReader("resources/sampleJson.json"));
            Result res = gson.fromJson(br, GsonExample.class);

            if (res != null ){
                /*for ( GsonExample t){
                    System.out.println(t.getAge() + " - " + t.getFirstName() +  " - " + t.getLastName());
                }
                System.out.println("to be figured out");

            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
    }
}
