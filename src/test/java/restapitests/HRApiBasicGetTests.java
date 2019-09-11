package restapitests;

import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class HRApiBasicGetTests {
    String hrRegionsUrl = "http://ec2-54-164-195-86.compute-1.amazonaws.com:1000/ords/hr/regions";

    @Test
    public void getAllRegionsTest(){
        Response response = get(hrRegionsUrl);
        System.out.println("Status code: " + response.statusCode());
        System.out.println("content type: " + response.contentType());
        response.body().prettyPrint();

        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Americas"));
        assertTrue(response.body().asString().contains("Europe"));
    }

}
