package cz.devconf;

import java.io.IOException;

public class Starter {

    public static void main(String[] args) throws IOException {

        Converter converter = new Converter();
        converter
                .readCSVFrom("/Users/passos/Downloads/devconf.csv")
                .createSessionsByDayJSON()
                .writeIn("/Users/passos/Desktop/sessions.json");

    }

}
