package com.example.web.Fuseki;

import java.util.Iterator;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
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
        System.out.println("Alllsdso.?");
        while (results.hasNext()) {
            System.out.println("Alllo.?");
            QuerySolution soln = results.nextSolution();

            Iterator<String> ite = soln.varNames();

            while (ite.hasNext()) {
                System.out.println("Alllo.?");
                RDFNode x = soln.get(ite.next());
                System.err.println("Iterator");
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