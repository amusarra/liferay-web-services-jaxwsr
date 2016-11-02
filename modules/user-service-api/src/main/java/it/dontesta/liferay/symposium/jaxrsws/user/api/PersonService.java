/**
 * 
 */
package it.dontesta.liferay.symposium.jaxrsws.user.api;

import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;
import it.dontesta.liferay.symposium.jaxrsws.user.model.PersonList;

/**
 * @author amusarra
 *
 */
public interface PersonService {

	PersonList getUsersByTag(String tagName) throws CustomUserServiceException;
	
	PersonList getUsersByTag(String[] tagNames) throws CustomUserServiceException;
	
	PersonList getUsersByCategory(String categoryName) throws CustomUserServiceException;

}
