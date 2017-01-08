package cz.devconf.model;

import java.util.List;
import java.util.Map;

public class Conference {

    private CheckAccess checkAccess;
    private Map<String, String> admins;
    private List<Room> rooms;
    private List<Track> tracks;
    private Map<String, Speaker> speakers;
    private Map<String, Session> sessions;

    public CheckAccess getCheckAccess() {
        return checkAccess;
    }

    public void setCheckAccess(CheckAccess checkAccess) {
        this.checkAccess = checkAccess;
    }

    public Map<String, String> getAdmins() {
        return admins;
    }

    public void setAdmins(Map<String, String> admins) {
        this.admins = admins;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Map<String, Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Map<String, Speaker> speakers) {
        this.speakers = speakers;
    }

    public Map<String, Session> getSessions() {
        return sessions;
    }

    public void setSessions(Map<String, Session> sessions) {
        this.sessions = sessions;
    }
}
