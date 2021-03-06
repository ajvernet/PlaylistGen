package org.ssa.ironyard.model;

public class AuthToken {
    private String access_token;
    private String token_type;

    public AuthToken(String access_token, String token_type) {
	this.access_token = access_token;
	this.token_type = token_type;
    }

    public AuthToken() {
    }

    public String getAccess_token() {
	return access_token;
    }

    public void setAccess_token(String access_token) {
	this.access_token = access_token;
    }

    public String getToken_type() {
	return token_type;
    }

    public void setToken_type(String token_type) {
	this.token_type = token_type;
    }

    @Override
    public String toString()
    {
        return "AuthToken{" + "access_token=" + access_token + ", token_type=" + token_type + '}';
    }
    
    
}
