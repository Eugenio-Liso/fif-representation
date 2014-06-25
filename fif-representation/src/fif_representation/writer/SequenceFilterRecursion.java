package fif_representation.writer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.vocabulary.RDF;

import fif_core.Filter;
import fif_core.SequenceFilter;
import fif_representation.factory.WriterRecursion;
import fif_representation.utility.Utility;
import fif_representation.vocabulary.FILTER;

/**
 * This class implements the WriterRecursion Interface and provides an
 * implementation of a recursive decomposition of a Sequence Filter.
 * 
 * @author Eugenio Liso
 * @version 1.0
 * @see WriterRecursion
 *
 */
public class SequenceFilterRecursion implements WriterRecursion {

    @Override
    public Resource decomposeFilter(Model model, Filter f)
	    throws URISyntaxException {

	assert model != null : "Il grafo delle relazioni non può essere null.";
	assert f != null : "Il filtro da scomporre non può essere null.";

	Resource filterResource = null;

	// mi creo la lista dei filtri contenuti nel filtro in input
	AbstractList<Filter> filtersList = ((SequenceFilter) f).getAllFilters();

	URI resourceUri = Utility.initializeUri(Utility.createResourceName(f));

	filterResource = Utility.initializeResource(model, resourceUri);

	// aggiungo una proprieta'
	filterResource.addProperty(RDF.type, FILTER.filter);

	// creo un container "Seq" - Questo e' un SequenceFilter
	Seq filtersSeq = model.createSeq(resourceUri.toString());

	for (Filter fil : filtersList) {
	    // ricorsione
	    // alla fine la risorsa analizzata verra' aggiunta al Container
	    // attuale
	    filtersSeq.add(FilterRdfWriter.writerRecursion(model, fil));

	}
	return filterResource;
    }

}
