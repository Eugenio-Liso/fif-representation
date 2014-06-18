package fif_representation.writer;

import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import fif_core.DescriptionBasedFilter;

import fif_core.Filter;

import fif_core.ParallelFilter;

import fif_core.SequenceFilter;

import fif_representation.factory.RecursionFactory;

/**
 * This class allows to represent a decomposition of a Fuzzy Filter as a RDF
 * Standard.
 * <p>
 * There are many ways to represent a Filter, and all the supported ones are
 * defined in the RDFType Enum class.
 * 
 * @author Eugenio Liso
 * @version 1.0
 * @see RDFType
 * @see Filter
 *
 */
public class FilterRdfWriter {
    // hashtable synchronized
    // hashtable non supporta valori nulli (chiave, elem)
    /**
     * Variable useful to store the prefixes used when writing a type of a RDF
     * Document.
     */
    private Map<String, String> prefixesList;

    /**
     * Public Constructor. It gives to Map Interface the Hashtable
     * implementation.
     */
    public FilterRdfWriter() {
	prefixesList = new Hashtable<>();
    }

    /**
     * Write a RDF Document whose type is specified by the RDFType variable. <br>
     * The Filter which will be decomposed and the istance of the Abstract Class
     * Writer have to be istantiated (not null).
     * <p>
     * If the variable type is null, the RDF Document will be written with the
     * default RDF/XML type.
     * 
     * @param filter
     *            The Filter to decompose
     * @param out
     *            Determines where to write the RDF Document
     * @param type
     *            The type of the RDF Document to be written
     * @throws URISyntaxException
     *             Iff a Resource URI cannot be parsed
     */
    public void writeRdf(Filter filter, Writer out, RDFType type)
	    throws URISyntaxException {

	assert filter != null : "Il filtro deve essere stato valorizzato";
	assert out != null : "L'istanza della classe astratta Writer non può essere null";

	// creazione grafo vuoto
	Model modello = ModelFactory.createDefaultModel();

	// inizio la ricorsione passando il modello ed il filtro
	writerRecursion(modello, filter);

	for (String prefix : prefixesList.keySet()) {
	    modello.setNsPrefix(prefix, prefixesList.get(prefix));
	}

	switch (type) {
	case TURTLE: {
	    modello.write(out, "TURTLE");
	    break;
	}
	case RDF_XML: {
	    modello.write(out, "RDF/XML");
	    break;
	}
	case RDF_XML_ABBREV: {
	    modello.write(out, "RDF/XML-ABBREV");
	    break;
	}
	case N3: {
	    modello.write(out, "N3");
	    break;
	}
	case N_TRIPLE: {
	    modello.write(out, "N-TRIPLE");
	    break;
	}
	default: {
	    modello.write(out);
	    break;
	}
	}
    }

    // SE IL VALORE E' GIA' PRESENTE, VERRA' SOVRASCRITTO
    /**
     * This method allows to set the preferred prefixes to be used in the RDF
     * Document.
     * <p>
     * The prefix and resourceName cannot be null.<br>
     * Note that if a prefix has already been defined, the previous stored
     * prefix relation with a resource's name will be overwritten.
     * 
     * @param prefix
     *            The prefix to be stored
     * @param resourceName
     *            The resource's name associated to the prefix
     */
    public void setRdfPrefix(String prefix, String resourceName) {
	assert prefix != null : "Il prefisso deve essere stato valorizzato";
	assert resourceName != null : "Il nome della risorsa non può essere null";

	prefixesList.put(prefix, resourceName);
    }

    // CHAIN OF RESPONSIBILITY
    /**
     * This method contains the writing logic of a RDF Document.
     * 
     * @param model
     *            The model that stores all the relations beetween Resources
     * @param f
     *            The filter to decompose
     * @return The last created Resource
     * @throws URISyntaxException
     *             Iff a Resource URI cannot be parsed
     */
    static Resource writerRecursion(Model model, Filter f)
	    throws URISyntaxException {

	assert model != null : "Il modello deve essere stato valorizzato";

	// variabile utile per aggiungere la risorsa al termine della ricorsione
	Resource filterResource = null;

	// controllo mediante RTTI dell'istanza
	if (f instanceof DescriptionBasedFilter) {
	    // assegnazione valore alla variabile
	    // utile per l'aggiunta della risorsa nel passo ricorsivo
	    filterResource = RecursionFactory.getDescrBasedFilterRecursion()
		    .decomposeFilter(model, f);

	} else if (f instanceof ParallelFilter) {

	    filterResource = RecursionFactory.getParallelFilterRecursion()
		    .decomposeFilter(model, f);

	} else if (f instanceof SequenceFilter) {

	    filterResource = RecursionFactory.getSequenceFilterRecursion()
		    .decomposeFilter(model, f);

	} else {
	    throw new IllegalArgumentException("Il filtro" + f.toString()
		    + " non può essere scomposto");
	}

	return filterResource;
    }

}
