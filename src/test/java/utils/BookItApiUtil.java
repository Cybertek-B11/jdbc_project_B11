package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
public class BookItApiUtil {
    
    private static Response response;

    public static void setResponse(Response res) {
        response = res;
    }

    public static Response getResponse() {
        return response;
    }

    public static String generateToken() {
        Response response = given().queryParams("email", ConfigurationReader.getProperty("bookit.email"),
                                                "password",ConfigurationReader.getProperty("bookit.password") )
                           .when().get(ConfigurationReader.getProperty("bookit.baseuri")+"/sign");
       // String token = response.body().jsonPath().getString("accessToken");
        JsonPath jsonPath = response.body().jsonPath();
        String token = jsonPath.getString("accessToken");
        return token;

    }

}
