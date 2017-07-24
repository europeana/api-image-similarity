package eu.europeana.image.similarity.definitions.vocabulary;

public enum Features {

	ph ("PHOG"), ce ("CEDD"), cl ("ColorLayout"), sc ("ScalableColor"), jc ("JCD"), oh ("OpponentHistogram"), eh ("EdgeHistogram");  
	
	private String fullName;
	
	Features(String fullName){
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}
}
