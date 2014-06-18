package fif_representation.factory;

import fif_representation.writer.DescrBasedFilterRecursion;
import fif_representation.writer.ParallelFilterRecursion;
import fif_representation.writer.SequenceFilterRecursion;

//FACTORY - SINGLETON
/**
 * This class keeps the references of the various classes that implements the
 * WriterRecursion interface.
 * <p>
 * Depending on the method called, it will return an object that exhibits the
 * WriterRecursion interface but holds a different implementation.
 * 
 * @author Eugenio Liso
 * @version 1.0
 */
public class RecursionFactory {
    /**
     * Private Constructor
     */
    private RecursionFactory() {
    }

    // STRATEGY
    /**
     * Variable that holds the recursive implementation of a Parallel Filter.
     */
    private static WriterRecursion parallelFilter = new ParallelFilterRecursion();
    /**
     * Variable that holds the recursive implementation of a Sequence Filter.
     */
    private static WriterRecursion sequenceFilter = new SequenceFilterRecursion();
    /**
     * Variable that holds the recursive implementation of Description Based
     * Filter.
     */
    private static WriterRecursion descrBasedFilter = new DescrBasedFilterRecursion();

    /**
     * Returns an object that exhibits the WriterRecursion interface but holds
     * the recursive implementation of a Parallel Filter's decomposition.
     * 
     * @return An object that contains an implementation of a Parallel Filter's
     *         decomposition.
     */
    public static WriterRecursion getParallelFilterRecursion() {
	return parallelFilter;
    }

    /**
     * Returns an object that exhibits the WriterRecursion interface but holds
     * the recursive implementation of a Sequence Filter's decomposition.
     * 
     * @return An object that contains an implementation of a Sequence Filter's
     *         decomposition.
     */
    public static WriterRecursion getSequenceFilterRecursion() {
	return sequenceFilter;
    }

    /**
     * Returns an object that exhibits the WriterRecursion interface but holds
     * the recursive implementation of a Description Based Filter's
     * decomposition.
     * 
     * @return An object that contains an implementation of a Description Based
     *         Filter's decomposition.
     */
    public static WriterRecursion getDescrBasedFilterRecursion() {
	return descrBasedFilter;
    }
}
