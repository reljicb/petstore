package com.example.api.controllers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.PetstoreApplication;
import com.example.api.repositories.PetRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PetstoreApplication.class)
@WebAppConfiguration
public class PetControllerIntegrationTest
{
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WebApplicationContext webConfig;

	protected MockMvc mvc;

	@Autowired
	private PetRepository petRepo;

	@Before
	public void setup()
	{
		mvc = MockMvcBuilders.webAppContextSetup(webConfig).build();
//		TestUtils.cleanDb(daRepo);
//		prepareTestSesson();
	}

	private static String TEST_PET_NAME = "Miuwah";
	private static String TEST_PET_RASE = "rabbit";
	private static String TEST_PET_OWNER = "Alice";
	private static String TEST_PET_COLOR = "white/gray/beige";

	@Test
	public void testIntegration() throws Exception
	{
		String petUri = PetController.PET;
		String petsUri = PetsController.PETS;


		// check POST
		MockHttpServletRequestBuilder mockBuilder = MockMvcRequestBuilders.post(petUri)
		      .header(PetController.G_PARAM_PET_NAME, TEST_PET_NAME)
		      .header(PetController.G_PARAM_PET_RASE, TEST_PET_RASE)
//		      .header(PetController.G_PARAM_PET_OWNER, TEST_PET_OWNER)
		      .header(PetController.G_PARAM_PET_COLOR, TEST_PET_COLOR);

		mockBuilder.accept(MediaType.APPLICATION_JSON);

		
		// check mandatory params
		MvcResult result = mvc.perform(mockBuilder).andReturn();
		int status = result.getResponse().getStatus();

		assertEquals("Pet must have owner - owner param is mandatory", status, HttpStatus.BAD_REQUEST.value());
		log.info("status: {}", status);
		
		
		// check successful request
		mockBuilder.header(PetController.G_PARAM_PET_OWNER, TEST_PET_OWNER);
		

		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals("pet POST failed", status, HttpStatus.OK.value());
		log.info("Pet POST status: {}", status);
		
		
		// check GET
		final long TEST_PET_ID  =1l; 
		mockBuilder = MockMvcRequestBuilders.get(petUri +"/" +TEST_PET_ID);
		mockBuilder.accept(MediaType.APPLICATION_JSON);
		
		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals(String.format("Pet id: %d cannot be retrieved", TEST_PET_ID), status, HttpStatus.OK.value());
		log.info("Pet GET status: {}", status);
		log.info(result.getResponse().getContentAsString());

		// check DELETE
		mockBuilder = MockMvcRequestBuilders.delete(petUri +"/" +TEST_PET_ID);
		mockBuilder.accept(MediaType.APPLICATION_JSON);
		
		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals(String.format("Pet id: %d cannot be deleted", TEST_PET_ID), status, HttpStatus.OK.value());
		log.info("Pet DELETE status: {}", status);

		mockBuilder = MockMvcRequestBuilders.get(petUri +"/" +TEST_PET_ID);
		mockBuilder.accept(MediaType.APPLICATION_JSON);
		
		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals(String.format("Pet id: %d should not be present after deletion", TEST_PET_ID), status, HttpStatus.NOT_FOUND.value());
	}
}
