package eu.europeana;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.image.similarity.definitions.model.ImageSimilarity;
import eu.europeana.image.similarity.definitions.model.search.Query;
import eu.europeana.image.similarity.definitions.model.search.result.ResultSet;
import eu.europeana.image.similarity.solr.exception.ImageSimilarityRetrievalException;
import eu.europeana.image.similarity.solr.service.SolrImageSimilarityService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/image-similarity-solr-context.xml" })
public class SolrImageSimilarityServiceTest {

	@Resource
	SolrImageSimilarityService solrImageSimilarityServices;
	
	private final Logger log = Logger.getLogger(getClass());

	@Test
	public void testSearch() throws ImageSimilarityRetrievalException{
		
		String recordId = "/9200397/BibliographicResource_3000126283891";
		Query searchQuery = solrImageSimilarityServices.buildSearchQuery(recordId, "ph", 0, 5);
		ResultSet<? extends ImageSimilarity> rs = solrImageSimilarityServices.search(searchQuery);
		
		assertNotNull(rs.getResults());
		assertTrue(rs.getResultSize() >= 1 );
		assertEquals(recordId, rs.getResults().get(0).getId());
		log.info("found results:" + rs.getResultSize());
		log.info("retrieved results:" + rs.getResults().size());
		log.info(rs.getResults().get(0));
	}
}
