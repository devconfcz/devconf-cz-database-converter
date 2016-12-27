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

            session.setDifficulty(record.get(7));
            session.setDescription(record.get(9));

            // Speakers relation
            String[] speakersEmail = record.get(2).split(";");
            for (String speakerEmail : speakersEmail) {
                // Session 377 contains names instead of email on speaker session
                if (session.getId().equals("377")) {
                    continue;
                }

                Speaker speaker = speakers.get(Speaker.generateIdFromEmail(speakerEmail));
                // Ignore session when speakers does not exists
                if (speaker == null) {
                    System.out.println(session.getId() + " - " + speakerEmail.trim());
                    continue;
                }

                session.getSpeakers().add(speaker.getId());
            }

        }

        in.close();

        return new JsonWriterImpl(speakers, sessions);
    }

}
