package cz.devconf;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public abstract class BaseConverter {

    abstract JSONConverter readCSVFrom(String fileName) throws IOException;

    final class JSONWriterImpl implements JSONWriter {

        private JSONObject data;

        public JSONWriterImpl(JSONObject data) {
            this.data = data;
        }

        /**
         * Write a JSON file with sessions
         *
         * @param fileName JSON file with sessions
         */
        public void writeIn(String fileName) throws IOException {
            String prettyJsonString = formatterJSON(data);

            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(prettyJsonString);

            fileWriter.close();
        }

        /**
         * Formatter JSON string
         *
         * @param data {@link JSONObject} to be formatted
         *
         * @return Formmated JSON String
         */
        private String formatterJSON(JSONObject data) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jsonParser = new JsonParser();
            JsonElement je = jsonParser.parse(data.toJSONString());

            return gson.toJson(je);
        }

    }


}
