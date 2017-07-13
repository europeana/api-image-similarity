package eu.europeana.image.similarity.definitions.model;

public interface ImageSimilarity {

	public void setImgurl(String imgurl);

	public String getImgurl();

	public void setTitle(String title);

	public String getTitle();

	public void setId(String id);

	public String getId();

	public void setDistance(double d);

	public double getDistance();

}
