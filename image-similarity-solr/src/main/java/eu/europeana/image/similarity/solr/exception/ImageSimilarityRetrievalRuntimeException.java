package eu.europeana.image.similarity.solr.exception;


public class ImageSimilarityRetrievalRuntimeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -167560566275881316L;

	public ImageSimilarityRetrievalRuntimeException(String message, Throwable th) {
		super(message, th);
	}

	public ImageSimilarityRetrievalRuntimeException(String message) {
		super(message);
	}
	
	
}
