package cz.devconf.model;

import java.util.Map;

public class Conference {

    private Map<String, String> admins;
    private Map<String, Speaker> speakers;
    private Map<String, Session> sessions;
    private Map<String, Map<String, String>> sessionSpeakers;

    public Map<String, String> getAdmins() {
        return admins;
    }

    public void setAdmins(Map<String, String> admins) {
        this.admins = admins;
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

    public Map<String, Map<String, String>> getSessionSpeakers() {
        return sessionSpeakers;
    }

    public void setSessionSpeakers(Map<String, Map<String, String>> sessionSpeakers) {
        this.sessionSpeakers = sessionSpeakers;
    }
}
