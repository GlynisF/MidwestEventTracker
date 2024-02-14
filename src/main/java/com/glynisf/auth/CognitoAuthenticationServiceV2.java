package com.glynisf.auth;


import com.glynisf.eventtracker.util.PropertiesLoader;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;


import javax.annotation.processing.Generated;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Generated(value="com.amazonaws:aws-java-sdk-code-generator")
public class CognitoAuthenticationServiceV2 implements PropertiesLoader {

	private static String USER_POOL_ID;
	private static String CLIENT_ID;
	public static String CLIENT_SECRET;
	private Properties properties;

	public CognitoAuthenticationServiceV2() {
		this.properties = new Properties(loadProperties("/cognito.properties"));
		this.USER_POOL_ID = properties.getProperty("poolId");
		this.CLIENT_ID = properties.getProperty("client.id");
		this.CLIENT_SECRET = properties.getProperty("client.secret");

	}

	public static AuthenticationResultType authenticateUser(CognitoIdToken token) {
		CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder().build();
		String randomString = generateRandomString(); // Implement this method to generate a random string
		String secretHash = calculateSecretHash(CLIENT_ID, CLIENT_SECRET, randomString);
		Map<String, String> authParameters = new HashMap<>();
		authParameters.put("USERNAME", token.getCognitoUsername());
		authParameters.put("PASSWORD", token.getPassword());
		authParameters.put("SECRET_HASH", calculateSecretHash(CLIENT_ID, CLIENT_SECRET, token.getCognitoUsername()));


	try {
		InitiateAuthResponse initiateAuthResponse = cognitoClient.initiateAuth(
				InitiateAuthRequest.builder()
						.clientId(CLIENT_ID)
						.authFlow(AuthFlowType.USER_PASSWORD_AUTH)
						.authParameters(authParameters)
						.build()
		);
		return initiateAuthResponse.authenticationResult();
	} catch (CognitoIdentityProviderException e) {
		System.err.println("Cognito Error: " + e.awsErrorDetails().errorMessage());
		throw e;
	}
	}

	private static String calculateSecretHash(String clientId, String clientSecret, String randomString) {
		String data = CLIENT_ID + CLIENT_SECRET + randomString;
		try {
			Mac sha256HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(CLIENT_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			sha256HMAC.init(secretKey);
			byte[] hashBytes = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return new String(Base64.getEncoder().encode(hashBytes), StandardCharsets.UTF_8);
		} catch (Exception e) {
			// Handle exception
			return null;
		}
	}

	private static String generateRandomString() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[32]; // Adjust the length as needed
		random.nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes);
	}

	// Respond to an authentication challenge.
	public static void adminRespondToAuthChallenge(CognitoIdentityProviderClient identityProviderClient, String userName, String clientId, String mfaCode, String session) {
		System.out.println("SOFTWARE_TOKEN_MFA challenge is generated");
		Map<String, String> challengeResponses = new HashMap<>();

		challengeResponses.put("USERNAME", userName);
		challengeResponses.put("SOFTWARE_TOKEN_MFA_CODE", mfaCode);

		RespondToAuthChallengeRequest respondToAuthChallengeRequest = RespondToAuthChallengeRequest.builder()
				.challengeName(ChallengeNameType.SOFTWARE_TOKEN_MFA)
				.clientId(clientId)
				.challengeResponses(challengeResponses)
				.session(session)
				.build();

		RespondToAuthChallengeResponse respondToAuthChallengeResult = identityProviderClient.respondToAuthChallenge(respondToAuthChallengeRequest);
		System.out.println("respondToAuthChallengeResult.getAuthenticationResult()" + respondToAuthChallengeResult.authenticationResult());
	}





}