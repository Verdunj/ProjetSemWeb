package com.example.web.Fuseki;

import java.util.Iterator;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

public class ConnexionFuseki {
    String url = "http://localhost:3030/dataset";

    public ConnexionFuseki() {
    }

    public void execSelectAndPrint(String query) {
        System.out.println("Test2");
        QueryExecution q = QueryExecutionFactory.sparqlService(url,
                query);
        ResultSet results = q.execSelect();
        // ResultSetFormatter.out(System.out, results);

        while (results.hasNext()) {

            QuerySolution soln = results.nextSolution();

            Iterator<String> ite = soln.varNames();

            while (ite.hasNext()) {
                RDFNode x = soln.get(ite.next());
                System.err.println(x.toString());
            }

        }
    }

    public ResultSet execReturn(String query) {
        QueryExecution q = QueryExecutionFactory.sparqlService(url,
                query);
        ResultSet results = q.execSelect();
        return results;
    }

}