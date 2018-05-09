package org.prof_itgroup.json.driver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "app",
        "doc",
        "system"
})
public class Driver {

    @JsonProperty("app")
    private String app;
    @JsonProperty("doc")
    private Doc doc;
    @JsonProperty("system")
    private String system;

    @JsonProperty("app")
    public String getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(String app) {
        this.app = app;
    }

    @JsonProperty("doc")
    public Doc getDoc() {
        return doc;
    }

    @JsonProperty("doc")
    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    @JsonProperty("system")
    public void setSystem (String system) {
        this.system=system;
    }
}
