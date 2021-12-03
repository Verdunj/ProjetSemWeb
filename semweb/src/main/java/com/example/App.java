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
        Model model = ModelFactory.createDefaultModel();
        model.read("semweb/src/main/java/com/example/ontology.rdf", "RDF/XML");

        StmtIterator iter = model.listStatements();
        List<Node> lsNodes = new ArrayList<>();

        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); // get next statement
            Resource subject = stmt.getSubject(); // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject(); // get the object

            Node node = new Node(subject, predicate, object);
            lsNodes.add(node);

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }

        // Dataset ds = DatasetFactory.create(uriList);



        FusekiServer server = FusekiServer.create()
                .build();
        server.start();


    }
}
