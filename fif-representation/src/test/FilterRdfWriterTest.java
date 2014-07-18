package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

import fif_core.Attribute;
import fif_core.ClosedVeristicInterpretation;
import fif_core.DescriptionBasedFilter;
import fif_core.Filter;
import fif_core.FuzzySet;
import fif_core.Metadata;
import fif_core.OWA;
import fif_core.OpenVeristicInterpretation;
import fif_core.ParallelFilter;
import fif_core.PossibilisticInterpretation;
import fif_core.SequenceFilter;
import fif_core.interfaces.Aggregator;
import fif_representation.utility.CONSTANTS;
import fif_representation.vocabulary.AGGREGATOR;
import fif_representation.vocabulary.DESCRIPTION_BASED_FILTER;
import fif_representation.vocabulary.FILTER;
import fif_representation.vocabulary.FUZZY_SET_ITEM;
import fif_representation.vocabulary.METADATA;
import fif_representation.vocabulary.OWA_AGGREGATOR;
import fif_representation.vocabulary.PARALLEL_FILTER;
import fif_representation.writer.FilterRdfWriter;
import fif_representation.writer.RDFType;
import junit.framework.TestCase;

public class FilterRdfWriterTest extends TestCase {
    private FilterRdfWriter test;
    private Filter[] filterTest;
    private RDFType[] rdfTypes;

    protected void setUp() throws Exception {
	test = new FilterRdfWriter();
	filterTest = populateFilter();
	rdfTypes = populateTypes();
	super.setUp();
    }

    protected void tearDown() throws Exception {
	test = null;
	filterTest = null;
	rdfTypes = null;
	super.tearDown();
    }

    private RDFType[] populateTypes() {
	RDFType[] result = new RDFType[5];

	for (int i = 0; i < result.length; i++) {
	    result[i] = chooseRdfType(i);
	}
	return result;
    }

    private Filter istantiate(int i) {
	switch (i) {
	case 0:
	    return istantiateFirstFilter();
	case 1:
	    return istantiateSecondFilter();
	case 2:
	    return istantiateThirdFilter();
	case 3:
	    return istantiateFourthFilter();
	case 4:
	    return istantiateFifthFilter();
	case 5:
	    return istantiateSixthFilter();
	case 6:
	    return istantiateSeventhFilter();
	case 7:
	    return istantiateEightFilter();
	case 8:
	    return istantiateNinthFilter();
	case 9:
	    return istantiateTenthFilter();
	case 10:
	    return istantiateEleventhFilter();
	case 11:
	    return istantiateTwelfthFilter();
	default:
	    return null;
	}
    }

    // 2 ATTRIBUTI, FUZZY SET NON VUOTI (ELEMS > 1), 2 METADATA, 2 DBF, 1
    // SEQUENCE FILTER
    private Filter istantiateFirstFilter() {
	// mi creo un attributo

	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();
	// setto dei valori associati al primo fuzzy set
	fs1.setValue("horror", 0.7);
	fs1.setValue("dark romance", 0.2);

	FuzzySet fs2 = new FuzzySet();
	// setto dei valori associati al secondo fuzzy set
	fs2.setValue("1980", 0.2);
	fs2.setValue("1981", 0.2);
	fs2.setValue("1982", 0.3);
	fs2.setValue("1983", 0.4);
	fs2.setValue("1984", 0.6);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());
	Metadata m2 = new Metadata(att2, fs2,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	Filter f2 = new DescriptionBasedFilter(m2);

	// creazione sequence filter
	Filter s1 = new SequenceFilter(f1, f2);

	return s1;
    }

