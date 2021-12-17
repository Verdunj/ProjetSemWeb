package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class CSVSensor {

    private final static String defaultPath = "src/main/java/com/example/data/csv/";
    static File folder = new File(CSVSensor.defaultPath);
    static FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File f, String name) {
            return name.endsWith(".csv");
        }
    };

    public static List<SensorMeasur> readCSV() {
        List<SensorMeasur> lsMeasurs = new ArrayList<>();
        String[] pathNames = folder.list(filter);

        for (String fileName : pathNames) {
            System.out.println(fileName);
            try {
                List<SensorMeasur> beans = new CsvToBeanBuilder<SensorMeasur>(new FileReader(defaultPath + fileName))
                        .withType(SensorMeasur.class)
                        .build()
                        .parse();

                lsMeasurs.addAll(beans);
            } catch (IllegalStateException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println(lsMeasurs.get(0));
        return lsMeasurs;
    }
}
