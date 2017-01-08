package cz.devconf.converter;

import com.google.gson.GsonBuilder;
import cz.devconf.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        admins.put("2Qqco5czNtTJbmpxisSs37aWgKj2", "dpassos@redhat.com");
        admins.put("3fj9xJUtNzec5M7NruIxOuShKpd2", "cward@redhat.com");
        admins.put("9YN2bZBpBsSfeGHzpMYXeAeH4Mh2", "kpiwko@redhat.com");
        admins.put("QDDaKB1Zzqbr2FvY4RzXfr2nha03", "jkortus@redhat.com");
        admins.put("aKWuwECQo9YZB3sXnZVvGSHuetl2", "jridky@redhat.com");
        admins.put("yhxRsebxBlfDXUVZhfjjUFe8wXz2", "daniel@passos.me");

        Conference conference = new Conference();
        conference.setAdmins(admins);
        conference.setTracks(tracks());
        conference.setRooms(rooms());
        conference.setSpeakers(speakers);
        conference.setSessions(sessions);

        Writer writer = new FileWriter(fileName);
        new GsonBuilder().setPrettyPrinting().create().toJson(conference, writer);
        writer.close();
    }

    private List<Room> rooms() {
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(new Room("D105", "Talk"));
        rooms.add(new Room("D206", "Talk"));
        rooms.add(new Room("D207", "Talk"));
        rooms.add(new Room("C228", "Talk"));
        rooms.add(new Room("E104", "Talk"));
        rooms.add(new Room("E105", "Talk"));
        rooms.add(new Room("E112", "Talk"));
        rooms.add(new Room("G202", "Talk"));
        rooms.add(new Room("A112", "Workshop"));
        rooms.add(new Room("A113", "Workshop"));
        rooms.add(new Room("A218", "Workshop"));
        rooms.add(new Room("C236", "Workshop"));

        return rooms;
    }

    private List<Track> tracks() {
        List<Track> tracks = new ArrayList<Track>();

        tracks.add(new Track(".Net", "#6b7275"));
        tracks.add(new Track("Agile", "#00c853"));
        tracks.add(new Track("Cloud", "#448aff"));
        tracks.add(new Track("Config Mgmt", "#990f8e"));
        tracks.add(new Track("Containers", "#5e35b1"));
        tracks.add(new Track("Desktop", "#9b0101"));
        tracks.add(new Track("DevOps", "#01579b"));
        tracks.add(new Track("DevTools", "#0f9977"));
        tracks.add(new Track("Discussion, Agile", "#000000"));
        tracks.add(new Track("Fedora", "#3c6eb4"));
        tracks.add(new Track("Hackfest", "#b3e5fc"));
        tracks.add(new Track("Hackfests", "#b3e5fc"));
        tracks.add(new Track("JUDCon", "#007e8d"));
        tracks.add(new Track("Keynote", "#05050a"));
        tracks.add(new Track("Lightning", "#000000"));
        tracks.add(new Track("Linux", "#009688"));
        tracks.add(new Track("Meetup", "#d57800"));
        tracks.add(new Track("Meetups", "#000000"));
        tracks.add(new Track("Microservices", "#f57f17"));
        tracks.add(new Track("Networking", "#b52b00"));
        tracks.add(new Track("OpenShift", "#2e7d32"));
        tracks.add(new Track("OpenStack", "#757575"));
        tracks.add(new Track("Security", "#008ccb"));
        tracks.add(new Track("Storage", "#6d4c41"));
        tracks.add(new Track("Testing", "#006064"));
        tracks.add(new Track("Virtualization", "#01579b"));

        return tracks;
    }

}
