package cz.devconf.converter;

import cz.devconf.model.Session;
import cz.devconf.model.Speaker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;


public class SessionsDetailsReaderImpl implements SessionsDetailsReader {

    private final Map<String, Speaker> speakers;
    private final Map<String, Session> sessions;
    private final Map<String, Map<String, String>> sessionSpeakers = new HashMap<String, Map<String, String>>();

    public SessionsDetailsReaderImpl(Map<String, Speaker> speakers, Map<String, Session> sessions) {
        this.speakers = speakers;
        this.sessions = sessions;
    }

    public JsonWriterImpl readSessionsDetailsFrom(String fileName) throws IOException {

        Reader in = new FileReader(fileName);

        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

        for (CSVRecord record : records) {

//                [0] track
//                [1] session
//                [2] speakers
//                [3] id
//                [4] title
//                [5] type
//                [6] theme
//                [7] difficulty
//                [8] abstract
//                [9] description

            Session session = sessions.get(record.get(3));

            session.setTitle(record.get(4));
            session.setTrack(record.get(0));
            session.setSpeakers(record.get(2));
            session.setDifficulty(record.get(7));
            session.setDescription(record.get(9));

            createSpeakerRelation(session);

        }

        in.close();

        return new JsonWriterImpl(speakers, sessions, sessionSpeakers);
    }

    private void createSpeakerRelation(Session session) {
        String[] speakersEmail = session.getSpeakers().split(";");
        for (String speakerEmail : speakersEmail) {
            // Session 377 contains names instead of email on speaker session
            if(session.getId().equals("377")) {
                continue;
            }

            Speaker speaker = speakers.get(Speaker.generateIdFromEmail(speakerEmail));
            // Ignore session when speakers does not exists
            if(speaker == null) {
                System.out.println(session.getId() + " - " +  speakerEmail.trim());
                continue;
            }

            if(!sessionSpeakers.containsKey(session.getId())) {
                Map<String, String> speakers = new HashMap<String, String>();
                sessionSpeakers.put(session.getId(), speakers);
            }
            sessionSpeakers.get(session.getId()).put(speaker.getId(), speaker.getName());
        }

    }

}
