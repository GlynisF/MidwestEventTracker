package com.glynisf.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CognitoIdToken{

	@JsonProperty("at_hash")
	private String atHash;

	@JsonProperty("origin_jti")
	private String originJti;

	@JsonProperty("sub")
	private String sub;

	@JsonProperty("aud")
	private String aud;

	@JsonProperty("token_use")
	private String tokenUse;

	@JsonProperty("auth_time")
	private int authTime;

	@JsonProperty("iss")
	private String iss;

	@JsonProperty("cognito:username")
	private String cognitoUsername;

	@JsonProperty("exp")
	private int exp;

	@JsonProperty("iat")
	private int iat;

	@JsonProperty("jti")
	private String jti;

	public String getAtHash(){
		return atHash;
	}

	public String getOriginJti(){
		return originJti;
	}

	public String getSub(){
		return sub;
	}

	public String getAud(){
		return aud;
	}

	public String getTokenUse(){
		return tokenUse;
	}

	public int getAuthTime(){
		return authTime;
	}

	public String getIss(){
		return iss;
	}

	public String getCognitoUsername(){
		return cognitoUsername;
	}

	public int getExp(){
		return exp;
	}

	public int getIat(){
		return iat;
	}

	public String getJti(){
		return jti;
	}
}