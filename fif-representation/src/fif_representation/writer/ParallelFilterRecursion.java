package fif_representation.writer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractList;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.vocabulary.RDF;

import fif_core.Filter;
import fif_core.ParallelFilter;
import fif_core.interfaces.Aggregator;
import fif_representation.factory.WriterRecursion;
import fif_representation.utility.Utility;
import fif_representation.vocabulary.AGGREGATOR;
import fif_representation.vocabulary.FILTER;
import fif_representation.vocabulary.OWA_AGGREGATOR;
import fif_representation.vocabulary.PARALLEL_FILTER;

/**
 * This class implements the WriterRecursion Interface and provides an
 * implementation of a recursive decomposition of a Parallel Filter.
 * 
 * @author Eugenio Liso
 * @version 1.0
 * @see WriterRecursion
 *
 */
public class ParallelFilterRecursion implements WriterRecursion {

    @Override
    public Resource decomposeFilter(Model model, Filter f)
	    throws URISyntaxException {
	Resource filterResource = null;

	// ottengo l'uri della risorsa in questione
	URI resourceUri = Utility.initializeUri(Utility.createResourceName(f));

	filterResource = Utility.initializeResource(model, resourceUri);

	// aggiungo una proprieta
	filterResource.addProperty(RDF.type, FILTER.filter);

	// creazione filtro parallelo e creazione risorsa anonima
	ParallelFilter parafil = (ParallelFilter) f;
	Aggregator agg = parafil.getAggregator();

	// creaione Uri e risorsa
	URI aggregatorUri = Utility.initializeUri(Utility
		.createResourceName(agg));
	Resource aggregatorResource = Utility.initializeResource(model,
		aggregatorUri);

	// aggiunta proprieta'
	filterResource.addProperty(PARALLEL_FILTER.hasAggregator,
		AGGREGATOR.aggregator);
	if (!model.containsResource(aggregatorResource)) {
	    double[] weights = agg.getWeights();

	    // creazione sequenza dei pesi
	    Seq weightSequence = model.createSeq();

	    for (double weight : weights) {
		weightSequence.add(model.createTypedLiteral(weight,
			XSDDatatype.XSDdouble));
	    }

	    // aggiunta proprieta'

	    aggregatorResource.addProperty(OWA_AGGREGATOR.hasValues,
		    weightSequence);

	    aggregatorResource.addProperty(RDF.type, AGGREGATOR.aggregator);
	}

	// mi creo la lista dei filtri contenuti nel filtro in input
	AbstractList<Filter> filtersList = parafil.getAllFilters();

	// creo un container "Bag" - Questo e' un ParallelFilter
	Bag filtersBag = model.createBag(resourceUri.toString());

	for (Filter fil : filtersList) {
	    // ricorsione
	    // alla fine la risorsa analizzata verra' aggiunta al Container
	    // attuale
	    filtersBag.add(FilterRdfWriter.writerRecursion(model, fil));
	}
	return filterResource;
    }

}
