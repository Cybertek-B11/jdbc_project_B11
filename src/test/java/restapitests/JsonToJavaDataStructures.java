package restapitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class JsonToJavaDataStructures {

    @Test
    public void convertSpartanData_to_Map() {
        Response response = given().accept(ContentType.JSON)
                .when().get("http://54.164.195.86:8000/api/spartans/33");

        Map<String, Object> spartanMap = response.body().as(Map.class);

        System.out.println("spartanMap = " + spartanMap.toString());
        System.out.println(spartanMap.get("id"));

    }


}
