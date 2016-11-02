package it.dontesta.liferay.symposium.jaxrsws.user.service.impl.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import it.dontesta.liferay.symposium.jaxrsws.user.api.CustomUserService;
import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;

@Component(
	immediate = true,
	property = {
			"jaxws.application=true"
	},
	service = CustomUserService.class
)
@WebService
public class CustomUserServiceWSEndPoint implements CustomUserService {

	@Override
	@WebMethod
	public List<User> getUsersByTag(String tagName) 
			throws CustomUserServiceException {
		if (_log.isInfoEnabled()) {
			_log.info("getUsersByTag {"
					+ tagName
					+ "}...");
		}
		return _customUserService.getUsersByTag(tagName);
	}

	@Override
	@WebMethod
	public List<User> getUsersByTag(String[] tagNames) 
			throws CustomUserServiceException {
		if (_log.isInfoEnabled()) {
			_log.info("getUsersByTag {"
					+ tagNames
					+ "}...");
		}
		return _customUserService.getUsersByTag(tagNames);
	}

	@Override
	@WebMethod
	public List<User> getUsersByCategory(String categoryName) 
			throws CustomUserServiceException {
		if (_log.isInfoEnabled()) {
			_log.info("getUsersByCategory {"
					+ categoryName
					+ "}...");
		}
		return _customUserService.getUsersByCategory(categoryName);
	}

	@Reference
	public void setCustomUserService(CustomUserService customUserService) {
		_customUserService = customUserService;
	}

	private CustomUserService _customUserService;

	private static Log _log = LogFactoryUtil.getLog(CustomUserServiceWSEndPoint.class);

}