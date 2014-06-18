package fif_representation.vocabulary;

import org.apache.jena.atlas.logging.Log;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

import fif_representation.utility.CONSTANTS;

/**
 * Class which represents the possible relations of a METADATA.
 * <p>
 * Those relations are specified on the RDF Graph which holds all the RDF's
 * triples.
 * 
 * @author Eugenio Liso
 * @version 1.0
 *
 */
public class METADATA {

    /**
     * Private Constructor.
     */
    private METADATA() {

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
    private static final String N_HAS_ATTRIBUTE = "hasAttribute";

    /**
     * Property useful when creating relations in the RDF Graph.
     */
    public static Property hasAttribute = null;

    /**
     * String that will be appended to the URI specified in this class.
     */
    private static final String N_HAS_FUZZY_SET = "hasFuzzySet";

    /**
     * Property useful when creating relations in the RDF Graph.
     */
    public static Property hasFuzzySet = null;

    /**
     * String that will be appended to the URI specified in this class.
     */
    private static final String N_HAS_INTERPRETATION = "hasInterpretation";

    /**
     * Property useful when creating relations in the RDF Graph.
     */
    public static Property hasInterpretation = null;

    static {
	try {
	    hasAttribute = new PropertyImpl(URI, N_HAS_ATTRIBUTE);
	    hasFuzzySet = new PropertyImpl(URI, N_HAS_FUZZY_SET);
	    hasInterpretation = new PropertyImpl(URI, N_HAS_INTERPRETATION);

	} catch (Exception e) {
	    Log.fatal(FILTER.class.getSimpleName(), e.getMessage());
	}
    }
}
