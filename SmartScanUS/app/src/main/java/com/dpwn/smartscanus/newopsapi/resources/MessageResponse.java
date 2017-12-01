package com.dpwn.smartscanus.newopsapi.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fshamim on 09.10.14.
 */
public class MessageResponse {

    /**
     * Default message mapped to the response submitted by the API.
     */
    private String msg;

    /**
     * Map of data that may be required to submit to the bus.
     */
    private Map<String, String> parameterValues;

    public MessageResponse() {
        parameterValues = new HashMap<>();
    }

    /**
     * Create a simple msg response object with given message data
     * @param msg message
     */
    public MessageResponse(String msg) {
        this();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(Map<String, String> parameterValues) {
        this.parameterValues = parameterValues;
    }
}
