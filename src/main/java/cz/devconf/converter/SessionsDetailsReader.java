package cz.devconf.converter;

import java.io.IOException;

public interface SessionsDetailsReader {

    JsonWriterImpl readSessionsDetailsFrom(String fileName) throws IOException;

}
