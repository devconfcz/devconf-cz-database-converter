package cz.devconf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SpeakerConverter extends BaseConverter {

    private enum Headers {
        COUNTER, NAME, COUNTRY, BIO, ORG, SIZE, EMAIL, AVATAR, TWITTER;

        public String nodeName() {
            return name().toLowerCase();
        }
    }

    public JSONConverter readCSVFrom(String fileName) throws IOException {
        List<JSONObject> sessions = new ArrayList<JSONObject>();

        Reader in = new FileReader(fileName);

        Iterable<CSVRecord> records = CSVFormat.RFC4180
                .withHeader(Headers.class)
                .parse(in);

        for (CSVRecord record : records) {
            JSONObject speakers = new JSONObject();

            for (Headers header : Headers.values()) {
                speakers.put(header.nodeName(), record.get(header));
            }

            sessions.add(speakers);
        }

        in.close();

        return new JSONConverterImpl(sessions);
    }

    private class JSONConverterImpl implements JSONConverter {

        private List<JSONObject> data;

        JSONConverterImpl(List<JSONObject> data) {
            this.data = data;
        }

        public JSONWriter createJSON() {
            JSONObject speakers = new JSONObject();

            for (JSONObject speaker : this.data) {
                speakers.put(speaker.get(Headers.EMAIL.nodeName()), speaker);
            }

            JSONObject root = new JSONObject();
            root.put("speakers", speakers);

            return new JSONWriterImpl(root);
        }

    }

}
