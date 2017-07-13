package eu.europeana.image.similarity.definitions.model.search.result;

import java.util.Map;

public interface FacetFieldView {

	public String getName();

	public Map<String, Long> getValueCountMap();
	
}
