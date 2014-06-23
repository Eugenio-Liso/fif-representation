package fif_representation.vocabulary;

import org.apache.jena.atlas.logging.Log;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

import fif_representation.utility.CONSTANTS;

/**
 * Class which represents the possible relations of an item of a FUZZY SET.
 * <p>
 * Those relations are specified on the RDF Graph which holds all the RDF's
 * triples.
 * 
 * @author Eugenio Liso
 * @version 1.0
 *
 */
public class FUZZY_SET_ITEM {

    /**
     * Private Constructor.
     */
    private FUZZY_SET_ITEM() {

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
    private static final String N_HAS_ELEMENT = "hasElement";

    /**
     * Property useful when creating relations in the RDF Graph.
     */
    public static Property hasElement = null;

    /**
     * String that will be appended to the URI specified in this class.
     */
    private static final String N_HAS_MEMBERSHIP_VALUE = "hasMembershipValue";

    /**
     * Property useful when creating relations in the RDF Graph.
     */
    public static Property hasMembershipValue = null;

    static {
	try {
	    hasElement = new PropertyImpl(URI, N_HAS_ELEMENT);
	    hasMembershipValue = new PropertyImpl(URI, N_HAS_MEMBERSHIP_VALUE);
	} catch (Exception e) {
	    Log.fatal(FUZZY_SET_ITEM.class.getSimpleName(), e.getMessage());
	}
    }
}
