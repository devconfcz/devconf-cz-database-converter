package cz.devconf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

import java.io.IOException;

public abstract class BaseConverter {

    abstract JSONConverter readCSVFrom(String fileName) throws IOException;

    /**
     * Formatter JSON string
     *
     * @param data {@link JSONObject} to be formatted
     *
     * @return Formmated JSON String
     */
    String formatterJSON(JSONObject data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement je = jsonParser.parse(data.toJSONString());

        return gson.toJson(je);
    }


}
