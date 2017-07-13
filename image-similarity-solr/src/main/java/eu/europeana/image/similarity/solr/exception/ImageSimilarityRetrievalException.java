package eu.europeana.image.similarity.solr.exception;


public class ImageSimilarityRetrievalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167560566275881316L;

	public ImageSimilarityRetrievalException(String message, Throwable th) {
		super(message, th);
	}

	public ImageSimilarityRetrievalException(String message) {
		super(message);
	}
	
	
}
