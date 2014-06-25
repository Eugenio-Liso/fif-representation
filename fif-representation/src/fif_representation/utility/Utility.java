package fif_representation.utility;

import java.net.URI;
import java.net.URISyntaxException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Utility class which holds methods used by different classes in the
 * fif_representation.writer package.
 * 
 * @author Eugenio Liso
 * @version 1.0
 *
 */
public class Utility {

    /**
     * Private Constructor
     */
    private Utility() {

    }

    /**
     * Creates a Resource name which will be used by an object of the Model
     * class. The input object cannot be null.
     * 
     * @param obj
     *            The object whereof create a Resource name
     * @return A string representing the Resource name of obj
     */
    public static String createResourceName(Object obj) {

	assert obj != null : "L'oggetto di cui si vuole ottenere il nome non può essere null.";
	return CONSTANTS.RESOURCE_URI + obj.getClass().getSimpleName() + "_"
		+ Integer.toHexString(obj.hashCode());
    }

    /**
     * Initialize an object of the URI class from a String (which will be parsed
     * to make sure it conforms the URI standards). The string to be parsed
     * cannot be null.
     *
     * @param s
     *            The String from which create an object of the URI class
     * @return An object of the URI class
     * @throws URISyntaxException
     *             Iff a Resource URI cannot be parsed
     * @see URI
     */
    public static URI initializeUri(String s) throws URISyntaxException {
	assert s != null : "La stringa da parserizzare come URI non può essere null.";
	// questa istruzione necessita del throws URISyntaxException
	URI uri = new URI(s);
	return uri;

    }

    /**
     * Initialize a Resource with the specified URI in a Model object. The model
     * and the URI cannot be null.
     * 
     * @param model
     *            The model object in which create a Resource
     * @param uri
     *            An URI object which will be represented as a Resource in the
     *            given Model object
     * @return The object of type Resource created
     */
    public static Resource initializeResource(Model model, URI uri) {
	assert model != null : "Il modello (grafo) non può essere null.";
	assert uri != null : "L'URI da inserire nel modello non può essere null.";
	// creazione risorsa
	Resource filterResource = model.createResource(uri.toString());

	return filterResource;
    }
}
