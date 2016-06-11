package com.example.api.controllers;

import static org.junit.Assert.*;

import java.util.List;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.PetstoreApplication;
import com.example.api.repositories.PetRepository;
import com.google.common.collect.Lists;

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
	
	@Autowired
	private WebApplicationContext wac;
	

	@Bean
	public Filter jwtFilter()
	{
		return new JwtFilter();
	}

	@Bean
	public Filter corsFilter()
	{
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Before
	public void setup()
	{
		List<Filter> filters = Lists.newArrayList(corsFilter(), jwtFilter());
		mvc = MockMvcBuilders.webAppContextSetup(webConfig).addFilters(filters.toArray(new Filter[filters.size()])).build();
//		TestUtils.cleanDb(daRepo);
//		prepareTestSesson();
	}

	private static String TEST_PET_NAME = "Miuwah";
	private static String TEST_PET_RASE = "rabbit";
	private static String TEST_PET_OWNER = "Alice";
	private static String TEST_PET_COLOR = "white/gray/beige";

	private static MockHttpServletRequestBuilder signRequest(MockHttpServletRequestBuilder mockBuilder){
		return mockBuilder.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNDY1NjY4MjE2fQ.YF4Vs7ieiXJceVPpWqB6vWtlu0_bYF2k7uX5DVDEUXI");	
	}
	@Test
	public void testIntegration() throws Exception
	{
		String petUri = PetController.PET;
		String petsUri = PetsController.PETS;


		// check POST
		MockHttpServletRequestBuilder mockBuilder = MockMvcRequestBuilders.post(petUri)
		      .param(PetController.G_PARAM_PET_NAME, TEST_PET_NAME)
		      .param(PetController.G_PARAM_PET_RASE, TEST_PET_RASE)
//		      .param(PetController.G_PARAM_PET_OWNER, TEST_PET_OWNER)
		      .param(PetController.G_PARAM_PET_COLOR, TEST_PET_COLOR);

		mockBuilder
			.accept(MediaType.APPLICATION_JSON);

		// check mandatory params
		signRequest(mockBuilder);
		MvcResult result = mvc.perform(mockBuilder).andReturn();
		int status = result.getResponse().getStatus();

		assertEquals("Pet must have owner - owner param is mandatory", HttpStatus.BAD_REQUEST.value(), status);
		log.info("status: {}", status);
		
		
		// check successful request
		mockBuilder.param(PetController.G_PARAM_PET_OWNER, TEST_PET_OWNER);
		
		signRequest(mockBuilder);
		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals("pet POST failed", HttpStatus.OK.value(), status);
		log.info("Pet POST status: {}", status);
		
		
		// check GET
		final long TEST_PET_ID  =1l; 
		mockBuilder = MockMvcRequestBuilders.get(petUri +"/" +TEST_PET_ID);
		mockBuilder.accept(MediaType.APPLICATION_JSON);
		
		signRequest(mockBuilder);
		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals(String.format("Pet id: %d cannot be retrieved", TEST_PET_ID), HttpStatus.OK.value(), status);
		log.info("Pet GET status: {}", status);
		log.info(result.getResponse().getContentAsString());

		// check DELETE
		mockBuilder = MockMvcRequestBuilders.delete(petUri +"/" +TEST_PET_ID);
		mockBuilder.accept(MediaType.APPLICATION_JSON);
		
		signRequest(mockBuilder);
		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals(String.format("Pet id: %d cannot be deleted", TEST_PET_ID), HttpStatus.OK.value(), status);
		log.info("Pet DELETE status: {}", status);

		mockBuilder = MockMvcRequestBuilders.get(petUri +"/" +TEST_PET_ID);
		mockBuilder.accept(MediaType.APPLICATION_JSON);
		
		signRequest(mockBuilder);
		result = mvc.perform(mockBuilder).andReturn();
		status = result.getResponse().getStatus();

		assertEquals(String.format("Pet id: %d should not be present after deletion", TEST_PET_ID), HttpStatus.NOT_FOUND.value(), status);
	}
}
