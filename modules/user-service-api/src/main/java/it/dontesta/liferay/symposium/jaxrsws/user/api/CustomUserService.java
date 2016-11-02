package it.dontesta.liferay.symposium.jaxrsws.user.api;

import java.util.List;

import com.liferay.portal.kernel.model.User;

import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;

/**
 * 
 * @author amusarra
 *
 */
public interface CustomUserService {
	
	List<User> getUsersByTag(String tagName) throws CustomUserServiceException;
	
	List<User> getUsersByTag(String[] tagNames) throws CustomUserServiceException;
	
	List<User> getUsersByCategory(String categoryName) throws CustomUserServiceException;
}