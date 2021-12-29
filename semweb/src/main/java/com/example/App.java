package com.example;

import java.io.IOException;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.rdf.model.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String url = "https://www.meteociel.fr/temps-reel/obs_villes.php?code2=7475";

        RDFWritter emseRDF = new RDFWritter();
        // emseRDF.downloadAll();
        emseRDF.setFileName();

        Model model = ModelFactory.createDefaultModel();

        /*
         * for (String fileName : emseRDF.getLsFileRDF()) {
         * model.read(emseRDF.getSavePath() + "\\" + fileName, "RDF/XML");
         * }
         */
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String todayYear = formatterYear.format(date);
        Scraping scrap = new Scraping(url);
        scrap.parseTableRow();
        String ex = "http://example/";
        String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
        String time = "http://www.w3.org/2006/time#";
        model.setNsPrefix("ex", "http://example/");
        model.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        model.setNsPrefix("time", "http://www.w3.org/2006/time#");
        Resource dateResource = model.createResource(ex + "d");
        Resource typeResource = model.createResource(time + "DateTimeDescription");
        Property type = model.createProperty(rdf + "type");
        model.add(dateResource, type, typeResource);
        model.add(dateResource, model.createProperty(time + "hasYear"),
                ResourceFactory.createTypedLiteral(todayYear, XSDDatatype.XSDgYear));
        for (ItemMeteo item : scrap.getItems()) {
            System.out.println(item.getTemperature());

        }

        // emseRDF.addNodeFromModel(model);

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
