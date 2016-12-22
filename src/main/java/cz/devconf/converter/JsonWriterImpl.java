package cz.devconf.converter;

import com.google.gson.GsonBuilder;
import cz.devconf.model.Conference;
import cz.devconf.model.Session;
import cz.devconf.model.Speaker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonWriterImpl implements JsonWriter {

    private Map<String, Speaker> speakers;
    private Map<String, Session> sessions;

    public JsonWriterImpl(Map<String, Speaker> speakers, Map<String, Session> sessions) {
        this.speakers = speakers;
        this.sessions = sessions;
    }

    public void convertAndWriteIn(String fileName) throws IOException {
        Map<String, String> admins = new LinkedHashMap<String, String>();
        admins.put("QDDaKB1Zzqbr2FvY4RzXfr2nha03", "Jaroslav Kortus");
        admins.put("yhxRsebxBlfDXUVZhfjjUFe8wXz2", "Daniel Passos");

        Conference conference = new Conference();
        conference.setAdmins(admins);
        conference.setSpeakers(speakers);
        conference.setSessions(sessions);

        Writer writer = new FileWriter(fileName);
        new GsonBuilder().setPrettyPrinting().create().toJson(conference, writer);
        writer.close();
    }

}
