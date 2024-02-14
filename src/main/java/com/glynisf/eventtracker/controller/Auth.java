package com.glynisf.eventtracker.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glynisf.auth.*;
import com.glynisf.eventtracker.entity.User;
import com.glynisf.eventtracker.persistence.GenericDao;
import com.glynisf.eventtracker.util.PropertiesLoader;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@WebServlet(
		urlPatterns = {"/auth"}
)
// TODO if something goes wrong it this process, route to an error page. Currently, errors are only caught and logged.
/**
 * Inspired by: https://stackoverflow.com/questions/52144721/how-to-get-access-token-using-client-credentials-using-java-code
 */

public class Auth extends HttpServlet implements PropertiesLoader, Serializable {

	private Properties properties;
	private static String CLIENT_ID;
	private static String CLIENT_SECRET;
	private static String OAUTH_URL;
	private static String LOGIN_URL;
	private static String REDIRECT_URL;
	private static String REGION;
	private static String POOL_ID;
	private User user;
	Keys jwks;

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			this.properties = (Properties) getServletContext().getAttribute("cognitoProperties");
			CLIENT_ID = properties.getProperty("client.id");
			CLIENT_SECRET = properties.getProperty("client.secret");
			OAUTH_URL = properties.getProperty("oauthURL");
			LOGIN_URL = properties.getProperty("loginURL");
			REDIRECT_URL = properties.getProperty("redirectURL");
			REGION = properties.getProperty("region");
			POOL_ID = properties.getProperty("poolId");
		} catch (Exception e) {
			logger.error("Error loading properties" + e.getMessage(), e);
		}
		loadKey();

	}
	/**
	 * Gets the auth code from the request and exchanges it for a token containing user info.
	 * @param req servlet request
	 * @param resp servlet response
	 * @throws ServletException servlet init
	 * @throws IOException error reading or writing to page
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String authCode = req.getParameter("code");
		System.out.println(authCode);

		if (authCode == null) {
			getDispatcher("index.jsp", req, resp);
		} else {
			HttpRequest authRequest = buildAuthRequest(authCode);
			try {
				TokenResponse tokenResponse = getToken(req, authRequest);
				checkUser(req, tokenResponse);
			} catch (InterruptedException e) {
				String message = "Error getting or validating the token from Cognito oauth url";
				logger.error(message + e.getMessage(), e);
				req.setAttribute("message", message);
				getDispatcher("error.jsp", req, resp);
			}
		}
		getDispatcher("/homepage.jsp", req, resp);

	}

	public List<User> checkIfUserExists(TokenResponse tokenResponse) throws IOException {
		DecodedJWT jwt = validate(tokenResponse);
		String username = jwt.getClaim("cognito:username").asString();
		String password = jwt.getClaim("sub").asString();
		GenericDao<User, Serializable> userDao = new GenericDao(User.class);
		List<User> userList = userDao.getByPropertiesLike("userName", username, "password", password);
		System.out.println(userList);
		return userList;
	}

	public void checkUser(HttpServletRequest req, TokenResponse tokenResponse) throws IOException {
		List<User> userList = checkIfUserExists(tokenResponse);
		HttpSession session = req.getSession();
		if (userList.isEmpty()) {
			user = addCognitoUser(tokenResponse);
			session.setAttribute("user", user);
		} else {
			user = getUserFrmListOfUsers(userList);
			int id = userList.get(0).getId();
			session.setAttribute("user", user);
			session.setAttribute("userId", id);
		}
	}

	public User getUserFrmListOfUsers(List<User> userList) {
		for (User user : userList) {
			return user;
		}
		return null;
	}

	public RequestDispatcher getDispatcher(String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(path);
		dispatcher.forward(req, resp);
		return dispatcher;
	}

	private static String formatDateString(String dateString, String inputFormat, String outputFormat) {
		// Parse the original date string
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputFormat);
		LocalDate originalDate = LocalDate.parse(dateString, inputFormatter);

		// Format the LocalDate into the desired output format
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
		return originalDate.format(outputFormatter);
	}

	/**
	 * Sends the request for a token to Cognito and maps the response
	 * @param authRequest the request to the oauth2/token url in cognito
	 * @return response from the oauth2/token endpoint which should include id token, access token and refresh token
	 * @throws IOException error getting response
	 * @throws InterruptedException error with threads
	 */
	private TokenResponse getToken(HttpServletRequest req, HttpRequest authRequest) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<?> response = null;

		response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());

		System.out.println("Response headers: " + response.headers().toString());
		System.out.println("Response body: " + response.body().toString());

		ObjectMapper mapper = new ObjectMapper();
		TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
		req.setAttribute("id_token", tokenResponse.getIdToken());
		req.setAttribute("access_token", tokenResponse.getAccessToken());
		System.out.println(tokenResponse.getIdToken());
		System.out.println(tokenResponse.getAccessToken());
		return tokenResponse;

	}
	/**
	 * Get values out of the header to verify the token is legit. If it is legit, get the claims from it, such
	 * as username.
	 * @param tokenResponse
	 * @return
	 * @throws IOException
	 */
	private DecodedJWT validate(TokenResponse tokenResponse) throws IOException, JWTDecodeException {
		ObjectMapper mapper = new ObjectMapper();
		CognitoTokenHeader tokenHeader = mapper.readValue(CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(), CognitoTokenHeader.class);
		CognitoIdToken idToken = mapper.readValue(CognitoJWTParser.getPayload(tokenResponse.getIdToken()).toString(), CognitoIdToken.class);

		// Header should have kid and alg- https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-using-the-id-token.html
		String keyId = tokenHeader.getKid();
		String alg = tokenHeader.getAlg();

		// todo pick proper key from the two - it just so happens that the first one works for my case
		// Use Key's N and E
		BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getN()));
		BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getE()));

		// TODO the following is "happy path", what if the exceptions are caught?
		// Create a public key
		PublicKey publicKey = null;
		try {
			publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
		} catch (InvalidKeySpecException e) {
			logger.error("Invalid Key Error " + e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Algorithm Error " + e.getMessage(), e);
		}

		// get an algorithm instance
		Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

		// Verify ISS field of the token to make sure it's from the Cognito source
		String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);

		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer(iss)
				.withClaim("token_use", "id")
				.acceptLeeway(120)
				.acceptExpiresAt(Instant.now().plusSeconds(120).toEpochMilli())// make sure you're verifying id token
				.build();

		// Verify the token
		DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());


		return jwt;

	}

	public User addCognitoUser(TokenResponse tokenResponse) throws IOException {
		Map<String, String> map = getUserCredentials(tokenResponse);
		GenericDao<User, Serializable> userDao =  new GenericDao<>(User.class);

		User user = new User(map.get("given_name"),
				map.get("family_name"),
				map.get("username"),
				map.get("email"),
				0,
				map.get("sub"),
				LocalDate.parse(map.get("birthdate")),
				map.get("gender"));

		int id = userDao.insert(user);

		return user;

	}

	public Map<String, String> getUserCredentials(TokenResponse tokenResponse) throws IOException {
		Map<String, String> cognitoMap = new HashMap<>();

		DecodedJWT jwt = validate(tokenResponse);

		cognitoMap.put("username", jwt.getClaim("cognito:username").asString());
		cognitoMap.put("email", jwt.getClaim("email").asString());
		cognitoMap.put("sub", jwt.getClaim("sub").asString());
		cognitoMap.put("given_name", jwt.getClaim("given_name").asString());
		cognitoMap.put("family_name", jwt.getClaim("family_name").asString());
		cognitoMap.put("birthdate", formatDateString(jwt.getClaim("birthdate").asString(), "MM/dd/yyy", "yyy-MM-dd"));
		cognitoMap.put("gender", jwt.getClaim("gender").asString());
		cognitoMap.put("password", jwt.getClaim("password").asString());

		return cognitoMap;
	}

	public HashMap<String, String> buildParamsMap(String authCode) {
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("grant_type", "authorization_code");
		parameters.put("client-secret", CLIENT_SECRET);
		parameters.put("client_id", CLIENT_ID);
		parameters.put("code", authCode);
		parameters.put("redirect_uri", REDIRECT_URL);

		return parameters;
	}

	/** Create the auth url and use it to build the request.
	 *
	 * @param authCode auth code received from Cognito as part of the login process
	 * @return the constructed oauth request
	 */
	private HttpRequest buildAuthRequest(String authCode) {
		String keys = CLIENT_ID + ":" + CLIENT_SECRET;

		HashMap<String, String> parameters = buildParamsMap(authCode);

		String form = parameters.keySet().stream()
				.map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
				.collect(Collectors.joining("&"));

		String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OAUTH_URL))
				.headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
				.POST(HttpRequest.BodyPublishers.ofString(form)).build();

		return request;
	}

	/**
	 * Gets the JSON Web Key Set (JWKS) for the user pool from cognito and loads it
	 * into objects for easier use.
	 *
	 * JSON Web Key Set (JWKS) location: https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
	 * Demo url: https://cognito-idp.us-east-2.amazonaws.com/us-east-2_XaRYHsmKB/.well-known/jwks.json
	 *
	 * @see Keys
	 * @see KeysItem
	 */
	private void loadKey() {
		ObjectMapper mapper = new ObjectMapper();

		try {
			URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
			File jwksFile = new File("jwks.json");
			FileUtils.copyURLToFile(jwksURL, jwksFile);
			jwks = mapper.readValue(jwksFile, Keys.class);
			System.out.println("Keys are loaded. Here's e: " + jwks.getKeys().get(0).getE());
		} catch (IOException ioException) {
			logger.error("Cannot load json..." + ioException.getMessage(), ioException);
		} catch (Exception e) {
			logger.error("Error loading json" + e.getMessage(), e);
		}
	}


}