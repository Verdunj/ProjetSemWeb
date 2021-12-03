package com.example;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

public class Server {

    private static String URL = "http://localhost:3030";
    private static String DATASET_NAME = "/dataset";

    private FusekiServer server;
    private RDFConnectionRemoteBuilder builder;

    public Server() {
        
    }

    public Server(Dataset ds) {
        server = FusekiServer.create().port(3030).add(DATASET_NAME, ds).build();
    }

    public void initServer() {
        server = FusekiServer.create().build();
    }

    public void start(){
        server.start();
    }

    public void connect(){
        builder = RDFConnectionFuseki.create().destination(URL);
    }

    public void connect(String url){
        builder = RDFConnectionFuseki.create().destination(url+"/update");
    }

    public Boolean insert(Node node) {

        String query = "INSERT DATA {" + node.getSubject() + " " + node.getPredicate() + " " + node.getObject()
                + ". } ;";

        System.out.println(query);

        // In this variation, a connection is built each time.
        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            UpdateRequest request = UpdateFactory.create();

            request.add(query);
            conn.update(request);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public Boolean insertTest(){
        String data = ":test  rdf:etat   rdf:test";
        String query = "prefix INSERT DATA {" +data+". } ;";

        String query2 = " INSERT { <http://example/egbook> dc:title  'test sans prefix' } WHERE {}";

        System.out.println(query);

        // In this variation, a connection is built each time.
        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            UpdateRequest request = UpdateFactory.create();

            request.add(query2);
            conn.update(request);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }
}
