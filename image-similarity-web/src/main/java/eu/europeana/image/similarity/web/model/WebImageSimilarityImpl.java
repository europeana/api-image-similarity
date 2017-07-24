package eu.europeana.image.similarity.web.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import eu.europeana.image.similarity.solr.model.impl.SolrImageSimilarityImpl;

public class WebImageSimilarityImpl extends SolrImageSimilarityImpl {


	@Override
	@JsonGetter ("media")
	public String getImgurl() {
		return super.getImgurl();
	}
	
	@Override
	@JsonGetter ("record")
	public String getId() {
		return "http://data.europeana.eu/item"  + super.getId();
	}
	
	@Override
	@JsonGetter ("score")
	public double getDistance() {
		return super.getDistance();
	}

}