    // 3 ATTRIBUTI, 3 FUZZY SET NON VUOTI (ELEMS > 1), 3 METADATI, 3 DBF, 1
    // AGGREGATOR, 1 PARALLEL FILTER
    private Filter istantiateSecondFilter() {

	Attribute attribute1 = new Attribute("string:genere");
	Attribute attribute2 = new Attribute("string:editore");
	Attribute attribute3 = new Attribute("string:autore");

	FuzzySet fs_utente1 = new FuzzySet();
	FuzzySet fs2_utente1 = new FuzzySet();
	FuzzySet fs3_utente1 = new FuzzySet();

	fs_utente1.setValue("fantasy", 1.0);
	fs_utente1.setValue("romanzo gotico", 1.0);
	fs_utente1.setValue("horror", 0.7);
	fs_utente1.setValue("thriller", 0.5);
	fs_utente1.setValue("fantascienza", 0.3);
	fs_utente1.setValue("romanzo distopico", 0.4);
	fs_utente1.setValue("steampunk", 0.3);
	fs_utente1.setValue("storico", 0.3);

	fs2_utente1.setValue("salani", 1.0);
	fs2_utente1.setValue("mondadori", 0.9);
	fs2_utente1.setValue("urania", 0.5);
	fs2_utente1.setValue("TEA", 0.4);

	fs3_utente1.setValue("J.K Rowling", 1.0);
	fs3_utente1.setValue("David Eddings", 1.0);
	fs3_utente1.setValue("Terry Brooks", 1.0);
	fs3_utente1.setValue("Anne Rice", 0.8);

	Metadata metadata_utente1 = new Metadata(attribute1, fs_utente1,
		OpenVeristicInterpretation.getinstance());
	Metadata metadata2_utente1 = new Metadata(attribute2, fs2_utente1,
		OpenVeristicInterpretation.getinstance());
	Metadata metadata3_utente1 = new Metadata(attribute3, fs3_utente1,
		OpenVeristicInterpretation.getinstance());

	DescriptionBasedFilter filtro_utente1 = new DescriptionBasedFilter(
		metadata_utente1);
	DescriptionBasedFilter filtro2_utente1 = new DescriptionBasedFilter(
		metadata2_utente1);
	DescriptionBasedFilter filtro3_utente1 = new DescriptionBasedFilter(
		metadata3_utente1);

	Aggregator owa = new OWA(0.25, 0.25, 0.5);

	ParallelFilter filtroPar = new ParallelFilter(owa, filtro_utente1,
		filtro2_utente1, filtro3_utente1);

	return filtroPar;
    }

    // 2 ATTRIBUTI, 1 FUZZY SET > 1, 1 FUZZY SET VUOTO, 2 METADATA, 2 DBF, 1
    // SEQUENCE FILTER
    private Filter istantiateThirdFilter() {

	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();

	FuzzySet fs2 = new FuzzySet();
	// setto dei valori associati al secondo fuzzy set
	fs2.setValue("1980", 0.2);
	fs2.setValue("1981", 0.2);
	fs2.setValue("1982", 0.3);
	fs2.setValue("1983", 0.4);
	fs2.setValue("1984", 0.6);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());
	Metadata m2 = new Metadata(att2, fs2,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	Filter f2 = new DescriptionBasedFilter(m2);

	// creazione sequence filter
	Filter s1 = new SequenceFilter(f1, f2);

	return s1;
    }

    // 2 ATTRIBUTI, 2 FUZZY SET VUOTI, 2 METADATA, 2 DBF, 1
    // SEQUENCE FILTER
    private Filter istantiateFourthFilter() {

	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();

	FuzzySet fs2 = new FuzzySet();

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());
	Metadata m2 = new Metadata(att2, fs2,
		OpenVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	Filter f2 = new DescriptionBasedFilter(m2);

	// creazione sequence filter
	Filter s1 = new SequenceFilter(f1, f2);

	return s1;
    }

    // 1 ATTRIBUTO, 1 FUZZY SET NON VUOTO (ELEMS = 1), 1 METADATA, 1 DBF, 1
    // SEQUENCE FILTER
    private Filter istantiateFifthFilter() {

	Attribute att1 = new Attribute("string:genere");

	FuzzySet fs1 = new FuzzySet();
	// setto dei valori associati al primo fuzzy set
	fs1.setValue("horror", 0.7);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		PossibilisticInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);

	// creazione sequence filter
	Filter s1 = new SequenceFilter(f1);

