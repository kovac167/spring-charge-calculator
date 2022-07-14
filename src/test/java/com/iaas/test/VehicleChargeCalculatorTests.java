package com.iaas.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.iaas.VehicleChargeCalculator;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = VehicleChargeCalculator.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleChargeCalculatorTests {

	private final String testProperties = "{\"vehicle\":\"hummer\",\"percent\":40,\"state\":\"MI\"}";

	@Autowired
	private MockMvc mvc;

	@Order(0)
	@Test
	void testGetHealth() {
		try {
			mvc.perform(
					get("/actuator/health"))
			.andDo(print())
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Order(1)
	@Test
	void testGetApiDocs() {
		try {
			mvc.perform(
					get("/api-docs"))
			.andDo(print())
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Order(2)
	@Test
	void testGetSwaggerUi() {
		try {
			mvc.perform(
					get("/swagger-ui"))
			.andDo(print())
			.andExpect(status().isFound());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Order(3)
	@Test
	void testPostCalculateRequest() {
		try {
			JSONObject content = new JSONObject(testProperties);

			mvc.perform(
					post("/api/calculate_request")
					.contentType(MediaType.APPLICATION_JSON)
					.content(content.toString())
					.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.result", notNullValue()))
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
