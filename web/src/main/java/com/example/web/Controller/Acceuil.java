package com.example.web.Controller;

import com.example.web.Fuseki.ConnexionFuseki;

import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Acceuil {
    @RequestMapping(value = "/acceuil")
    public String acceuil(Model m) {

        return "acceuil.html";
    }

    @RequestMapping(value = "/querying")
    public String selectQuery(@RequestParam(value = "query", required = true) String query, Model m) {
        System.err.println(query);
        ConnexionFuseki conn = new ConnexionFuseki();
        try {
            conn.execSelectAndPrint(query);
        } catch (QueryParseException e1) {
            System.err.println(e1);
            System.err.println();
            String err = e1.toString();
            m.addAttribute("erreur", err);
        }
        return "view.html";

    }

    @RequestMapping(value = "/temp")
    public String temp(Model m) {
        ConnexionFuseki conn = new ConnexionFuseki();
        String query = "PREFIX time: <http://www.w3.org/2006/time#> PREFIX time: <http://www.w3.org/2006/time#> PREFIX ex:   <http://example/> SELECT * WHERE{?h ex:at \"Saint-Etienne\". ?h ex:hasTemp ?t.}";
        String query2 = "PREFIX ex:   <http://example/> PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX sosa: <http://www.w3.org/ns/sosa/> PREFIX time: <http://www.w3.org/2006/time#> SELECT ?t ?v WHERE {?r rdf:type sosa:Result. ?r ex:hasType \"Cel\". ?r sosa:isResultOf ?o. ?o sosa:resultTime ?t. ?r ex:hasValue ?v.}";
        ResultSet res = conn.execReturn(query);
        conn.execSelectAndPrint(query2);
        float totalTemp = 0;
        int nbRes = 0;
        while (res.hasNext()) {
            QuerySolution soln = res.nextSolution();
            totalTemp += Float.parseFloat(soln.get("t").toString().split(" ")[0]);
            nbRes++;

        }
        System.err.println("Moyenne : " + (totalTemp / nbRes));
        return "temperature.html";
    }
}