	return s1;
    }

    // 1 ATTRIBUTO, 1 FUZZY SET > 1, 1 METADATA, 1 DBF, 1 AGGREGATOR CON 1 PESO,
    // 1 PARALLEL FILTER
    private Filter istantiateSixthFilter() {
	Attribute attribute = new Attribute("string:genere");

	FuzzySet fs_utente1 = new FuzzySet();

	fs_utente1.setValue("fantasy", 1.0);
	fs_utente1.setValue("romanzo gotico", 1.0);
	fs_utente1.setValue("horror", 0.7);
	fs_utente1.setValue("thriller", 0.5);
	fs_utente1.setValue("fantascienza", 0.3);
	fs_utente1.setValue("romanzo distopico", 0.4);
	fs_utente1.setValue("steampunk", 0.3);
	fs_utente1.setValue("storico", 0.3);

	Metadata metadata_utente1 = new Metadata(attribute, fs_utente1,
		OpenVeristicInterpretation.getinstance());
	DescriptionBasedFilter filtro_utente1 = new DescriptionBasedFilter(
		metadata_utente1);

	Aggregator owa = new OWA(1);

	ParallelFilter filtroPar = new ParallelFilter(owa, filtro_utente1);
	return filtroPar;
    }

    // 2 ATTRIBUTI, 2 FUZZY SET NON VUOTI (ELEMS = 1), 2 METADATA, 2 DBF, 1
    // SEQUENCE FILTER
    private Filter istantiateSeventhFilter() {
	// mi creo un attributo

	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();
	// setto dei valori associati al primo fuzzy set
	fs1.setValue("horror", 0.7);

	FuzzySet fs2 = new FuzzySet();
	// setto dei valori associati al secondo fuzzy set
	fs2.setValue("1980", 0.2);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());

	Metadata m2 = new Metadata(att2, fs2,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	// mi creo un Description Based Filter
	Filter f2 = new DescriptionBasedFilter(m2);

	// creazione sequence filter
	Filter s1 = new SequenceFilter(f1, f2);

	return s1;
    }

    // 2 ATTRIBUTI, 1 FUZZY SET = 1, 1 FUZZY SET VUOTO, 2 METADATA, 2 DBF, 1
    // PARALLEL FILTER (f1, f2), 1 AGGREGATOR CON 2 PESI
    private Filter istantiateEightFilter() {
	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();

	FuzzySet fs2 = new FuzzySet();
	// setto dei valori associati al secondo fuzzy set
	fs2.setValue("1980", 0.2);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());
	Metadata m2 = new Metadata(att2, fs2,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	Filter f2 = new DescriptionBasedFilter(m2);

	Aggregator owa = new OWA(0.5, 0.5);

	// creazione sequence filter
	Filter s1 = new ParallelFilter(owa, f1, f2);

	return s1;
    }

    // 2 ATTRIBUTI, 1 FS VUOTO, 1 FS > 1, 2 METADATA, 2 DBF, 3
    // PARALLEL FILTER F3[(f1, f2)], 2 AGGREGATOR DI CUI UNO IN COMUNE
    private Filter istantiateNinthFilter() {
	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();

	FuzzySet fs2 = new FuzzySet();
	// setto dei valori associati al secondo fuzzy set
	fs2.setValue("1980", 0.2);
	fs2.setValue("1981", 0.2);
	fs2.setValue("1982", 0.3);
	fs2.setValue("1983", 0.4);
	fs2.setValue("1984", 0.6);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());
	Metadata m2 = new Metadata(att2, fs2,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	Filter f2 = new DescriptionBasedFilter(m2);

	Aggregator owa1 = new OWA(1.0);

	Aggregator owa2 = new OWA(0.7, 0.3);

	// creazione parallel filter
	Filter s1 = new ParallelFilter(owa1, f1);
	Filter s2 = new ParallelFilter(owa1, f2);

	Filter s3 = new ParallelFilter(owa2, s1, s2);

	return s3;
    }

    // 2 ATTRIBUTI, 1 FS VUOTO, 1 FS > 1, 2 METADATA, 2 DBF, 2 PARALLEL FILTER,
    // 2 OWA, 1 SEQUENCE FILTER
    private Filter istantiateTenthFilter() {
	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();

	FuzzySet fs2 = new FuzzySet();
	// setto dei valori associati al secondo fuzzy set
	fs2.setValue("1980", 0.2);
	fs2.setValue("1981", 0.2);
	fs2.setValue("1982", 0.3);
	fs2.setValue("1983", 0.4);
	fs2.setValue("1984", 0.6);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());
	Metadata m2 = new Metadata(att2, fs2,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	Filter f2 = new DescriptionBasedFilter(m2);

	Aggregator owa1 = new OWA(1.0);
	Aggregator owa2 = new OWA(1.0);

	// creazione parallel filter
	Filter s1 = new ParallelFilter(owa1, f1);
	Filter s2 = new ParallelFilter(owa2, f2);

	Filter s3 = new SequenceFilter(s1, s2);

	return s3;
    }

    // 2 ATTRIBUTI, UN FUZZY SET > 1 IN COMUNE A DUE METADATI, 2 DBF, 1 SEQUENCE
    // FILTER
    private Filter istantiateEleventhFilter() {
	// mi creo un attributo

	Attribute att1 = new Attribute("string:genere");
	Attribute att2 = new Attribute("string:anno");

	FuzzySet fs1 = new FuzzySet();
	// setto dei valori associati al secondo fuzzy set
	fs1.setValue("1980", 0.2);
	fs1.setValue("1981", 0.2);
	fs1.setValue("1982", 0.3);
	fs1.setValue("1983", 0.4);
	fs1.setValue("1984", 0.6);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());
	Metadata m2 = new Metadata(att2, fs1,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);
	Filter f2 = new DescriptionBasedFilter(m2);

	// creazione sequence filter
	Filter s1 = new SequenceFilter(f1, f2);

	return s1;
    }

    // 1 Attributo, 1 FS vuoto, 1 Metadata, 1 DBF, 1 OWA con 1 peso, 1 PARALLEL
    private Filter istantiateTwelfthFilter() {
	// mi creo un attributo

	Attribute att1 = new Attribute("string:genere");

	FuzzySet fs1 = new FuzzySet();

	fs1.setValue("horror", 0.7);

	// creazione metadati
	Metadata m = new Metadata(att1, fs1,
		ClosedVeristicInterpretation.getinstance());

	// mi creo un Description Based Filter
	Filter f1 = new DescriptionBasedFilter(m);

	Aggregator agg = new OWA(1.0);
	// creazione sequence filter
	Filter s1 = new ParallelFilter(agg, f1);

	return s1;
    }

    private Filter[] populateFilter() {
	Filter[] test = new Filter[12];
	for (int i = 0; i < test.length; i++) {
	    test[i] = istantiate(i);
	}
	return test;
    }

    private void setPrefixes(FilterRdfWriter fw) {
	// settaggio dei prefissi del namespace
	fw.setRdfPrefix("fuzzy-owl", CONSTANTS.ONTOLOGY_URI);
	fw.setRdfPrefix("fuzzy", CONSTANTS.RESOURCE_URI);
	fw.setRdfPrefix("xsd", CONSTANTS.XSD_URI);
	fw.setRdfPrefix("rdf", CONSTANTS.RDF_URI);

    }

    private void testFailed(Resource res, Property prop) {
	fail("La proprieta' " + prop.toString() + " associata alla risorsa "
		+ res.toString() + " non e' ammessa");
    }

    private RDFType chooseRdfType(int i) {

	switch (i) {
	case 0: {
	    return RDFType.TURTLE;
	}
	case 1: {
	    return RDFType.N3;
	}
	case 2: {
	    return RDFType.N_TRIPLES;
	}
	case 3: {
	    return RDFType.RDF_XML;
	}
	case 4: {
	    return RDFType.RDF_XML_ABBREV;
	}
	default:
	    throw new IndexOutOfBoundsException("Intero " + i + " fuori range");

	}
    }

    private String chooseInputLanguage(int i) {
	switch (i) {
	case 0: {
	    return "TURTLE";
	}
	case 1: {
	    return "N3";
	}
	case 2: {
	    return "N-TRIPLE";
	}
	case 3: {
	    return "RDF/XML";
	}
	case 4: {
	    return "RDF/XML-ABBREV";
	}
	default:
	    throw new IndexOutOfBoundsException("Intero " + i + " fuori range");

	}
    }

    public void testWriteRdf() throws FileNotFoundException, URISyntaxException {
	// setto i prefissi
	setPrefixes(test);

	for (int i = 0; i < filterTest.length; i++) {
	    Filter filterToWrite = filterTest[i];

	    for (int j = 0; j < rdfTypes.length; j++) {
		// scrivo il file per il test
		RDFType type = chooseRdfType(j);
		test.writeRdf(filterToWrite, new PrintWriter("src/output/test"
			+ type + "_" + "Filtro" + (i + 1) + ".txt"), type);

		// importo il file
		Model model = ModelFactory.createDefaultModel();
		model.read(new FileInputStream("src/output/test" + type + "_"
			+ "Filtro" + (i + 1) + ".txt"), null,
			chooseInputLanguage(j));

		// ottengo la lista di TUTTI i soggetti nel grafo
		ResIterator it = model.listSubjects();
		while (it.hasNext()) {
		    Resource res = it.next();

		    String localName = res.getLocalName();

		    // se non e' un nodo anonimo
		    if (!(res.isAnon())) {

			// Iteratore che filtra gli statements.
			// Seleziono solo quelli che hanno la stessa risorsa
			StmtIterator iterOnStmts = model
				.listStatements(new SimpleSelector(res, null,
					(RDFNode) null));

			System.out.println("---------------------"
				+ "\nNome Locale: " + localName + "\n");

			if (localName.contains("Metadata")) {
			    while (iterOnStmts.hasNext()) {

				Statement nextStmt = iterOnStmts.next();
				Property prop = nextStmt.getPredicate();
				RDFNode object = nextStmt.getObject();

				System.out.println("\nSoggetto: "
					+ nextStmt.getSubject().toString()
					+ "\n" + "Predicato: "
					+ prop.toString() + "\n" + "Oggetto: "
					+ object.toString());

				if (prop.equals(METADATA.hasAttribute)) {
				    // mi assicuro che l'oggetto di questa
				    // relazione
				    // sia una risorsa
				    assertTrue(object.isResource());
				} else if (prop
					.equals(METADATA.hasInterpretation)) {
				    // l'interpretazione e' un letterale
				    assertTrue(object.isLiteral());
				    String litName = object.asLiteral()
					    .getString();

				    // controllo che rientri nelle 3
				    // possibilita'
				    // previste
				    boolean equals = litName
					    .equalsIgnoreCase("PossibilisticInterpretation")
					    || litName
						    .equalsIgnoreCase("OpenVeristicInterpretation")
					    || litName
						    .equalsIgnoreCase("ClosedVeristicInterpretation");
				    assertTrue(equals);

				} else {
				    assertTrue(prop
					    .equals(METADATA.hasFuzzySet));
				}
			    }

			} else if (localName.contains("SequenceFilter")) {
			    while (iterOnStmts.hasNext()) {

				Statement nextStmt = iterOnStmts.next();
				Property prop = nextStmt.getPredicate();

				System.out.println("\nSoggetto: "
					+ nextStmt.getSubject().toString()
					+ "\n" + "Predicato: "
					+ prop.toString() + "\n" + "Oggetto: "
					+ nextStmt.getObject().toString());

				if (prop.equals(RDF.type)) {

				    RDFNode object = nextStmt.getObject();
				    // mi assicuro che l'oggetto sia una risorsa
				    assertTrue(object.isResource());

				    Resource newRes = object.asResource();

				    assertTrue((newRes.equals(RDF.Seq) || (newRes
					    .equals(FILTER.filter))));
				} else {
				    // creo l'iteratore associato al soggetto
				    // della
				    // relazione (un SequenceFilter)
				    // la Seq e' legata al soggetto
				    Resource subject = nextStmt.getSubject();
				    NodeIterator iterSeq = model
					    .getSeq(subject).iterator();

				    while (iterSeq.hasNext()) {
					RDFNode nodeSeq = iterSeq.next();
					// controllo se e' una risorsa
					assertTrue(nodeSeq.isResource());
					// non ho bisogno di altri controlli,
					// perche'
					// gli
					// altri nodi verranno analizzati in
					// seguito
				    }
				}
			    }
			} else if (localName.contains("DescriptionBasedFilter")) {
			    while (iterOnStmts.hasNext()) {
				Statement nextStmt = iterOnStmts.next();
				Property prop = nextStmt.getPredicate();

				System.out.println("\nSoggetto: "
					+ nextStmt.getSubject().toString()
					+ "\n" + "Predicato: "
					+ prop.toString() + "\n" + "Oggetto: "
					+ nextStmt.getObject().toString());

				RDFNode object = nextStmt.getObject();
				// mi assicuro che l'oggetto sia una risorsa
				assertTrue(object.isResource());

				Resource newRes = object.asResource();

				if (prop.equals(RDF.type)) {

				    // controllo che il soggetto sia un Filtro a
				    // sua
				    // volta
				    assertTrue(newRes.equals(FILTER.filter));
				} else {
				    assertTrue(prop
					    .equals(DESCRIPTION_BASED_FILTER.hasDescription));
				}
			    }
			} else if (localName.contains("ParallelFilter")) {
			    while (iterOnStmts.hasNext()) {
				Statement nextStmt = iterOnStmts.next();
				Property prop = nextStmt.getPredicate();
				RDFNode object = nextStmt.getObject();
				System.out.println("\nSoggetto: "
					+ nextStmt.getSubject().toString()
					+ "\n" + "Predicato: "
					+ prop.toString() + "\n" + "Oggetto: "
					+ object.toString());

				if (prop.equals(RDF.type)) {

				    // Controllo se l'oggetto e' una risorsa
				    assertTrue(object.isResource());

				    Resource newRes = object.asResource();
				    assertTrue(newRes.equals(RDF.Bag)
					    || newRes.equals(FILTER.filter));

				} else if (prop
					.equals(PARALLEL_FILTER.hasAggregator)) {

				    // Resource newRes = object.asResource();
				    // assertTrue(newRes
				    // .equals(AGGREGATOR.aggregator));

				    // controllo che l'oggetto della relazione
				    // sia una risorsa (mi riferisco all'OWA)
				    assertTrue(object.isResource());

				} else {

				    // creo l'iteratore associato al soggetto
				    // della
				    // relazione (un ParallelFilter)
				    // la Bag e' legata al soggetto
				    Resource subject = nextStmt.getSubject();
				    NodeIterator iterSeq = model
					    .getBag(subject).iterator();
				    while (iterSeq.hasNext()) {
					RDFNode nodeSeq = iterSeq.next();
					// controllo se e' una risorsa
					assertTrue(nodeSeq.isResource());
					// non ho bisogno di altri controlli,
					// perche'
					// gli
					// altri nodi verranno analizzati in
					// seguito
				    }

				}

			    }
			} else if (localName.contains("FuzzySet")) {
			    while (iterOnStmts.hasNext()) {
				// final perche' la utilizzo in una classe
				// anonima
				final Statement nextStmt = iterOnStmts.next();

				Property prop = nextStmt.getPredicate();

				System.out.println("\nSoggetto: "
					+ nextStmt.getSubject().toString()
					+ "\n" + "Predicato: "
					+ prop.toString() + "\n" + "Oggetto: "
					+ nextStmt.getObject().toString());

				if (prop.equals(RDF.type)) {

				    RDFNode object = nextStmt.getObject();
				    // Controllo se l'oggetto e' una risorsa
				    assertTrue(object.isResource());

				    Resource newRes = object.asResource();

				    assertTrue(newRes.equals(RDF.Bag));
				} else {

				    // creo l'iteratore associato al soggetto
				    // della
				    // relazione
				    // la Bag e' legata al soggetto
				    Resource subject = nextStmt.getSubject();

				    NodeIterator iterSeq = model
					    .getBag(subject).iterator();

				    while (iterSeq.hasNext()) {

					// final perche' utilizzata in una
					// classe
					// anonima
					final RDFNode nodeSeq = iterSeq.next();

					// Iteratore specifico per i fuzzySet
					// items
					StmtIterator fuzzyItemsIter = model
						.listStatements(new SimpleSelector(
							nodeSeq.asResource(),
							null, (RDFNode) null) {
						    @Override
						    public boolean selects(
							    Statement s) {
							return nextStmt
								.getObject()
								.asResource()
								.equals(nodeSeq);
						    }
						});

					while (fuzzyItemsIter.hasNext()) {

					    Statement nextFsItem = fuzzyItemsIter
						    .next();
					    Property propItem = nextFsItem
						    .getPredicate();

					    RDFNode object = nextFsItem
						    .getObject();

					    // l'oggetto è sempre un letterale
					    // (elemento
					    // o MSV)
					    assertTrue(object.isLiteral());

					    // il soggetto è un blank node
					    assertTrue(nextFsItem.getSubject()
						    .isAnon());

					    Literal lit = object.asLiteral();

					    if (propItem
						    .equals(FUZZY_SET_ITEM.hasElement)) {
						/*
						String resUri = lit
							.getDatatypeURI();
						String trueUri = String.join(
							"", CONSTANTS.XSD_URI,
							"string");
						assertTrue(resUri
							.equalsIgnoreCase(trueUri));
							*/

						assertNotNull(lit.getString());

						System.out.println("Elemento: "
							+ lit.getString());
					    } else if (propItem
						    .equals(FUZZY_SET_ITEM.hasMembershipValue)) {

						assertNotNull(lit.getDouble());
						// ritorna
						// http://www.w3.org/2001/XMLSchema#double
						String resUri = lit
							.getDatatypeURI();
						String trueUri = String.join(
							"", CONSTANTS.XSD_URI,
							"double");
						assertTrue(resUri
							.equalsIgnoreCase(trueUri));

						System.out
							.println("ValoreMembership: "
								+ lit.getString());

					    } else {
						testFailed(res, propItem);
					    }
					}
				    }
				}

			    }
			} else if (localName.contains("OWA")) {
			    while (iterOnStmts.hasNext()) {
				Statement nextStmt = iterOnStmts.next();
				Property prop = nextStmt.getPredicate();
				RDFNode object = nextStmt.getObject();

				System.out.println("\nSoggetto: "
					+ nextStmt.getSubject().toString()
					+ "\n" + "Predicato: "
					+ prop.toString() + "\n" + "Oggetto: "
					+ object.toString());

				Resource newRes = object.asResource();

				if (prop.equals(RDF.type)) {
				    assertTrue(object.isResource());

				    assertTrue(newRes
					    .equals(AGGREGATOR.aggregator));

				} else if (prop
					.equals(OWA_AGGREGATOR.hasValues)) {

				    // l'oggetto è una risorsa anonima
				    assertTrue(newRes.isAnon());

				    StmtIterator iterNR = model.listStatements(
					    newRes, null, (RDFNode) null);

				    while (iterNR.hasNext()) {
					Statement st = iterNR.next();
					Property propNR = st.getPredicate();
					RDFNode objectNR = st.getObject();

					System.out.println("\nSoggetto: "
						+ st.getSubject().toString()
						+ "\n" + "Predicato: "
						+ propNR.toString() + "\n"
						+ "Oggetto: "
						+ objectNR.toString());

					if (propNR.equals(RDF.type)) {
					    assertTrue(objectNR.equals(RDF.Seq));
					} else {
					    assertTrue(objectNR.isLiteral());

					    Literal newResLit = objectNR
						    .asLiteral();
					    String resUri = newResLit
						    .getDatatypeURI();
					    String trueUri = String
						    .join("",
							    CONSTANTS.XSD_URI,
							    "double");

					    assertTrue(resUri
						    .equalsIgnoreCase(trueUri));

					}
				    }
				}
			    }

			} else {
			    testFailed(res, null);

			}
		    }
		}
	    }
	}
    }
}
