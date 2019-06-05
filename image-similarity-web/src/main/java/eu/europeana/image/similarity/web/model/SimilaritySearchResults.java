package eu.europeana.image.similarity.web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.europeana.image.similarity.definitions.model.ImageSimilarity;


@JsonInclude(Include.NON_NULL)
public class SimilaritySearchResults<T extends ImageSimilarity>{

	private Long size;

	private Long total;

	private List<T> items;

	//TODO: enamble when specified
//	public SimilaritySearchResults(String apikey, String action) {
//		super(apikey, action);
//	}

	public SimilaritySearchResults() {
		// used by Jackson
		super();
	}
	
//	@JsonGetter
	@JsonIgnore
	public Long getSize() {
		return size;
	}

	@JsonGetter
	public Long getTotal() {
		return total;
	}

	@JsonGetter
	public List<T> getItems() {
		return items;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setTotal(long total) {
		this.total = total;
	}

//	public void setItems(List<T> list) {
//		this.items = list;
//	}

	@SuppressWarnings("unchecked")
	public void setItems(List<? extends ImageSimilarity> list) {
		this.items = (List<T>) list;
	}

	
}
