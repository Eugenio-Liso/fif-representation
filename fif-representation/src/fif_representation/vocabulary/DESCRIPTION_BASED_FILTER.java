package fif_representation.vocabulary;

import org.apache.jena.atlas.logging.Log;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

import fif_representation.utility.CONSTANTS;

/**
 * Class which represents the possible relations of a DESCRIPTION BASED FILTER.
 * <p>
 * Those relations are specified on the RDF Graph which holds all the RDF's
 * triples.
 * 
 * @author Eugenio Liso
 * @version 1.0
 *
 */
public class DESCRIPTION_BASED_FILTER {

    /**
     * Private Constructor.
     */
    private DESCRIPTION_BASED_FILTER() {

    }

    /**
     * Final String representing the URI associated to this class.
     */
    protected static final String URI = CONSTANTS.ONTOLOGY_URI;

    /**
     * Returns the URI associated to this class. It can be referred to ANY of
     * the constants defined in the CONSTANTS class.
     * 
     * @return A String representing an URI
     * @see CONSTANTS
     */
    public static String getURI() {
	return URI;
    }

    /**
     * String that will be appended to the URI specified in this class.
     */
    private static final String N_HAS_DESCRIPTION = "hasDescription";

    /**
     * Property useful when creating relations in the RDF Graph.
     */
    public static Property hasDescription = null;

    static {
	try {
	    hasDescription = new PropertyImpl(URI, N_HAS_DESCRIPTION);
	} catch (Exception e) {
	    Log.fatal(DESCRIPTION_BASED_FILTER.class.getSimpleName(), e.getMessage());
	}
    }
}
