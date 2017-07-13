package eu.europeana.image.similarity.web.service.impl;

import javax.annotation.Resource;

import eu.europeana.image.similarity.solr.service.SolrImageSimilarityService;
import eu.europeana.image.similarity.web.service.ImageSimilarityService;

public class ImageSimilarityServiceImpl implements ImageSimilarityService {

	public final String BASE_URL_DATA = "http://data.europeana.eu/";  
	
	@Resource 
	SolrImageSimilarityService solrEntityService;
	
//	@Override
//	public Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException{
//		
//		String entityUri = BASE_URL_DATA + type.toLowerCase() + "/" + namespace + "/" + identifier;
//		Entity result;
//		try {
//			result = solrEntityService.searchByUrl(type, entityUri);
//		} catch (EntityRetrievalException e) {
//			throw new HttpException(e.getMessage(), I18nConstants.SERVER_ERROR_CANT_RETRIEVE_URI, new String[]{entityUri} , HttpStatus.INTERNAL_SERVER_ERROR);
//		} catch (UnsupportedEntityTypeException e) {
//			throw new HttpException(null, I18nConstants.UNSUPPORTED_ENTITY_TYPE, new String[]{type}, HttpStatus.NOT_FOUND, null);
//		}
//		//if not found send appropriate error message
//		if(result == null)
//			throw new HttpException(null, I18nConstants.URI_NOT_FOUND, new String[]{entityUri}, HttpStatus.NOT_FOUND, null);
//		
//		return result;
//	}
	
//	protected Query buildSearchQuery(String queryString, String[] filters, int rows) {
//		
//		Query searchQuery = new QueryImpl();
//		searchQuery.setQuery(queryString);
//		searchQuery.setRows(Math.min(rows, Query.MAX_PAGE_SIZE));	
//		searchQuery.setFilters(filters);
//		
//		return searchQuery;
//	}

	
}
