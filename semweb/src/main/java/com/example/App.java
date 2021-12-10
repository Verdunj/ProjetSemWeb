package com.example;

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
        emseRDF.setFileName();

        Model model = ModelFactory.createDefaultModel();

        for(String fileName :emseRDF.getLsFileRDF()){
            model.read(emseRDF.getSavePath()+fileName, "RDF/XML");
        }

        emseRDF.addNodeFromXML(model);
        // emseRDF.printListNode();

        Dataset ds = DatasetFactory.create(model);

        Server server = new Server(ds);

        server.start();
        // String urlTest = "http://localhost:3030/test";
        // server.connect(urlTest);

        // server.insertTest();
        // server.insert(lsNodes.get(1));

    }
}
