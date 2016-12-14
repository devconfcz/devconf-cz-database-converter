package cz.devconf;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        SessionConverter sessionConverter = new SessionConverter();
        sessionConverter
                .readCSVFrom("/Users/passos/Downloads/devconf.csv")
                .createJSON()
                .writeIn("/Users/passos/Desktop/sessions.json");

    }

}
