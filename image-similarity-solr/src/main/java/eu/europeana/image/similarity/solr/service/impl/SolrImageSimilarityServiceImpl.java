package eu.europeana.image.similarity.solr.service.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
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
import eu.europeana.image.similarity.solr.model.impl.SolrImageSimilarityImpl;
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
			throws ImageSimilarityRetrievalException {
		getLogger().trace("Search by query:" + searchQuery);

		ResultSet<? extends ImageSimilarity> res = null;

		/**
		 * Construct a SolrQuery
		 */
		SolrQuery query = toSolrQuery(searchQuery);

		/**
		 * Query the server
		 */
		try {
			QueryResponse rsp = solrClient.query(query);
			res = buildResultSet(rsp, SolrImageSimilarityImpl.class);
		} catch (SolrServerException e) {
			throw new ImageSimilarityRetrievalException(
					"Unexpected exception occured when retrieving image similarities by query: " + query.toString(),
					e);
		} catch (IOException e) {
			throw new ImageSimilarityRetrievalException(
					"IO exception occured when trying to access the solr server:" + solrClient.toString() , e);
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

		solrQuery.setRows(searchQuery.getRows());
		solrQuery.setStart(searchQuery.getStart());

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
	
	protected <T extends ImageSimilarity> ResultSet<T> buildResultSet(QueryResponse rsp, Class<T> concreteClass) {

		ResultSet<T> resultSet = new ResultSet<>();

		//quickfix for https://github.com/gsergiu/liresolr/issues/4
		NamedList<Object> responseMap = rsp.getResponse();
		SolrDocumentList docList = new SolrDocumentList();
	    List<Object> docs = (List<Object>) responseMap.get("docs");
	    SolrDocument solrDoc;
	    for (Object doc : docs) {
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