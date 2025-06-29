package com.gamingroom.gameauth;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

/*
* GameAuthConfiguration for authorization configuration
*/
public class GameAuthConfiguration extends Configuration {
	
	 @Valid
	  @NotNull
	  private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

	  @JsonProperty("jerseyClient")
	  public JerseyClientConfiguration getJerseyClientConfiguration() {
	    return jerseyClient;
	  }

	  @JsonProperty("jerseyClient")
	  public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
	    this.jerseyClient = jerseyClient;
	  }

}