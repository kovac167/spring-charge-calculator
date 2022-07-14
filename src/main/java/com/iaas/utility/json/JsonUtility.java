package com.iaas.utility.json;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.load.Dereferencing;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JsonUtility {

	public boolean validate(String jsonInput, String jsonSchemaName) throws IOException, ProcessingException {
		LoadingConfiguration cfg = LoadingConfiguration
				.newBuilder()
				.dereferencing(Dereferencing.INLINE)
				.freeze();

		JsonSchemaFactory factory = JsonSchemaFactory
				.newBuilder()
				.setLoadingConfiguration(cfg).freeze();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonInputNode = mapper.readTree(jsonInput);

		InputStream jsonSchemaFile = new ClassPathResource(jsonSchemaName).getInputStream();
		JsonNode jsonSchemaNode = mapper.readTree(jsonSchemaFile);
		JsonSchema jsonSchema = factory.getJsonSchema(jsonSchemaNode);

		ProcessingReport report = jsonSchema.validate(jsonInputNode);
		return report.isSuccess();
	}
}
