package com.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.*;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        RDFWritter emseRDF = new RDFWritter();
        // emseRDF.downloadAll();

        Model model = ModelFactory.createDefaultModel();
        model.read(emseRDF.getSavePath()+"ontology.rdf", "RDF/XML");
        model.read(emseRDF.getSavePath()+"shower.rdf", "RDF/XML");

        emseRDF.addNodeFromXML(model);
        emseRDF.printListNode();

        Dataset ds = DatasetFactory.create(model);

        Server server = new Server(ds);

        server.start();
        // String urlTest = "http://localhost:3030/test";
        // server.connect(urlTest);

        // server.insertTest();
        // server.insert(lsNodes.get(1));

    }
}
