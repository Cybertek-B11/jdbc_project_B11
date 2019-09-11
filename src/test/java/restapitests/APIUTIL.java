package restapitests;

import com.google.gson.JsonElement;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class APIUTIL {

    public static Response postAPI(Map<String,String> params, Map<String,String> headers, JsonElement jsonElement, String uri){
        return given().and().params(params).headers(headers).body(jsonElement.toString()).when().post();
    }
}
