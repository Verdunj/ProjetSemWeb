package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.example.SensorMeasur.ResultType;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class CSVSensor {

        private final static String defaultPath = "semweb\\src\\main\\java\\com\\example\\data\\csv\\";
        static File folder = new File(CSVSensor.defaultPath);
        static FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                        return name.endsWith("daily-sensor-measures.csv");
                }
        };

        public static List<SensorMeasur> readCSV() {
                List<SensorMeasur> lsMeasurs = new ArrayList<>();
                String[] pathNames = folder.list(filter);

                for (String fileName : pathNames) {
                        try {
                                List<SensorMeasur> beans = new CsvToBeanBuilder<SensorMeasur>(
                                                new FileReader(defaultPath + fileName))
                                                                .withType(SensorMeasur.class)
                                                                .withFieldAsNull(
                                                                                CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                                                                .build()
                                                                .parse();

                                lsMeasurs.addAll(beans);
                        } catch (IllegalStateException | FileNotFoundException e) {
                                e.printStackTrace();
                        }
                }

                return lsMeasurs;
        }

        public static Model addSensoreModel(Model m, List<SensorMeasur> lsMeasurs) {
                m.setNsPrefix("sosa", "http://www.w3.org/ns/sosa/");
                m.setNsPrefix("ex", "http://example/");
                m.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");

                String sosa = "http://www.w3.org/ns/sosa/";
                String ex = "http://example/";
                String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
                int idx = 0;

                Property rdfType = m.createProperty(rdf + "type");

                for (SensorMeasur sensorMeasur : lsMeasurs) {
                        Resource rsId = m.createResource(ex + sensorMeasur.getId());
                        Resource obsId = m.createResource(ex + sensorMeasur.getId() + "_" + idx);
                        String type = sensorMeasur.getResultUOM();
                        Double result = sensorMeasur.getResult();
                        Resource resultResource = m.createResource(sosa + "result" + idx);

                        m.add(
                                        rsId,
                                        rdfType,
                                        m.createResource(sosa + "Sensor"));

                        m.add(
                                        rsId,
                                        m.createProperty(sosa + "madeObservation"),
                                        obsId);

                        m.add(
                                        obsId,
                                        m.createProperty(sosa + "madeBySensor"),
                                        rsId);

                        m.add(
                                        obsId,
                                        rdfType,
                                        m.createResource(sosa + "Observation"));

                        m.add(
                                        obsId,
                                        m.createProperty(sosa + "resultTime"),
                                        ResourceFactory.createTypedLiteral(sensorMeasur.getTime(),
                                                        XSDDatatype.XSDdateTime));

                        // result de l'observation
                        m.add(
                                        obsId,
                                        m.createProperty(sosa + "hasResult"),
                                        resultResource);

                        m.add(
                                        resultResource,
                                        rdfType,
                                        m.createResource(sosa + "Result"));

                        m.add(
                                        resultResource,
                                        m.createProperty(ex + "hasType"),
                                        ResourceFactory.createTypedLiteral(type, XSDDatatype.XSDstring));

                        m.add(
                                        resultResource,
                                        m.createProperty(ex + "hasValue"),
                                        ResourceFactory.createTypedLiteral(result.toString(), XSDDatatype.XSDdouble));

                        m.add(
                                        resultResource,
                                        m.createProperty(sosa + "isResultOf"),
                                        rsId);

                        idx++;
                }

                return m;
        }
}
