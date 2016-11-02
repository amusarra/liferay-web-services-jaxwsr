package it.dontesta.liferay.symposium.jaxrsws.user.service.impl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import it.dontesta.liferay.symposium.jaxrsws.user.api.CustomUserService;
import it.dontesta.liferay.symposium.jaxrsws.user.exception.CustomUserServiceException;


@Component(
	immediate = true,
	property = {
	},
	service = CustomUserService.class
)
public class CustomUserServiceImpl implements CustomUserService {

	@Override
	public List<User> getUsersByTag(String tagName) throws CustomUserServiceException {
		
		List<User> userList = searchUsersByTagName(tagName);
		if (Validator.isNotNull(userList) && !userList.isEmpty()) {
			return userList;
		} else {
			throw new CustomUserServiceException("Users not found by"
					+ " tag "
					+ tagName);
		}
	}

	@Override
	public List<User> getUsersByTag(String[] tagNames) throws CustomUserServiceException {
		throw new CustomUserServiceException("To be implements");	
	}

	@Override
	public List<User> getUsersByCategory(String categoryName) throws CustomUserServiceException {
		throw new CustomUserServiceException("To be implements");	
	}

	/**
	 * Search users by tag name
	 * 
	 * @param tagName
	 */
	private List<User> searchUsersByTagName(String tagName) {
		
		BooleanQueryImpl usersQuery = new BooleanQueryImpl();
		List<User> result = null;
		
		SearchEngine searchEngine = SearchEngineHelperUtil.getSearchEngine(
				SearchEngineHelperUtil.getDefaultSearchEngineId());
		
		SearchContext searchContext = new SearchContext();
		searchContext.setCompanyId(PortalUtil.getDefaultCompanyId());
		searchContext.setEntryClassNames(new String[] { User.class.getName() });
		searchContext.setAndSearch(true);
		
		usersQuery.addRequiredTerm(Field.COMPANY_ID, PortalUtil.getDefaultCompanyId());
		usersQuery.addRequiredTerm(Field.ASSET_TAG_NAMES, tagName);
		usersQuery.addRequiredTerm(Field.STATUS, 0);
		
		Hits hitsUser = null;
		try {
			hitsUser = searchEngine.getIndexSearcher().search(
					searchContext, usersQuery);
			
			result = UsersAdminUtil.getUsers(hitsUser);
		} catch (SearchException e) {
			if (_log.isErrorEnabled()) {
				_log.error(e);
			}
		} catch (PortalException e) {
			if (_log.isErrorEnabled()) {
				_log.error(e);
			}
		}
		
		return result;
	}
	
	private static Log _log = LogFactoryUtil.getLog(CustomUserServiceImpl.class);
}