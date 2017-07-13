package eu.europeana.image.similarity.web.controller.exception;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;

import eu.europeana.api.commons.config.i18n.I18nService;
import eu.europeana.api.commons.web.controller.exception.AbstractExceptionHandlingController;
import eu.europeana.api.commons.web.model.ApiResponse;

/**
 * TODO move to api-common
 * 
 * @author GordeaS
 *
 */
@ControllerAdvice
public class GlobalExceptionHandling extends AbstractExceptionHandlingController {

	Logger logger = Logger.getLogger(getClass());
	
	@Resource
	I18nService i18nService;

	
	protected I18nService getI18nService() {
		return i18nService;
	}

	@Override
	public ApiResponse buildErrorResponse(String errorMessage, String action, String apiKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
