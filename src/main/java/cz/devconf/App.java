package cz.devconf;

import cz.devconf.converter.Converter;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        new Converter()
                .readSpeakersFrom("/Users/passos/Downloads/devconfcz-speakers.csv")
                .readSessionsFrom("/Users/passos/Downloads/devconfcz-sessions.csv")
                .readSessionsDetailsFrom("/Users/passos/Downloads/devconfcz-sessions2.csv")
                .convertAndWriteIn("/Users/passos/Desktop/devconf.json");
    }

}
