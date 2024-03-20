package com.anant.rest.services.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	private UserDaoService service;

	public UserResource(UserDaoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping(path="users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	
	@GetMapping(path="users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		
		User user = service.findUser(id);
		
		if(user==null)
			throw new UserNotFoundException("id:"+id);
		
		EntityModel<User> entityModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@DeleteMapping(path="users/{id}")
	public void deleteById(@PathVariable int id){
		service.deleteById(id);
	}
	
	@PostMapping(path="/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
