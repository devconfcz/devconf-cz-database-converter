package cz.devconf.converter;

import java.io.IOException;

public interface SessionsReader {

    SessionsDetailsReader readSessionsFrom(String fileName) throws IOException;

}
