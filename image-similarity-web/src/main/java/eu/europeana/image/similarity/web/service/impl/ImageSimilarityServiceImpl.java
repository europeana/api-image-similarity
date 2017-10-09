package eu.europeana.image.similarity.web.service.impl;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;

import eu.europeana.api.commons.config.i18n.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.image.similarity.definitions.model.ImageSimilarity;
import eu.europeana.image.similarity.definitions.model.search.Query;
import eu.europeana.image.similarity.definitions.model.search.result.ResultSet;
import eu.europeana.image.similarity.solr.exception.ImageSimilarityRetrievalException;
import eu.europeana.image.similarity.solr.service.SolrImageSimilarityService;
import eu.europeana.image.similarity.web.service.ImageSimilarityService;

public class ImageSimilarityServiceImpl implements ImageSimilarityService {

//	public final String BASE_URL_DATA = "http://data.europeana.eu/";  
	
	@Resource 
	SolrImageSimilarityService imageSimilaritySolrService;
	
	public ResultSet<? extends ImageSimilarity> searchByRecordId(String recordId, String feature, int page,
			int pageSize) throws ImageSimilarityRetrievalException, HttpException {
		Query searchQuery = imageSimilaritySolrService.buildSearchQuery(recordId, feature, (page - 1) * pageSize, pageSize);
		ResultSet<? extends ImageSimilarity> results;
		
		try{
			results = imageSimilaritySolrService.search(searchQuery);		
		}catch(ImageSimilarityRetrievalException e){
			throw new HttpException("Unknown record id: " + recordId, 
					I18nConstants.RESOURCE_NOT_FOUND, new String[]{"recordId", recordId}, HttpStatus.NOT_FOUND, null);
		}
		
		return results;
	}
	
}
