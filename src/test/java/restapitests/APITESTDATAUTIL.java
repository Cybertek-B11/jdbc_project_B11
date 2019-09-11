package restapitests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

import java.io.InputStream;

public class APITESTDATAUTIL {
    public JsonElement jsonElement;
    public JsonElement rootElement;

    public static Response getJsonFromFile(String filePath){
        //assume Ad.json in the resources folder
        JsonParser jsonParser = new JsonParser();
        filePath = "src/resources/"+filePath;
//        InputStream inputStream;
        //creates input stream
        //creates Reader object
        //then json parser parses the reader
        //this.rootElement = whatever is parsed from basic json
        return null;
    }
}
