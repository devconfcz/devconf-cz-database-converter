package cz.devconf;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public final class SessionConverter extends BaseConverter {

    private enum Headers {
        TYPE, TITLE, ID, SPEAKERS, TRACK, ROOM, DAY,
        START, END, DURATION, SESSION, QA, BREAK,
        NOTES, STATUS, DESCRIPTION, HOTEL;

        public String nodeName() {
            return name().toLowerCase();
        }
    }

    // Reader ---------------------------------------------------------------------------------------------------------

    /**
     * Read all sessions from CSV and create JSONObject with that
     *
     * @param fileName CVS filename
     * @return SessionConverter instance
     */
    public JSONConverter readCSVFrom(String fileName) throws IOException {
        List<JSONObject> sessions = new ArrayList<JSONObject>();

        Reader in = new FileReader(fileName);

        Iterable<CSVRecord> records = CSVFormat.RFC4180
                .withHeader(Headers.class)
                .parse(in);

        for (CSVRecord record : records) {
            JSONObject session = new JSONObject();

            for (Headers header : Headers.values()) {
                if (header.equals(Headers.SPEAKERS)) {
                    JSONArray speakers = new JSONArray();

                    String[] speakersEmail = record.get(header).split(";");
                    for (String speakerEmail : speakersEmail) {
                        JSONObject speaker = new JSONObject();
                        speaker.put("email", speakerEmail);

                        speakers.add(speaker);
                    }

                    session.put(header.nodeName(), speakers);
                } else {
                    session.put(header.nodeName(), record.get(header));
                }
            }

            sessions.add(session);
        }

        in.close();

        return new JSONConverterImpl(sessions);
    }

    // JSON SessionConverter -------------------------------------------------------------------------------------------------

    /**
     * Create sessions
     * <p>
     * <pre>
     * {
     *    "sessions": {
     *       "201": {
     *          "id": "201",
     *          "title": "My code will change the world",
     *          "day": "1",
     *          "start": "10:30:00 AM",
     *          "room": "vikings"
     *       },
     *       "202": {
     *          "id": "202",
     *          "title": "Listen music when code make every developer more productive",
     *          "day": "1",
     *          "start": "10:30:00 AM",
     *          "room": "spartacus"
     *       }
     *    }
     * }
     * </pre>
     */
    private class AllSessionsJSONCreator implements JSONCreator {

        JSONObject sessions = new JSONObject();

        public void add(JSONObject session) {
            sessions.put(session.get(Headers.ID.nodeName()), session);
        }

        public JSONObject createNode() {
            return sessions;
        }

    }

    /**
     * Create sessions grouped by day
     * <p>
     * <pre>
     * {
     *    "sessions": {
     *       "1": {
     *          "201": {
     *             "id": "201",
     *             "title": "My code will change the world",
     *             "day": "1",
     *             "start": "10:30:00 AM",
     *             "room": "vikings"
     *          },
     *          "202": {
     *             "id": "202",
     *             "title": "Listen music when code make every developer more productive",
     *             "day": "1",
     *             "start": "10:30:00 AM",
     *             "room": "spartacus"
     *          }
     *       }
     *    }
     * }
     * </pre>
     */
    private class SessionsByDayJSONCreator implements JSONCreator {

        JSONObject sessions = new JSONObject();

        Map<String, List<JSONObject>> daysHelper = new HashMap<String, List<JSONObject>>();

        public void add(JSONObject session) {
            if (!daysHelper.containsKey(session.get(Headers.DAY.nodeName()))) {
                daysHelper.put(session.get(Headers.DAY.nodeName()).toString(), new ArrayList<JSONObject>());
            }
            daysHelper.get(session.get(Headers.DAY.nodeName())).add(session);
        }

        public JSONObject createNode() {
            for (Object key : daysHelper.keySet()) {
                JSONObject day = new JSONObject();

                for (Object value : daysHelper.get(key)) {
                    JSONObject session = (JSONObject) value;
                    day.put(session.get(Headers.ID.nodeName()), session);
                }

                sessions.put(key, day);
            }

            return sessions;
        }

    }

    /**
     * Create sessions grouped by day and hour
     * <p>
     * <pre>
     * {
     *    "sessions": {
     *       "1": {
     *          "10:30:00 AM": {
     *             "201": {
     *                "id": "201",
     *                "title": "My code will change the world",
     *                "day": "1",
     *                "start": "10:30:00 AM",
     *                "room": "vikings"
     *             },
     *             "202": {
     *                "id": "202",
     *                "title": "Listen music when code make every developer more productive",
     *                "day": "1",
     *                "start": "10:30:00 AM",
     *                "room": "spartacus"
     *             }
     *          }
     *       }
     *    }
     * }
     * </pre>
     */
    private class SessionsByDayHourJSONCreator implements JSONCreator {

        JSONObject sessions = new JSONObject();

        Map<String, Map<String, List<JSONObject>>> dayHoursHelper = new HashMap<String, Map<String, List<JSONObject>>>();

        public void add(JSONObject session) {
            if (!dayHoursHelper.containsKey(session.get(Headers.DAY.nodeName()))) {
                dayHoursHelper.put(session.get(Headers.DAY.nodeName()).toString(),
                        new HashMap<String, List<JSONObject>>());
            }

            Map<String, List<JSONObject>> day = dayHoursHelper.get(session.get(Headers.DAY.nodeName()));
            if (!day.containsKey(session.get(Headers.START.nodeName()))) {
                day.put(session.get(Headers.START.nodeName()).toString(), new ArrayList<JSONObject>());
            }
            day.get(session.get(Headers.START.nodeName())).add(session);
        }

        public JSONObject createNode() {
            for (Object keyDay : dayHoursHelper.keySet()) {
                JSONObject day = new JSONObject();

                Map<String, List<JSONObject>> hours = dayHoursHelper.get(keyDay);

                for (Object keyHour : hours.keySet()) {
                    JSONObject hour = new JSONObject();

                    for (Object value : hours.get(keyHour)) {
                        JSONObject session = (JSONObject) value;

                        hour.put(session.get(Headers.ID.nodeName()), session);
                    }

                    day.put(keyHour, hour);
                }

                sessions.put(keyDay, day);
            }

            return sessions;
        }

    }

    /**
     * See {@link JSONConverter}
     */
    private final class JSONConverterImpl implements JSONConverter {

        private List<JSONObject> sessions;
        private JSONObject root = new JSONObject();

        public JSONConverterImpl(List<JSONObject> sessions) {
            this.sessions = sessions;
        }

        /**
         * Create a sessions entry grouped by day in the JSON file
         *
         * @return SessionConverter instance
         */
        public JSONWriter createJSON() {
            JSONCreator allSessions = new AllSessionsJSONCreator();
            JSONCreator sessionsByDay = new SessionsByDayJSONCreator();
            JSONCreator sessionsByDayHour = new SessionsByDayHourJSONCreator();

            for (JSONObject session : this.sessions) {
                allSessions.add(session);
                sessionsByDay.add(session);
                sessionsByDayHour.add(session);
            }

            root.put("sessions", allSessions.createNode());
            root.put("sessions_by_day", sessionsByDay.createNode());
            root.put("sessions_by_day_hour", sessionsByDayHour.createNode());

            return new JSONWriterImpl(root);
        }

    }

    // Writter --------------------------------------------------------------------------------------------------------

    private final class JSONWriterImpl implements JSONWriter {

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

    }

}
