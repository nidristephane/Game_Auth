package com.gamingroom.gameauth.auth;

import io.dropwizard.auth.Authorizer;


/*
* GameAuthenticator for gameuser authorization
*/
public class GameAuthorizer implements Authorizer<GameUser> 
{
    @Override
    public boolean authorize(GameUser user, String role) {
    	
        // Done: Finish the authorize method based on BasicAuth Security Example
    	return user.getRoles() != null && user.getRoles().contains(role);
    	
    }
}