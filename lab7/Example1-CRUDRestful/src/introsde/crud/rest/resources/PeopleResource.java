package introsde.crud.rest.resources;

import introsde.crud.rest.dao.PersonDao;
import introsde.crud.rest.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


//Will map the resource to the URL /ehealth/v2
@Path("/person")
public class PeopleResource {

	// Allows to insert contextual objects into the class,
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of todos to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Person> getPersonsBrowser() {
		List<Person> todos = new ArrayList<Person>();
		todos.addAll(PersonDao.instance.getModel().values());
		return todos;
	}

	// Return the list of todos for applications
	@GET
	@Produces({ MediaType.APPLICATION_XML})
	public List<Person> getPersonListXML() {
		List<Person> people = new ArrayList<Person>();
		people.addAll(PersonDao.instance.getModel().values());
		return people;
	}
	

	// Return the list of todos for applications
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Person> getPersonListJson() {
		List<Person> people = new ArrayList<Person>();
		people.addAll(PersonDao.instance.getModel().values());
		return people;
	}

	// retuns the number of todos
	// use http://localhost:8080/de.vogella.jersey.todo/rest/todos/count
	// to get the total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		System.out.println("Getting count...");
		int count = PersonDao.instance.getModel().size();
		return String.valueOf(count);
	}

//	// let's create this service for responding a submission form
//	@POST
//	@Produces(MediaType.TEXT_HTML)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public void newPerson(@FormParam("id") String id,
//			@FormParam("firstname") String firstname,
//			@FormParam("lastname") String lastname,
//			@Context HttpServletResponse servletResponse) throws IOException {
//		Person p = new Person(id, firstname, lastname);
//		PersonDao.instance.getModel().put(id, p);
//		servletResponse.sendRedirect("../NewPerson.html");
//	}
	
	
	// let's create this service for responding a submission form
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Person newPerson(Person person) throws IOException {
		System.out.println("Creating new person...");
		int count = PersonDao.instance.getModel().size();
		String newId = count+1+"";
		person.setId(newId);
		PersonDao.instance.getModel().put(newId, person);
		return person;
	}
	

	// Defines that the next path parameter after the base url is
	// treated as a parameter and passed to the PersonResources
	// Allows to type http://localhost:599/base_url/1
	// 1 will be treaded as parameter todo and passed to PersonResource
	@Path("{personId}")
	public PersonResource getPerson(@PathParam("personId") String id) {
		return new PersonResource(uriInfo, request, id);
	}

}
