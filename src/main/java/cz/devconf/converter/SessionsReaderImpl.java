package cz.devconf.converter;

import cz.devconf.model.Session;
import cz.devconf.model.Speaker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SessionsReaderImpl implements SessionsReader {

    private Map<String, Speaker> speakers;

    public SessionsReaderImpl(Map<String, Speaker> speakers) {
        this.speakers = speakers;
    }

    public SessionsDetailsReader readSessionsFrom(String fileName) throws IOException {
        Map<String, Session> sessions = new LinkedHashMap<String, Session>();

        Reader in = new FileReader(fileName);

        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

        for (CSVRecord record : records) {

            Session session = new Session();

//                [0] type
//                [1] title
//                [2] session_id
//                [3] speakers
//                [4] status
//                [5] description
//                [6] hotel
//                [7] track
//                [8] room
//                [9] day
//                [10] start_time
//                [11] end_time
//                [12] duration
//                [13] session_duration
//                [14] session_qa
//                [15] break
//                [16] notes

            session.setId(record.get(2));
            session.setType(record.get(0));
            session.setRoom(record.get(8));
            session.setDay(record.get(9));
            session.setStart(dateFormatter(record.get(10)));
            session.setDuration(record.get(12));

            // Ignore session without id
            if(session.getId().equals("")) {
                continue;
            }

            sessions.put(session.getId(), session);
        }

        in.close();

        return new SessionsDetailsReaderImpl(speakers, sessions);

    }

    public String dateFormatter(String strDate) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm:ss aa");
            Date date = sdf1.parse(strDate);

            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
            return sdf2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}

