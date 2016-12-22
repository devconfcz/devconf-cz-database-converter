package cz.devconf.converter;

import cz.devconf.model.Speaker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Converter {

    public SessionsReader readSpeakersFrom(String fileName) throws IOException {
        Map<String, Speaker> speakers = new LinkedHashMap<String, Speaker>();

        Reader in = new FileReader(fileName);

        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

        // [0] name
        // [1] country
        // [2] bio
        // [3] org
        // [4] size
        // [5] email
        // [6] avatar
        // [7] twitter

        for (CSVRecord record : records) {
            Speaker speaker = new Speaker();
            speaker.setId(Speaker.generateIdFromEmail(record.get(5)));
            speaker.setName(record.get(0));
            speaker.setEmail(record.get(5).toLowerCase());
            speaker.setCountry(record.get(1));
            speaker.setAvatar(record.get(6));
            speaker.setOrganization(record.get(3));
            speaker.setBio(record.get(2));
            speaker.setTwitter(record.get(7));

            speakers.put(speaker.getId(), speaker);
        }

        in.close();

        return new SessionsReaderImpl(speakers);
    }

}
