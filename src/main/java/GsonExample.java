//-----------------------------------streamingapi.restClient.GsonExample.java-----------------------------------

//package streamingapi.restClient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// ref ; https://www.youtube.com/watch?v=y96VcLgOJqA
//created automatically - using jsontoJsonSchema link
//http://www.jsonschema2pojo.org/

public class GsonExample {

@SerializedName("firstName")
@Expose
private String firstName;
@SerializedName("lastName")
@Expose
private String lastName;
@SerializedName("age")
@Expose
private Integer age;

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public Integer getAge() {
return age;
}

public void setAge(Integer age) {
this.age = age;
}

}