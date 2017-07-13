package eu.europeana.image.similarity.web.controller;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.image.similarity.solr.service.SolrImageSimilarityService;
import eu.europeana.image.similarity.web.vocabulary.ImageSimilarityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Discovery API")
@SwaggerSelect
public class SearchController extends BaseRest {

	@Resource
	SolrImageSimilarityService similarityService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Suggest entitties for the given text query. Suported values for type: Agent, Place, Concept, Timespan, All. Supported values for scope: europeana", nickname = "getSuggestion", response = java.lang.Void.class)
	@RequestMapping(value = { "/entity/suggest", "/entity/suggest.jsonld" }, method = RequestMethod.GET, produces = {
			HttpHeaders.CONTENT_TYPE_JSON_UTF8, HttpHeaders.CONTENT_TYPE_JSONLD_UTF8 })
	public ResponseEntity<String> searchById(@RequestParam(value = ImageSimilarityConstants.PARAM_WSKEY) String wskey,
			@RequestParam(value = ImageSimilarityConstants.QUERY_PARAM_ROWS, defaultValue = ImageSimilarityConstants.PARAM_DEFAULT_ROWS) int rows)
			throws HttpException {

		String action = "get:/similarity/searchById";
		return null;
//		try {
//			// Check client access (a valid “wskey” must be provided)
//			validateApiKey(wskey);
//
//			// validate and convert type
//			EntityTypes[] entityTypes = getEntityTypesFromString(type);
//
//			// validate scope parameter
//			if (StringUtils.isNotBlank(scope) && !WebEntityConstants.PARAM_EUROPEANA.equalsIgnoreCase(scope))
//				throw new ParamValidationException("Invalid request parameter value! ",
//						WebEntityConstants.QUERY_PARAM_SCOPE, scope);
//
//			// perform search
//			ResultSet<? extends EntityPreview> results = entityService.suggest(text, language, entityTypes, scope, null,
//					rows);
//			
//			// serialize results
//			SuggestionSetSerializer serializer = new SuggestionSetSerializer(results);
//			String jsonLd = serializer.serialize();
//
//			// build response
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
//			headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
//			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
//			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);
//
//			ResponseEntity<String> response = new ResponseEntity<String>(jsonLd, headers, HttpStatus.OK);
//
//			return response;
//
//		} catch (RuntimeException e) {
//			// not found ..
//			System.out.println(e);
//			throw new InternalServerException(e);
//		} catch (HttpException e) {
//			// avoid wrapping http exception
//			throw e;
//		} catch (Exception e) {
//			throw new InternalServerException(e);
//		}

	}
	

}
