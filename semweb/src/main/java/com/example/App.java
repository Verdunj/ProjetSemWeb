package com.example;

import java.util.List;

import java.io.IOException;

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
     * @throws IOException
     */
    public static void main(String[] args) {
        

        //////////////////////////////
        // CREATION DU MODEL //
        //////////////////////////////

        RDFWritter emseRDF = new RDFWritter();
        // emseRDF.downloadAll();
        // emseRDF.setFileName();

        Model dataModel = ModelFactory.createDefaultModel();

        // Model from rdf file
        for(String fileName :emseRDF.getLsFileRDF()){
                dataModel.read(emseRDF.getSavePath()+fileName, "RDF/XML");
        }

        // Model from CSV file
        List<SensorMeasur> lsMeasurs = CSVSensor.readCSV();
        dataModel = CSVSensor.addSensoreModel(dataModel, lsMeasurs);

        // Model from weather web site
        WeatherModel weatherModel = new WeatherModel();
        dataModel = ModelFactory.createUnion(dataModel, weatherModel.getModel());

        //////////////////////////////
        // AJOUT DU MODEL AU SERVER //
        //////////////////////////////

        Dataset ds = DatasetFactory.create(dataModel);

        Server server = new Server(ds);

        server.start();
        // String urlTest = "http://localhost:3030/test";
        // server.connect(urlTest);

        // server.insertTest();
        // server.insert(lsNodes.get(1));

        }
}
