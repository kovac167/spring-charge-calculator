package com.iaas.utility.rest;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestUtility {

	public ResponseEntity<String> get(String uri, HttpHeaders headers) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	}

	public ResponseEntity<String> post(String uri, HttpHeaders headers, JSONObject body) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		HttpEntity<String> entity;

		if (body == null) {
			entity = new HttpEntity<>(headers);
		} else {
			entity = new HttpEntity<>(body.toString(), headers);
		}

		return restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
	}

	public ResponseEntity<String> delete(String uri, HttpHeaders headers, JSONObject body) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		HttpEntity<String> entity;

		if (body == null) {
			entity = new HttpEntity<>(headers);
		} else {
			entity = new HttpEntity<>(body.toString(), headers);
		}

		return restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class);
	}
}
