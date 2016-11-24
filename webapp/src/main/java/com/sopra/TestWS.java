package com.sopra;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class TestWS {
	@EJB
	TestDao testDao;

	@GET
	public String test() {
		return "Hello " + testDao.test();
	}
}
