package it.dontesta.liferay.symposium.jaxrsws.user.service.impl.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import it.dontesta.liferay.symposium.jaxrsws.user.api.PersonService;
import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;
import it.dontesta.liferay.symposium.jaxrsws.user.model.PersonList;

@Component(
	immediate = true,
	property = {
			"jaxws.application=true"
	},
	service = CustomUserServiceWSEndPoint.class
)
@WebService
public class CustomUserServiceWSEndPoint implements PersonService {

	@Override
	@WebMethod
	public PersonList getUsersByTag(String tagName) 
			throws CustomUserServiceException {
		return _personService.getUsersByTag(tagName);
	}

	@Override
	@WebMethod(exclude=true)
	public PersonList getUsersByTag(String[] tagNames) 
			throws CustomUserServiceException {
		return _personService.getUsersByTag(tagNames);
	}

	@WebMethod
	public PersonList getUsersByTags(String[] tagNames) 
			throws CustomUserServiceException {
		return _personService.getUsersByTag(tagNames);
	}

	@Override
	@WebMethod
	public PersonList getUsersByCategory(String categoryName) 
			throws CustomUserServiceException {
		return _personService.getUsersByCategory(categoryName);
	}

	@Reference
	@WebMethod(exclude=true)
	public void setPersonService(PersonService personService) {
		_personService = personService;
	}

	private PersonService _personService;
}
