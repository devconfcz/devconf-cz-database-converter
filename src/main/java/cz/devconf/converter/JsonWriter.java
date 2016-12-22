package cz.devconf.converter;

import java.io.IOException;

public interface JsonWriter {

    void convertAndWriteIn(String fileName) throws IOException;

}
