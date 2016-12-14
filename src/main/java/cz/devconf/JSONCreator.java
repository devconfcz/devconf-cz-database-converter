package cz.devconf;

import org.json.simple.JSONObject;

/**
 * Helper to structures the JSON nodes in differents ways
 */
public interface JSONCreator {

    void add(JSONObject object);

    JSONObject createNode();

}
