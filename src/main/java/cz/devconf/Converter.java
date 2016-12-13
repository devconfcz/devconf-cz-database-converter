package cz.devconf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Converter {

    private final JSONObject root;
    private final List<JSONObject> sessions;

    private enum Headers {
        TYPE, TITLE, ID, SPEAKERS, TRACK, ROOM, DAY,
        START, END, DURATION, SESSION_DURATION, QA, BREAK,
        NOTES, STATUS, DESCRIPTION, HOTEL;

        public String nodeName() {
            return name().toLowerCase();
        }
    }

    public Converter() {
        root = new JSONObject();

        sessions = new ArrayList<JSONObject>();
    }

    /**
     * Cache all sessions in memory
     *
     * @param fileName CVS filename
     * @return Converter instance
     */
    public Converter readCSVFrom(String fileName) throws IOException {
        Reader in = new FileReader(fileName);

        Iterable<CSVRecord> records = CSVFormat.RFC4180
                .withHeader(Headers.class)
                .parse(in);

        for (CSVRecord record : records) {
            JSONObject session = new JSONObject();

            for (Headers header : Headers.values()) {
                session.put(header.nodeName(), record.get(header));
            }

            sessions.add(session);
        }

        return this;
    }

    /**
     * Write a JSON file with sessions
     *
     * @param fileName JSON file with sessions
     */
    public void writeIn(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(root.toJSONString());
    }

    /**
     * Create a sessions entry in the JSON file
     *
     * @return Converter instance
     * @throws IOException
     */
    public Converter createAllSessionsJSON() throws IOException {
        JSONObject sessions = new JSONObject();

        for (JSONObject session : this.sessions) {
            sessions.put(session.get(Headers.ID.nodeName()), session);
        }

        root.put("sessions", sessions);

        return this;
    }

    public Converter createSessionsByDayJSON() throws IOException {

        JSONObject sessionsByDay = new JSONObject();

        HashMap<String, List<JSONObject>> daysHelper = new HashMap<String, List<JSONObject>>();

        // Group by day
        for (JSONObject session : this.sessions) {
            if (!daysHelper.containsKey(session.get(Headers.DAY.nodeName()))) {
                daysHelper.put(session.get(Headers.DAY.nodeName()).toString(), new ArrayList<JSONObject>());
            }
            daysHelper.get(session.get(Headers.DAY.nodeName())).add(session);
        }

        // Create JSON nodes by day
        for (Object key : daysHelper.keySet()) {
            JSONObject day = new JSONObject();

            for (Object value : daysHelper.get(key)) {
                JSONObject session = (JSONObject) value;
                day.put(session.get(Headers.ID.nodeName()), session);
            }

            sessionsByDay.put(key, day);
        }

        root.put("sessions_by_day", sessionsByDay);

        return this;

    }

    public Converter createSessionsByDayAndHoursJSON() throws IOException {

        return null;

    }


}
