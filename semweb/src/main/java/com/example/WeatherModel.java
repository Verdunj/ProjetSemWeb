package com.example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.andrewoma.dexx.collection.Function;

import org.apache.jena.assembler.assemblers.DefaultModelAssembler;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class WeatherModel {
    // default url to get content
    private String defaultUrl = "https://www.meteociel.fr/temps-reel/obs_villes.php?code2=7475";

    // date formatter
    private final static SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat formatterM = new SimpleDateFormat("MM");
    private final static SimpleDateFormat formatterD = new SimpleDateFormat("dd");

    // prefix
    private final static String ex = "http://example/";
    private final static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private final static String time = "http://www.w3.org/2006/time#";

    // Model
    private Model weatherModel = ModelFactory.createDefaultModel();

    public WeatherModel() {
        this.createWeatherModel();
    }

    public WeatherModel(String url) {
        super();
        this.setDefaultUrl(url);
        this.createWeatherModel();
    }

    public Model createWeatherModel() {
        Scraping scrap;
        try {
            scrap = scrapWebSite();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Date date = new Date();
        String todayYear = formatterYear.format(date);

        // set prefix
        weatherModel.setNsPrefix("ex", "http://example/");
        weatherModel.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        weatherModel.setNsPrefix("time", "http://www.w3.org/2006/time#");

        Resource dateResource = weatherModel.createResource(ex + "d");
        Resource typeResource = weatherModel.createResource(time + "DateTimeDescription");
        Property type = weatherModel.createProperty(rdf + "type");

        weatherModel.add(dateResource, type, typeResource);
        weatherModel.add(dateResource, weatherModel.createProperty(time + "year"),
                ResourceFactory.createTypedLiteral(todayYear, XSDDatatype.XSDgYear));

        weatherModel.add(dateResource, weatherModel.createProperty(time + "month"),
                ResourceFactory.createTypedLiteral(formatterM.format(date), XSDDatatype.XSDgMonth));

        weatherModel.add(dateResource, weatherModel.createProperty(time + "day"),
                ResourceFactory.createTypedLiteral(formatterD.format(date), XSDDatatype.XSDgDay));

        this.addWebContent(scrap, dateResource);

        return weatherModel;
    }

    private Scraping scrapWebSite() throws IOException {
        Scraping scrap;
        scrap = new Scraping(defaultUrl);
        scrap.parseTableRow();

        return scrap;
    }

    private void addWebContent(Scraping scrap, Resource dateResource) {
        for (ItemMeteo item : scrap.getItems()) {
            weatherModel.add(dateResource, weatherModel.createProperty(ex + "hasTime"),
                    weatherModel.createResource(ex + ("H" + item.getHeure()).replace(" ", "_")));

            weatherModel.add(weatherModel.getResource(ex + ("H" + item.getHeure()).replace(" ",
                    "_")), weatherModel.createProperty(ex + "at"),
                    ResourceFactory.createTypedLiteral("Saint-Etienne", XSDDatatype.XSDstring));

            weatherModel.add(weatherModel.getResource(ex + ("H" + item.getHeure()).replace(" ", "_")),
                    weatherModel.createProperty(ex + "hasTemp"),
                    ResourceFactory.createTypedLiteral(item.getTemperature(),
                            XSDDatatype.XSDinteger));
            weatherModel.add(weatherModel.getResource(ex + ("H" + item.getHeure()).replace(" ",
                    "_")), weatherModel.createProperty(time + "hours"),
                    ResourceFactory.createTypedLiteral(item.getHeure(), XSDDatatype.XSDdecimal));

        }
    }

    public Model getModel() {
        return weatherModel;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}