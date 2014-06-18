package fif_representation.writer;

/**
 * Enum class that represents the types of RDF which can be choosed as output of
 * the FilterRdfWriter class.
 * 
 * @author Eugenio Liso
 * @version 1.0
 * @see FilterRdfWriter
 */
public enum RDFType {
    /**
     * Constants that can be choosed as output of the FilterRdfWriter class.
     */
    TURTLE, RDF_XML, RDF_XML_ABBREV, N_TRIPLE, N3;

    /**
     * Private Constructor
     */
    private RDFType() {

    }
}
