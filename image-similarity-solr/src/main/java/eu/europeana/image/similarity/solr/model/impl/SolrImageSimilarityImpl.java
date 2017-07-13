package eu.europeana.image.similarity.solr.model.impl;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.image.similarity.definitions.model.BaseImageSimilarityImpl;
import eu.europeana.image.similarity.definitions.model.vocabulary.SolrFields;

public class SolrImageSimilarityImpl extends BaseImageSimilarityImpl{

	@Override
	@Field(SolrFields.ID)
	public void setId(String id) {
		super.setId(id);
	}
	
	@Override
	@Field(SolrFields.IMGURL)
	public void setImgurl(String imgurl) {
		super.setImgurl(imgurl);
	}
	
	@Override
	@Field(SolrFields.TITLE)
	public void setTitle(String title) {
		super.setTitle(title);
	}
	
	@Override
	@Field(SolrFields.DISTANCE)
	public void setDistance(double d) {
		super.setDistance(d);
	}
}
