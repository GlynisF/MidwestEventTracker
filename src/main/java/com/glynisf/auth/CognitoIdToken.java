package com.glynisf.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

	@JsonProperty("email")
	private String email;

	@JsonProperty("birthdate")
	private String birthdate;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("given_name")
	private String givenName;

	@JsonProperty("family_name")
	private String familyName;

	@JsonProperty("password")
	private String password;

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


	public String getEmail() {
		return email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getGender() {
		return gender;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getPassword() {
		return password;
	}
}