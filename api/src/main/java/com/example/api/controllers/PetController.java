package com.example.api.controllers;

import static com.google.common.base.Preconditions.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiInputException;
import com.example.api.model.Pet;
import com.example.api.repositories.PetRepository;
import com.google.common.base.Strings;

@RestController
public class PetController extends AbstractApiController
{
	protected static final String PET = API_PATH + "/pet";

	protected static final String G_PARAM_PET_NAME = "petName";
	protected static final String G_PARAM_PET_RASE = "petRase";
	protected static final String G_PARAM_PET_OWNER = "petOwner";
	protected static final String G_PARAM_PET_COLOR = "petColor";
	protected static final String G_PARAM_PET_SKILL = "petSkill";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PetRepository petRepo;

	@CrossOrigin()
	@RequestMapping(value = PET + "/{petId}",
	      method = RequestMethod.GET,
	      produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pet> get(@PathVariable Long petId, final HttpServletRequest request)
	      throws ApiInputException, ServletException
	{
		log.debug("GET pet id: %d", petId);

		UserController.checkAuthorization(UserController.ROLE_USER, request);

		checkNotNull(petId);

		Pet pet = petRepo.getById(petId);
		if (pet == null)
		{
			throw new ApiInputException(HttpStatus.NOT_FOUND, String.format("pet id: %s not found", petId));
		}

		return ResponseEntity.ok(pet);
	}

	@CrossOrigin()
	@RequestMapping(value = PET,
	      method = RequestMethod.POST,
	      produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pet> create(
	      @RequestParam(name = G_PARAM_PET_NAME, required = false) String petName,
	      @RequestParam(name = G_PARAM_PET_RASE, required = false) String petRase,
	      @RequestParam(name = G_PARAM_PET_OWNER, required = false) String petOwner,
	      @RequestParam(name = G_PARAM_PET_COLOR, required = false) String petColor,
	      @RequestParam(name = G_PARAM_PET_SKILL, required = false) String petSkill,
	      final HttpServletRequest request)
	            throws ApiInputException, ServletException
	{
		log.debug("POST pet name: %s", petName, petRase, petOwner);

		UserController.checkAuthorization(UserController.ROLE_ADMIN, request);

		if (Strings.isNullOrEmpty(petName))
		{
			throw new ApiInputException(HttpStatus.BAD_REQUEST, String.format("%s parameter is mandatory", G_PARAM_PET_NAME));
		}
		if (Strings.isNullOrEmpty(petRase))
		{
			throw new ApiInputException(HttpStatus.BAD_REQUEST, String.format("%s parameter is mandatory", G_PARAM_PET_RASE));
		}
		if (Strings.isNullOrEmpty(petOwner))
		{
			throw new ApiInputException(HttpStatus.BAD_REQUEST, String.format("%s parameter is mandatory", G_PARAM_PET_OWNER));
		}

		// in case the same pet exists, owerwrite
		Pet pet = petRepo.getByNameAndRaseAndOwner(petName, petRase, petOwner);
		if (pet == null)
		{
			pet = new Pet(petName, petRase, petOwner)
			      .setColor(petColor)
			      .setSkill(petSkill);
		}

		pet = petRepo.save(pet);

		return ResponseEntity.ok(pet);
	}

	@CrossOrigin()
	@RequestMapping(value = PET + "/{petId}",
	      method = RequestMethod.DELETE,
	      produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pet> delete(@PathVariable Long petId, final HttpServletRequest request)
	      throws ApiInputException, ServletException
	{
		log.debug("DELETE pet id: %d", petId);

		UserController.checkAuthorization(UserController.ROLE_ADMIN, request);

		checkNotNull(petId);

		Pet pet = petRepo.getById(petId);
		if (pet == null)
		{
			throw new ApiInputException(HttpStatus.NOT_FOUND, String.format("pet id: %s not found", petId));
		}

		petRepo.delete(petId);

		return ResponseEntity.ok(pet);
	}
}
