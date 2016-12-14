package cz.devconf;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        SessionConverter sessionConverter = new SessionConverter();
        sessionConverter
                .readCSVFrom("/Users/passos/Downloads/sessions.csv")
                .createJSON()
                .writeIn("/Users/passos/Desktop/sessions.json");

        SpeakerConverter speakerConverter = new SpeakerConverter();
        speakerConverter
                .readCSVFrom("/Users/passos/Downloads/speakers.csv")
                .createJSON()
                .writeIn("/Users/passos/Desktop/speakers.json");

    }

}
