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
        String query = "PREFIX time: <http://www.w3.org/2006/time#> PREFIX time: <http://www.w3.org/2006/time#> PREFIX ex:   <http://example/> SELECT ?t ?heure WHERE{?h ex:at \"Saint-Etienne\". ?h ex:hasTemp ?t. ?h time:hours ?heure.}";
        String query2 = "PREFIX ex:   <http://example/> PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX sosa: <http://www.w3.org/ns/sosa/> PREFIX time: <http://www.w3.org/2006/time#> SELECT ?t ?v WHERE {?r rdf:type sosa:Result. ?r ex:hasType \"Cel\". ?r sosa:isResultOf ?o. ?o sosa:resultTime ?t. ?r ex:hasValue ?v.}";
        ResultSet res = conn.execReturn(query);
        ResultSet res2 = conn.execReturn(query2);
        float temp = 0;
        int nbRes = 0;
        int n = 0;
        String tempsSensor = "";
        float tempeSensor = 0;
        while (res.hasNext()) {
            QuerySolution soln = res.nextSolution();
            temp = Float.parseFloat(soln.get("t").toString().split(" ")[0]);
            int heure = Integer.parseInt(soln.get("heure").toString().split(" ")[0]);
            System.out.println("temp : " + temp + " heure : " + heure);

        }

        while (res2.hasNext()) {

            QuerySolution soln = res2.nextSolution();
            tempsSensor = soln.get("t").asLiteral().getString();
            tempeSensor = Float.parseFloat(soln.get("v").asLiteral().getString());
            String salle = soln.get("v").asLiteral().getString();
            if (n < 100) {
                System.out.println("boucle " + tempsSensor + " " + tempeSensor + " Salle: " + salle);
                n++;
            }
        }
        System.out.println("fin " + tempsSensor + " " + tempeSensor);
        return "temperature.html";
    }
}
