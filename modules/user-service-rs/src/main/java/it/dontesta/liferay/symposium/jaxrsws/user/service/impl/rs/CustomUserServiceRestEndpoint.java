package it.dontesta.liferay.symposium.jaxrsws.user.service.impl.rs;

import java.util.Collections;
import java.util.List;
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import it.dontesta.liferay.symposium.jaxrsws.user.api.CustomUserService;
import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;

@ApplicationPath("/ext.users")
@Component(
	immediate = true, 
	property = {
			"jaxrs.application=true"
	},
	service = Application.class
)
public class CustomUserServiceRestEndpoint extends Application {

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton((Object)this);
	}

	@GET
	@Path("/list/{tagName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersByTag(@PathParam("tagName")String tagName) throws CustomUserServiceException {
		if (_log.isInfoEnabled()) {
			_log.info("getUsersByTag {"
					+ tagName
					+ "}...");
		}
		return _customUserService.getUsersByTag(tagName);
	}

	@GET
	@Path("/list/{tagNames}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersByTag(@PathParam("tagNames")String[] tagNames) throws CustomUserServiceException {
		if (_log.isInfoEnabled()) {
			_log.info("getUsersByTag {"
					+ tagNames
					+ "}...");
		}
		return _customUserService.getUsersByTag(tagNames);
	}

	@GET
	@Path("/list/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsersByCategory(@PathParam("category")String category) throws CustomUserServiceException {
		if (_log.isInfoEnabled()) {
			_log.info("getUsersByCategory {"
					+ category
					+ "}...");
		}
		return _customUserService.getUsersByCategory(category);
	}

	
	@Reference
	public void setCustomUserService(CustomUserService customUserService) {
		_customUserService = customUserService;
	}

	private CustomUserService _customUserService;

	private static Log _log = LogFactoryUtil.getLog(CustomUserServiceRestEndpoint.class);

}