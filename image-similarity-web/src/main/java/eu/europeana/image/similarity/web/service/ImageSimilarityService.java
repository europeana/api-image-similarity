package eu.europeana.image.similarity.web.service;

public interface ImageSimilarityService {

//	Entity retrieveByUrl(String type, String namespace, String identifier) throws HttpException;

	/**
	 * This method provides suggestions for auto-completion
	 * 
	 * @param text
	 * @param language
	 * @param internalEntityType
	 * @param namespace
	 * @param rows
	 * @return
	 * @throws HttpException
	 * 
	 * e.g. GET /entity/suggest?text=leonard&language=en
	 */
//	List<? extends Concept> suggest(
//	ResultSet<? extends EntityPreview> suggest(
//			String text, String language, EntityTypes[] internalEntityTypes, String scope, String namespace, int rows) throws HttpException;

	
	/**
	 * Performs a lookup for the entity in all 4 datasets:
	 * 
	 *    agents, places, concepts and time spans 
	 * 
	 * using an alternative uri for an entity (lookup will happen within the owl:sameAs properties).
	 * 
	 * @param uri
	 * @return
	 * @throws HttpException
	 */
//	String resolveByUri(String uri) throws HttpException;

}
