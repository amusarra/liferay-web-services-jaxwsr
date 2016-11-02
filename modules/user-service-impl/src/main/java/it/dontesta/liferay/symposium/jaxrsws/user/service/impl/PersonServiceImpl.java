package it.dontesta.liferay.symposium.jaxrsws.user.service.impl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.model.User;

import it.dontesta.liferay.symposium.jaxrsws.user.api.CustomUserService;
import it.dontesta.liferay.symposium.jaxrsws.user.api.PersonService;
import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;
import it.dontesta.liferay.symposium.jaxrsws.user.model.Person;
import it.dontesta.liferay.symposium.jaxrsws.user.model.PersonList;


@Component(
	immediate = true,
	property = {
	},
	service = PersonService.class
)
public class PersonServiceImpl implements PersonService {

	@Override
	public PersonList getUsersByTag(String tagName) throws CustomUserServiceException {

		List<User> userList = _customUserService.getUsersByTag(tagName);
		PersonList personList = new PersonList();
		
		for (User user : userList) {
			Person person = new Person();
			person.setUserId(user.getUserId());
			person.setUserName(user.getScreenName());
			person.setEmail(user.getEmailAddress());
			person.setFirstName(user.getFirstName());
			person.setLastName(user.getLastName());
			
			personList.getUserList().add(person);
		}
		
		return personList;
	}

	@Override
	public PersonList getUsersByTag(String[] tagNames) throws CustomUserServiceException {
		throw new CustomUserServiceException("To be implements");	
	}

	@Override
	public PersonList getUsersByCategory(String categoryName) throws CustomUserServiceException {
		throw new CustomUserServiceException("To be implements");	
	}


	@Reference
	public void setCustomUserService(CustomUserService customUserService) {
		_customUserService = customUserService;
	}

	private CustomUserService _customUserService;
}