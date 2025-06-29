package com.gamingroom.gameauth;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gamingroom.gameauth.auth.GameAuthenticator;
import com.gamingroom.gameauth.auth.GameAuthorizer;
import com.gamingroom.gameauth.auth.GameUser;

import com.gamingroom.gameauth.controller.GameUserRESTController;
import com.gamingroom.gameauth.controller.RESTClientController;

import com.gamingroom.gameauth.healthcheck.AppHealthCheck;
import com.gamingroom.gameauth.healthcheck.HealthCheckController;


/*
* GameAuthApplication main Application
*/
public class GameAuthApplication extends Application<Configuration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameAuthApplication.class);

	@Override
	public void initialize(Bootstrap<Configuration> b) {
	}

	@Override
	public void run(Configuration c, Environment e) throws Exception 
	{
		
		LOGGER.info("Registering REST resources");
 
		// Done: Create io.dropwizard.client.JerseyClientBuilder instance and give it io.dropwizard.setup.Environment reference (based on BasicAuth Security Example)
		
	    // ✅ Create JerseyClient for use in controllers and health checks
	    Client client = new JerseyClientBuilder(e).build("DemoRESTClient");
	 
	    // Done: register GameUserRESTController (based on BasicAuth Security Example)
	    // ✅ Register GameUser REST controller
	    e.jersey().register(new GameUserRESTController(e.getValidator()));

	    // ✅ Register REST client controller with the client
	    e.jersey().register(new RESTClientController(client));

		// Application health check
		e.healthChecks().register("APIHealthCheck", new AppHealthCheck(client));

		// Run multiple health checks
		e.jersey().register(new HealthCheckController(e.healthChecks()));
		
		 //****** Dropwizard security ***********/
		e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<GameUser>()
                .setAuthenticator(new GameAuthenticator())
                .setAuthorizer(new GameAuthorizer())
                //.setRealm("App Security")// Replaced label with "BASIC-AUTH-REALM"
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(GameUser.class));
        e.jersey().register(RolesAllowedDynamicFeature.class);
        
	}

	public static void main(String[] args) throws Exception {
		new GameAuthApplication().run(args);
	}
}