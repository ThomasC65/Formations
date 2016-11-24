package com.sopra;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.User;



@Path( "user" )
public class UserWS {
	@EJB
	UserDao userDao;

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<User> getAllUsers()
	{
		return userDao.findAllUsers();
	}


	@GET
	@Path( "{id}" )
	@Produces( MediaType.APPLICATION_JSON )
	public User getOneUser( @PathParam( "id" ) int idUser )
	{
		return userDao.findUserById( idUser );
	}

	// CREATE

	@PUT
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public User createUser( User user )
	{
		return userDao.createOrUpdateUser( user );
	}

	// UPDATE

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public User updateUser( User user )
	{
		return userDao.createOrUpdateUser( user );
	}

	// DELETE

	@DELETE
	@Path( "{id}" )
	public void deleteUser( @PathParam( "id" ) int idUser )
	{
		userDao.deleteUser( idUser );
	}

}
