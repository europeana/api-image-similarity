package eu.europeana.image.similarity.web.controller;

import org.apache.commons.lang3.StringUtils;

import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.image.similarity.config.i18n.I18nConstants;

public abstract class BaseRest{

	public BaseRest() {
		super();
	}

	protected void validateApiKey(String wsKey) throws ApplicationAuthenticationException {
		// throws exception if the wskey is not found
		if (StringUtils.isEmpty(wsKey))
			throw new ApplicationAuthenticationException(null, I18nConstants.EMPTY_APIKEY, null);
		if (!wsKey.equals("apidemo"))
			throw new ApplicationAuthenticationException(null, I18nConstants.INVALID_APIKEY,  new String[]{wsKey});
	}

//	public SimilaritySearchResults<Concept> buildSearchErrorResponse(String apiKey, String action,
//			Throwable th) {
//
//		EntitySearchResults<Concept> response = new EntitySearchResults<Concept>(apiKey,
//				action);
//		response.success = false;
//		response.error = th.getMessage();
//		// response.requestNumber = 0L;
//
//		return response;
//	}

	
}