
package com.chatbot.AI.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Argument {

   
    private String textValue;
    
    private String rawText;
                              
    private String name;
    
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

                              
    public String getTextValue() {
        return textValue;
    }

                              
    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

                              
    public String getRawText() {
        return rawText;
    }

                                                             
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

                             
    public String getName() {
        return name;
    }

                                                            
    public void setName(String name) {
        this.name = name;
    }

    
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("textValue", textValue).append("rawText", rawText).append("name", name).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(rawText).append(additionalProperties).append(name).append(textValue).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Argument) == false) {
            return false;
        }
        Argument rhs = ((Argument) other);
        return new EqualsBuilder().append(rawText, rhs.rawText).append(additionalProperties, rhs.additionalProperties).append(name, rhs.name).append(textValue, rhs.textValue).isEquals();
    }

}
