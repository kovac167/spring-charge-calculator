package com.iaas.utility.opendata;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.iaas.utility.json.JsonUtility;
import com.iaas.utility.rest.RestUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Component
@Log
@RequiredArgsConstructor
public class OpendataUtility {

	@Value("${app.opendata.host}")
	private String opendataHost;

	@Value("${app.opendata.key}")
	private String opendataKey;

	private final RestUtility restUtility;

	private final JsonUtility jsonUtility;

	public JSONArray buildStats() throws IOException, ProcessingException {
		JSONObject response = queryOpendata().getJSONObject("response");
		Integer length = response.getInt("total");
		log.info("length of opendata response: " + length);

		JSONArray stats = extractStatsFromResponse(response);
		length = stats.length();
		log.info("length of valid stats: " + length);
		return stats;
	}

	private JSONObject queryOpendata() throws IOException, ProcessingException {
		Date date = Date.from(ZonedDateTime.now().minusMonths(4).toInstant());
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		String targetPeriod = dt.format(date);

		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host(opendataHost)
				.path("/v2/electricity/retail-sales/data")
				.query("api_key=" + opendataKey)
				.query("frequency=monthly")
				.query("start=" + targetPeriod)
				.query("data[]=price")
				.build();

		String uriString = uri.toUriString();
		ResponseEntity<String> response = restUtility.get(uriString, null);
		String responseBody = response.getBody();

		Boolean valid = jsonUtility.validate(responseBody, "jsonschema/opendata/electricity.json");
		if (Boolean.FALSE.equals(valid)) throw new IllegalStateException("response did not pass schema validation!");
		return new JSONObject(responseBody);
	}

	private JSONArray extractStatsFromResponse(JSONObject response) throws IOException, ProcessingException {
		JSONArray validDataset = new JSONArray();
		JSONArray dataset = response.getJSONArray("data");

		for (int i = 0; i < dataset.length(); i++) {
			JSONObject stat = dataset.getJSONObject(i);
			Boolean valid = jsonUtility.validate(stat.toString(), "jsonschema/opendata/electricity-stat.json");
			if (Boolean.TRUE.equals(valid)) validDataset.put(stat);
		}
		return validDataset;
	}
}
