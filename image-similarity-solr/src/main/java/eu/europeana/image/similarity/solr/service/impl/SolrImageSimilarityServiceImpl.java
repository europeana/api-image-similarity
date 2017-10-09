package eu.europeana.image.similarity.solr.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

import eu.europeana.image.similarity.definitions.model.ImageSimilarity;
import eu.europeana.image.similarity.definitions.model.search.Query;
import eu.europeana.image.similarity.definitions.model.search.QueryImpl;
import eu.europeana.image.similarity.definitions.model.search.result.ResultSet;
import eu.europeana.image.similarity.definitions.model.vocabulary.SolrConstants;
import eu.europeana.image.similarity.definitions.model.vocabulary.SolrFields;
import eu.europeana.image.similarity.solr.exception.ImageSimilarityRetrievalException;
import eu.europeana.image.similarity.solr.exception.ImageSimilarityRetrievalRuntimeException;
import eu.europeana.image.similarity.solr.service.SolrImageSimilarityService;

@SuppressWarnings("deprecation")
public class SolrImageSimilarityServiceImpl implements SolrImageSimilarityService {

	@Resource
	SolrClient solrClient;

	private final Logger log = Logger.getLogger(getClass());

	public Logger getLogger() {
		return log;
	}

	public void setSolrClient(SolrClient solrClient) {
		this.solrClient = solrClient;
	}

	public ResultSet<? extends ImageSimilarity> search(Query searchQuery)
			throws ImageSimilarityRetrievalException, ImageSimilarityRetrievalRuntimeException {
		getLogger().trace("Search by query:" + searchQuery);

		ResultSet<? extends ImageSimilarity> res = null;

		/**
		 * Construct a SolrQuery
		 */
		SolrQuery query = toSolrQuery(searchQuery);

		/**
		 * Query the server
		 */
		String className = null;
		try {
			QueryResponse rsp = solrClient.query(query);
			//TODO: add to properties
			className = "eu.europeana.image.similarity.web.model.WebImageSimilarityImpl";
			@SuppressWarnings("unchecked")
			Class<? extends ImageSimilarity> similarityBeanClass = (Class<? extends ImageSimilarity>) Class.forName(className);
			
			res = buildResultSet(rsp, similarityBeanClass, searchQuery);
		} catch (SolrServerException e) {
			throw new ImageSimilarityRetrievalException(
					"Unexpected exception occured when retrieving image similarities by query: " + query.toString(),
					e);
		} catch (IOException e) {
			throw new ImageSimilarityRetrievalException(
					"IO exception occured when trying to access the solr server:" + solrClient.toString() , e);
		} catch (ClassNotFoundException e) {
			throw new ImageSimilarityRetrievalRuntimeException("Cannot access image similarity bean class:" + className, e);
		}

		return res;
	}

	protected SolrQuery toSolrQuery(Query searchQuery) {

		SolrQuery solrQuery = new SolrQuery();

		//search by ID
		if(StringUtils.startsWith(searchQuery.getQuery(), "/"))
			solrQuery.setParam(SolrFields.ID, searchQuery.getQuery());
		else
			solrQuery.setQuery(searchQuery.getQuery());
		
		//need to compensate missing start param https://github.com/gsergiu/liresolr/issues/7 
		//solrQuery.setRows(searchQuery.getRows());
		//solrQuery.setStart(searchQuery.getStart());
		//#7 need to retrieve from solrlire items from all previous pages + current one  
		solrQuery.setRows(searchQuery.getStart() + searchQuery.getRows());
		
		if (searchQuery.getFilters() != null)
			solrQuery.addFilterQuery(searchQuery.getFilters());

		if (searchQuery.getFacetFields() != null) {
			solrQuery.setFacet(true);
			solrQuery.addFacetField(searchQuery.getFacetFields());
			solrQuery.setFacetLimit(searchQuery.getFacetLimit());
		}

		if (searchQuery.getSort() != null) {
			solrQuery.setSort(searchQuery.getSort(), SolrQuery.ORDER.valueOf(searchQuery.getSortOrder()));
		}

		solrQuery.setFields(searchQuery.getViewFields());
		if(searchQuery.getQueryField() != null)
			solrQuery.setParam(SolrConstants.PARAM_FIELD, searchQuery.getQueryField());
		solrQuery.setRequestHandler(searchQuery.getReqHandler());
		
		//TODO: move to properties
		solrQuery.setParam("ms", false);
		solrQuery.setParam("fl", "*");
		
		return solrQuery;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends ImageSimilarity> ResultSet<T> buildResultSet(QueryResponse rsp, Class<T> concreteClass, Query searchQuery) throws ImageSimilarityRetrievalException {

		ResultSet<T> resultSet = new ResultSet<>();

		//quickfix for https://github.com/gsergiu/liresolr/issues/4
		NamedList<Object> responseMap = rsp.getResponse();

		Object error = responseMap.get("Error");
		if(error != null)
			throw new ImageSimilarityRetrievalException((String)error);
		
		SolrDocumentList docList = new SolrDocumentList();
	    List<Object> docs = (List<Object>) responseMap.get("docs");
	    SolrDocument solrDoc;
	    int counter = 0;
	    for (Object doc : docs) {
	    	//need to compensate missing start param https://github.com/gsergiu/liresolr/issues/7 
			if(counter++ < searchQuery.getStart())
				continue;//skip items that do not belong in the current page 
			
	    	solrDoc = new SolrDocument((Map<String, Object>) doc);
	    	docList.add(solrDoc);
		}
	    
	    String totalResults = (String) responseMap.get("RawDocsCount");
	    List<T> beans = solrClient.getBinder().getBeans(concreteClass,docList);
	    //end quickfix
	    
		resultSet.setResults(beans);
		resultSet.setResultSize(Integer.valueOf(totalResults));

		return resultSet;
	}

	@Override
	public Query buildSearchQuery(String recordId, String feature, int start, int rows){
		Query query = new QueryImpl(recordId, rows);
		query.setRows(rows);
		query.setStart(start);
		query.setReqHandler("/lireq");
		query.setQueryField(feature);
		return query;
	}
}
