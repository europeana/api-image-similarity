package eu.europeana.image.similarity.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.europeana.api.common.config.swagger.SwaggerSelect;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.api.commons.web.exception.InternalServerException;
import eu.europeana.api.commons.web.http.HttpHeaders;
import eu.europeana.image.similarity.definitions.model.ImageSimilarity;
import eu.europeana.image.similarity.definitions.model.search.Query;
import eu.europeana.image.similarity.definitions.model.search.result.ResultSet;
import eu.europeana.image.similarity.solr.service.SolrImageSimilarityService;
import eu.europeana.image.similarity.web.vocabulary.ImageSimilarityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "Image Search API")
@SwaggerSelect
public class SearchController extends BaseRest {

	@Resource
	SolrImageSimilarityService similarityService;

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Search images by europeana record id", nickname = "searchByRecordId", response = java.lang.Void.class)
	@RequestMapping(value = { "/similarity/{datasetId}/{localId}", "/similarity/{datasetId}/{localId}.json" }, method = RequestMethod.GET, produces = {
			HttpHeaders.CONTENT_TYPE_JSON_UTF8, HttpHeaders.CONTENT_TYPE_JSONLD_UTF8 })
	public ResponseEntity<String> searchById(
			@PathParam(value = ImageSimilarityConstants.PARAM_DATASET_ID) String datasetId,
			@PathParam(value = ImageSimilarityConstants.PARAM_LOCAL_ID) String localId,
			@RequestParam(value = ImageSimilarityConstants.PARAM_WSKEY) String wskey,
			@RequestParam(value = ImageSimilarityConstants.PARAM_FEATURE) String feature,
//			@RequestParam(value = ImageSimilarityConstants.PARAM_ALG) String algorithm,
			@RequestParam(value = ImageSimilarityConstants.PARAM_PAGE, defaultValue = "" + Query.DEFAULT_START) int page,
			@RequestParam(value = ImageSimilarityConstants.PARAM_PAGE_SIZE, defaultValue = ""
					+ Query.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = ImageSimilarityConstants.PARAM_PROFILE, required = false) String profile,
			HttpServletRequest request)
			throws HttpException {

//		/String action = "get:/similarity/searchByRecordId";
		
		try {
			// Check client access (a valid “wskey” must be provided)
			validateApiKey(wskey);

			// perform search
			Query searchQuery = similarityService.buildSearchQuery("/"+datasetId+"/"+localId, "ph", (page - 1) * pageSize, pageSize);
			ResultSet<? extends ImageSimilarity> results = similarityService.search(searchQuery);
			
			// serialize results
			String json = new ObjectMapper().writeValueAsString(results);

			// build response
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>(5);
			headers.add(HttpHeaders.VARY, HttpHeaders.ACCEPT);
//			headers.add(HttpHeaders.LINK, HttpHeaders.VALUE_LDP_CONTAINER);
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
