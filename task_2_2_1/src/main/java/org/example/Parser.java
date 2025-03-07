package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.File;
import java.io.IOException;

/**
 * Class for parsing json to JsonClass.
 */
public class Parser {

    /**
     * Class for info from json.
     */
    public static class JsonClass {
        @JsonProperty("cntCookers")
        public int cntCookers;
        @JsonProperty("cntDel")
        public int cntDel;
        @JsonProperty("maxCap")
        public int maxCap;
        @JsonProperty("cntOrders")
        public int cntOrders;
        @JsonProperty("orders")
        @JsonDeserialize(as=Order[].class)
        public Order orders[];
    }

    /**
     * Static method to get info.
     *
     * @param fileName name of json file
     * @return jsonClass object with info from json
     */
    public static JsonClass getConf(String fileName) {
        File file = new File(fileName);

        JsonClass jsonClass;

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonClass = mapper.readValue(file, JsonClass.class);
        } catch (JsonMappingException e) {
            System.out.println("Runtime");
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            System.out.println("Parse");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IO");
            throw new RuntimeException(e);
        }

        return jsonClass;
    }

}
