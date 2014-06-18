package fif_representation.writer;

import java.net.URI;
import java.net.URISyntaxException;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import fif_core.DescriptionBasedFilter;
import fif_core.Filter;
import fif_core.FuzzySet;
import fif_core.Metadata;
import fif_core.interfaces.Interpretation;
import fif_representation.factory.WriterRecursion;
import fif_representation.utility.Utility;
import fif_representation.vocabulary.DESCRIPTION_BASED_FILTER;
import fif_representation.vocabulary.FILTER;
import fif_representation.vocabulary.FUZZY_SET_ITEM;
import fif_representation.vocabulary.METADATA;

/**
 * This class implements the WriterRecursion Interface and provides an
 * implementation of a recursive decomposition of a Description Based Filter.
 * 
 * @author Eugenio Liso
 * @version 1.0
 * @see WriterRecursion
 *
 */
public class DescrBasedFilterRecursion implements WriterRecursion {

    @Override
    public Resource decomposeFilter(Model model, Filter f)
	    throws URISyntaxException {
	Resource filterResource;
	// passo BASE della ricorsione
	DescriptionBasedFilter newFilter = (DescriptionBasedFilter) f;

	// creazione URI e Risorsa di un filtro
	URI resourceUri = Utility.initializeUri(Utility
		.createResourceName(newFilter));

	filterResource = Utility.initializeResource(model, resourceUri);

	// ottengo l'unico metadato associato al DBF
	Metadata metadata = newFilter.getMetadata();

	// ottengo l'uri del metadato
	URI metadataUri = Utility.initializeUri(Utility
		.createResourceName(metadata));

	// creo la risorsa associata al metadato
	Resource metadataResource = Utility.initializeResource(model,
		metadataUri);

	// aggiungo alla risorsa il collegamento con il metadato
	filterResource.addProperty(DESCRIPTION_BASED_FILTER.hasDescription,
		metadataResource);
	filterResource.addProperty(RDF.type, FILTER.filter);

	// aggiungo le proprietà definite sul metadato

	String attributeName = metadata.getAttribute().getAttributeName();

	// URI attributeUri = Utility.initializeUri(String.join(":", "string",
	// attributeName));

	URI attributeUri = Utility.initializeUri(attributeName);

	Resource attributeResource = Utility.initializeResource(model,
		attributeUri);
	metadataResource.addProperty(METADATA.hasAttribute, attributeResource);

	// aggiungo la interpretazione identificata con il nome semplice della
	// classe
	Interpretation interp = metadata.getInterpretation();
	metadataResource.addProperty(METADATA.hasInterpretation,
		model.createLiteral(interp.getClass().getSimpleName()));

	// memorizzo l'uri del FuzzySet
	FuzzySet fs = metadata.getFuzzySet();
	URI fuzzySetUri = Utility.initializeUri(Utility.createResourceName(fs));

	// creo la risorsa associata al fuzzySet
	Resource fuzzySetResource = Utility.initializeResource(model,
		fuzzySetUri);

	// creo una nuova proprieta'
	metadataResource.addProperty(METADATA.hasFuzzySet, fuzzySetResource);

	// creazione bag di elementi-membershipvalues
	Bag fuzzyElements = model.createBag(fuzzySetUri.toString());

	// ciclo sugli elementi del fuzzyset
	for (String element : fs.getSupport()) {
	    // acquisisco il membershipValue associato all'elemento
	    double value = fs.getValue(element);

	    // creazione risorsa anonima
	    Resource blankNode = model.createResource();

	    // aggiunta delle due proprieta'
	    blankNode.addProperty(FUZZY_SET_ITEM.hasElement,
		    model.createTypedLiteral(element, XSDDatatype.XSDstring));

	    blankNode.addProperty(FUZZY_SET_ITEM.hasMembershipValue, model
		    .createTypedLiteral(String.valueOf(value),
			    XSDDatatype.XSDdouble));

	    fuzzyElements.add(blankNode);
	}

	return filterResource;

    }

}
