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
        // [1] email
        // [2] country
        // [3] bio
        // [4] org
        // [5] size
        // [6] avatar
        // [7] twitter

        for (CSVRecord record : records) {
            Speaker speaker = new Speaker();
            speaker.setId(Speaker.generateIdFromEmail(record.get(1)));
            speaker.setName(record.get(0));
            speaker.setEmail(record.get(1).toLowerCase());
            speaker.setCountry(record.get(2));
            speaker.setAvatar(record.get(6));
            speaker.setOrganization(record.get(4));
            speaker.setBio(record.get(3));
            speaker.setTwitter(record.get(7));

            speakers.put(speaker.getId(), speaker);
        }

        in.close();

        return new SessionsReaderImpl(speakers);
    }

}
