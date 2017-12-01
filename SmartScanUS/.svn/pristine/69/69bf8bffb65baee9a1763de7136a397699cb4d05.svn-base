package com.dpwn.smartscanus.newopsapi.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cekangak on 7/8/2015.
 */
public class Status {

    private String ReturnCode;
    private String Message;
    private String Timestamp;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Status() {}

    /**
     *
     * @return
     * The ReturnCode
     */
    @JsonProperty("ReturnCode")
    public String getReturnCode() {
        return ReturnCode;
    }

    /**
     *
     * @param ReturnCode
     * The ReturnCode
     */
    public void setReturnCode(String ReturnCode) {
        this.ReturnCode = ReturnCode;
    }

    /**
     *
     * @return
     * The Message
     */
    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    /**
     *
     * @param Message
     * The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     *
     * @return
     * The Timestamp
     */
    @JsonProperty("Timestamp")
    public String getTimestamp() {
        return Timestamp;
    }

    /**
     *
     * @param Timestamp
     * The Timestamp
     */
    public void setTimestamp(String Timestamp) {
        this.Timestamp = Timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ReturnCode).append(Message).append(Timestamp).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Status) == false) {
            return false;
        }
        Status rhs = ((Status) other);
        return new EqualsBuilder().append(ReturnCode, rhs.ReturnCode).append(Message, rhs.Message)
                .append(Timestamp, rhs.Timestamp).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
