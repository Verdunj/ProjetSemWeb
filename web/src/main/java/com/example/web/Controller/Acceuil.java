package com.example.web.Controller;

import javax.sound.midi.SysexMessage;

import com.example.web.Fuseki.ConnexionFuseki;

import org.apache.jena.query.QueryParseException;
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

}
