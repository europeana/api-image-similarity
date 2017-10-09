package eu.europeana.image.similarity.web.service;

import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.image.similarity.definitions.model.ImageSimilarity;
import eu.europeana.image.similarity.definitions.model.search.result.ResultSet;
import eu.europeana.image.similarity.solr.exception.ImageSimilarityRetrievalException;

public interface ImageSimilarityService {


	/**
	 * This method retrieves a set of images similar to the one linked to the given recordId 
	 * @param recordId - the europeana recordId
	 * @param feature - one of the supported search features (see solr schema)
	 * @param page - the page number (starting with 1 fo the first page)
	 * @param pageSize - the number of results per page
	 * @return
	 * @throws ImageSimilarityRetrievalException
	 * @throws HttpException
	 */
	public ResultSet<? extends ImageSimilarity> searchByRecordId(String recordId, String feature, int page,
			int pageSize) throws ImageSimilarityRetrievalException, HttpException;
	

}
