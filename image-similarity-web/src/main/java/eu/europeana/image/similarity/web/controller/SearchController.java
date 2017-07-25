package eu.europeana.image.similarity.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.config.i18n.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.exception.InternalServerException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.image.similarity.definitions.model.ImageSimilarity;
import eu.europeana.image.similarity.definitions.model.search.Query;
import eu.europeana.image.similarity.definitions.model.search.result.ResultSet;
import eu.europeana.image.similarity.solr.service.SolrImageSimilarityService;
import eu.europeana.image.similarity.web.model.SimilaritySearchResults;
import eu.europeana.image.similarity.web.vocabulary.ImageSimilarityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Image Search API")
@SwaggerSelect
public class SearchController extends BaseRest {

	@Resource
	SolrImageSimilarityService similarityService;

	@SuppressWarnings("deprecation")
	@ApiOperation(value = "Search images by europeana record id. Feature must be one of: ph (PHOG), ce (CEDD), cl (ColorLayout), sc (ScalableColor), jc (JCD), oh (OpponentHistogram), eh (EdgeHistogram)"
	, nickname = "searchByRecordId", response = java.lang.Void.class)
	@RequestMapping(value = { "/similarity/{datasetId}/{localId}", "/similarity/{datasetId}/{localId}.json" }, method = RequestMethod.GET, produces = {
			HttpHeaders.CONTENT_TYPE_JSON_UTF8, HttpHeaders.CONTENT_TYPE_JSONLD_UTF8 })
	public ResponseEntity<String> searchById(
			@PathVariable(value = ImageSimilarityConstants.PARAM_DATASET_ID) String datasetId,
			@PathVariable(value = ImageSimilarityConstants.PARAM_LOCAL_ID) String localId,
			@RequestParam(value = ImageSimilarityConstants.PARAM_WSKEY, required=false) String wskey,
			@RequestParam(value = ImageSimilarityConstants.PARAM_FEATURE) String feature,
//			@RequestParam(value = ImageSimilarityConstants.PARAM_ALG) String algorithm,
			@RequestParam(value = ImageSimilarityConstants.PARAM_PAGE, defaultValue = "" + Query.DEFAULT_START) int page,
			@RequestParam(value = ImageSimilarityConstants.PARAM_PAGE_SIZE, defaultValue = ""
					+ Query.DEFAULT_PAGE_SIZE) int pageSize,
//			@RequestParam(value = ImageSimilarityConstants.PARAM_PROFILE, required = false) String profile,
			HttpServletRequest request)
			throws HttpException {

//		/String action = "get:/similarity/searchByRecordId";
		
		try {
			// Check client access (a valid “wskey” must be provided)
			validateApiKey(wskey);
			validateFeature(feature);
			
			// perform search
			String recordId = "/"+datasetId+"/"+localId;
			
			
			
			Query searchQuery = similarityService.buildSearchQuery(recordId, feature, (page - 1) * pageSize, pageSize);
			ResultSet<? extends ImageSimilarity> results = similarityService.search(searchQuery);
			
			if(results.isEmpty())
				throw new HttpException("no results found when searching for: " + recordId, 
						I18nConstants.RESOURCE_NOT_FOUND, new String[]{"recordId: " +recordId }, HttpStatus.NOT_FOUND, null);
			
			SimilaritySearchResults<? extends ImageSimilarity> apiResponse = new SimilaritySearchResults<>();
			apiResponse.setItems(results.getResults());
			//TODO: set value when specifications are ready
			//apiResponse.setSize(results.getResultSize());
			//TODO: check this when specifications will be updated
			apiResponse.setTotal(results.getResults().size());
			
			// serialize results
			String json = new ObjectMapper().writeValueAsString(apiResponse);

			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
			headers.add(HttpHeaders.ALLOW, HttpHeaders.ALLOW_GET);

			ResponseEntity<String> response = new ResponseEntity<String>(json, headers, HttpStatus.OK);

			return response;

		} catch (RuntimeException e) {
			// not found ..
			//System.out.println(e);
			throw new InternalServerException(e);
		} catch (HttpException e) {
			// avoid wrapping http exception
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e);
		}

	}

}
