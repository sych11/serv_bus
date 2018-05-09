package org.prof_itgroup.it_juridique.json.result;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"success"
})

public class ServiceResult {

	@JsonProperty("success")
	private Boolean succes;
    @JsonIgnore
	private Map<String,Object> additionalProperties= new HashMap<String, Object>();

    @JsonProperty("success")
	public Boolean getSucces() {
		return succes;
	}

	@JsonProperty("success")
	public void setSucces(Boolean succes) {
		this.succes = succes;
	}

	@JsonAnyGetter
	public Map<String,Object> getAdditionalProperties() {
    	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperties (String name, Object value) {
    	this.additionalProperties.put(name, value);
	}

}