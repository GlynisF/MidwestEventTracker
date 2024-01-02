package com.glynisf.eventtracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class ObjectMapperHelper {

	private final Logger logger = (Logger) LogManager.getLogger(ObjectMapperHelper.class);
	private final ObjectMapper mapper;

	public ObjectMapperHelper() {
		mapper = new ObjectMapper();
		// To handle unrecognized property exception
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public <T> T getApiResponse(WebTarget target, Class<T> responseType) {
		return target.request(MediaType.APPLICATION_JSON).get().readEntity(responseType);
	}

	public <T> T convertJsonToJava(String response, Class<T> valueType) {
		T result = null;

		try {
			result = mapper.readValue(response, valueType);
		} catch (JsonMappingException e) {
			logger.error("There was a problem with mapping or encoding/decoding");
			logger.debug(e);
		} catch (JsonProcessingException e) {
			logger.error("There was a problem parsing or generating JSON.");
			logger.debug(e);
		}

		return result;
	}
}