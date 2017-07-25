package eu.europeana.image.similarity.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.config.i18n.I18nConstants;
import eu.europeana.api.commons.web.exception.ApplicationAuthenticationException;
import eu.europeana.api.commons.web.exception.ParamValidationException;
import eu.europeana.image.similarity.config.i18n.I18nConstantsSimilarity;
import eu.europeana.image.similarity.definitions.vocabulary.Features;
import eu.europeana.image.similarity.web.vocabulary.ImageSimilarityConstants;

public abstract class BaseRest {

	public BaseRest() {
		super();
	}

	protected void validateApiKey(String wsKey) throws ApplicationAuthenticationException {
		// throws exception if the wskey is not found
		if (StringUtils.isEmpty(wsKey))
			throw new ApplicationAuthenticationException(I18nConstants.EMPTY_APIKEY, I18nConstants.EMPTY_APIKEY, null);
		if (!wsKey.equals("apidemo"))
			throw new ApplicationAuthenticationException(I18nConstants.INVALID_APIKEY, I18nConstants.INVALID_APIKEY, new String[] { wsKey },
					HttpStatus.FORBIDDEN, null);
	}

	protected void validateFeature(String feature) throws ParamValidationException {
		if (StringUtils.isEmpty(feature))
			throw new ParamValidationException(I18nConstants.EMPTY_PARAM_MANDATORY, I18nConstants.EMPTY_PARAM_MANDATORY,
					new String[] { ImageSimilarityConstants.PARAM_FEATURE });
		try {
			Features.valueOf(feature);
		} catch (IllegalArgumentException e) {
			throw new ParamValidationException("Unknown feature: " + feature, I18nConstants.INVALID_PARAM_VALUE,
					new String[] { ImageSimilarityConstants.PARAM_FEATURE, feature }, e);
		}
	}

	// public SimilaritySearchResults<Concept> buildSearchErrorResponse(String
	// apiKey, String action,
	// Throwable th) {
	//
	// EntitySearchResults<Concept> response = new
	// EntitySearchResults<Concept>(apiKey,
	// action);
	// response.success = false;
	// response.error = th.getMessage();
	// // response.requestNumber = 0L;
	//
	// return response;
	// }

}