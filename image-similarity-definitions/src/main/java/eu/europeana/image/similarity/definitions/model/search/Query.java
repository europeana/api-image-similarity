package eu.europeana.image.similarity.definitions.model.search;

/**
 * TODO: implement a common query definition and implementation in api-commons-search
 * @author GordeaS
 * @deprecated
 */
public interface Query {

	void setFacetFields(String[] facetFields);

	String[] getFacetFields();

	void setFilters(String[] filters);

	String[] getFilters();

	void setQuery(String query);

	String getQuery();

	void setRows(int rows);

	int getRows();

	void setStart(int start);

	int getStart();

	void setViewFields(String[] viewFields);

	String[] getViewFields();

	public String getSort();

	public void setSort(String sort);
	
	public String getSortOrder();

	public void setSortOrder(String sortOrder);

	void setReqHandler(String reqHandler);

	String getReqHandler();

	void setFacetLimit(int facetLimit);

	int getFacetLimit();

	void setQueryField(String queryField);

	String getQueryField();

	/**
	 * Default start parameter for Solr
	 */
	public static final String DEFAULT_START = "1";
	/**
	 * Default number of items in the SERP
	 */
	public static final String DEFAULT_PAGE_SIZE = "10";
	
	public static final Integer MAX_PAGE_SIZE = 100;
	
	/**
	 * Use these instead of the ones provided in the apache Solr package
	 * in order to avoid introducing a dependency to that package in all modules
	 * they're public because they are read from SearchServiceImpl
	 */
	public static final Integer ORDER_DESC = 0;
	public static final Integer ORDER_ASC = 1;

}
