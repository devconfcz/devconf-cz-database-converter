package cz.devconf;

import cz.devconf.converter.Converter;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        new Converter()
                .readSpeakersFrom("/Users/passos/Downloads/speakers.csv")
                .readSessionsFrom("/Users/passos/Downloads/sessions.csv")
                .readSessionsDetailsFrom("/Users/passos/Downloads/sessions2.csv")
                .convertAndWriteIn("/Users/passos/Desktop/devconf.json");
    }

}
