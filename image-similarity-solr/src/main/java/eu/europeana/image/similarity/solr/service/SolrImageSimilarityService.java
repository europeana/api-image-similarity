package eu.europeana.image.similarity.solr.service;

import eu.europeana.image.similarity.definitions.model.ImageSimilarity;
import eu.europeana.image.similarity.definitions.model.search.Query;
import eu.europeana.image.similarity.definitions.model.search.result.ResultSet;
import eu.europeana.image.similarity.solr.exception.ImageSimilarityRetrievalException;

@SuppressWarnings("deprecation")
public interface SolrImageSimilarityService {

	/**
	 * This method retrieves available Entities by searching the given id.
	 * 
	 * @param id The SOLR entity_id
	 * @return the set of ImageSimilarites that match the given search criteria
	 * @throws EntityRetrievalException 
	 */
	public ResultSet<? extends ImageSimilarity> search(Query searchQuery) throws ImageSimilarityRetrievalException;

	public Query buildSearchQuery(String recordId, String feature, int start, int rows);

}
