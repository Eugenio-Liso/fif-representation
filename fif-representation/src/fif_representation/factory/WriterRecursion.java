package fif_representation.factory;

import java.net.URISyntaxException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import fif_core.Filter;

/**
 * Interface that defines an object which can decompose an istance of the Filter
 * class.
 * 
 * @author Eugenio Liso
 * @version 1.0
 */
public interface WriterRecursion {
    /**
     * Defines a way to decompose a Filter. The input variables cannot be null.
     * 
     * @param model
     *            The model which will hold all the relations beetween the
     *            filters
     * @param f
     *            The filter to decompose
     * @return An instance of the Resource class
     * @throws URISyntaxException
     *             Iff a Resource URI cannot be parsed
     */
    Resource decomposeFilter(Model model, Filter f) throws URISyntaxException;
}
