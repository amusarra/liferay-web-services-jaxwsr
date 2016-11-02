package it.dontesta.liferay.symposium.jaxrsws.user.service.impl.rs;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import it.dontesta.liferay.symposium.jaxrsws.user.api.PersonService;
import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;
import it.dontesta.liferay.symposium.jaxrsws.user.model.PersonList;

@ApplicationPath("/ext.persons")
@Component(
	immediate = true, 
	property = {
			"jaxrs.application=true"
	},
	service = Application.class
)
public class CustomUserServiceRestEndpoint extends Application implements PersonService {

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton((Object)this);
	}

	@GET
	@Path("/list/tag/{tagName}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PersonList getUsersByTag(@PathParam("tagName")String tagName) throws CustomUserServiceException {
		return _personService.getUsersByTag(tagName);
	}

	@GET
	@Path("/list/tags/{tagNames}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PersonList getUsersByTag(@PathParam("tagNames")String[] tagNames) throws CustomUserServiceException {
		return _personService.getUsersByTag(tagNames);
	}

	@GET
	@Path("/list/category/{category}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PersonList getUsersByCategory(@PathParam("category")String category) throws CustomUserServiceException {
		return _personService.getUsersByCategory(category);
	}

	
	@Reference
	public void setPersonService(PersonService personService) {
		_personService = personService;
	}

	private PersonService _personService;
}