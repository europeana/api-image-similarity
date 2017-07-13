package eu.europeana.image.similarity.definitions.model;

public class BaseImageSimilarityImpl implements ImageSimilarity {

	private String id;
	private String title;
	private String imgurl;
	private double distance;
	
	@Override
	public double getDistance() {
		return distance;
	}

	@Override
	public void setDistance(double d) {
		this.distance = d;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String getTitle() {
		return title;
	}
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String getImgurl() {
		return imgurl;
	}
	@Override
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Override
	public String toString() {
		return  "\n id: "+ getId() + 
				"\n imgurl: " + getImgurl() +
				"\n title: " + getTitle() +
				"\n distance: " + getDistance() ;
	}
	
}
