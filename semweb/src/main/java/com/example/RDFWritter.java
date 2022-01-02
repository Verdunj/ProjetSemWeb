package com.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RDFWritter {
    private List<Node> lsNodes = new ArrayList<>();
    private List<String> lsFileRDF = new ArrayList<>();
    private String savePath = "src/main/java/com/example/data/rdf/";
    private String url = "https://territoire.emse.fr/kg/";

    // Use default value
    public RDFWritter() {
    }

    public RDFWritter(String savePath, String url) {
        this.savePath = savePath;
        this.url = url;
    }

    public void downloadAll() {
        downloadAll(this.url);
    }

    public void downloadAll(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Elements links = doc.select("a[href~=(?i)\\/]"); // LINK is the unique identifier of "t=60"
            links.remove(0);

            downloadRDF(url);

            for (Element link : links) {
                downloadAll(url + link.text());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public boolean downloadRDF(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            Elements links = doc.select("a[href$=.rdf]"); // LINK is the unique identifier of "t=60"
            int linksSize = links.size();

            if (linksSize > 0) {
                for (Element link : links) {
                    // this returns an absolute URL
                    String linkUrl = link.attr("abs:href");

                    byte[] bytes = Jsoup.connect(linkUrl)
                            .header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            .referrer(linkUrl)
                            .ignoreContentType(true)
                            .maxBodySize(0)
                            .timeout(600000)
                            .execute()
                            .bodyAsBytes();

                    String savedFileName = link.text();
                    FileOutputStream fos = new FileOutputStream(this.savePath + savedFileName);

                    this.addFileName(savedFileName);

                    fos.write(bytes);
                    fos.close();
                }

            }

            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public void setFileName() {
        File directory = new File(this.getSavePath());
        File[] contents = directory.listFiles();
        List<String> lsFileRDF = new ArrayList<>();
        if (directory.exists()) {
            System.out.println("il existe");
        } else {
            System.out.println("il existe pas");
        }
        for (File elem : contents) {
            lsFileRDF.add(elem.getName());
        }

        this.setLsFileRDF(lsFileRDF);
    }

    public void addNodeFromModel(Model model) {
        StmtIterator iter = model.listStatements();

        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement(); // get next statement
            Resource subject = stmt.getSubject(); // get the subject
            Property predicate = stmt.getPredicate(); // get the predicate
            RDFNode object = stmt.getObject(); // get the object

            Node node = new Node(subject, predicate, object);
            lsNodes.add(node);
        }
    }

    public void printListNode() {

        for (Node node : lsNodes) {
            Resource subject = node.getSubject(); // get the subject
            Property predicate = node.getPredicate(); // get the predicate
            RDFNode object = node.getObject(); // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }

    }

    public List<Node> getLsNodes() {
        return lsNodes;
    }

    public void setLsNodes(List<Node> lsNodes) {
        this.lsNodes = lsNodes;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addFileName(String name) {
        this.lsFileRDF.add(name);
    }

    public List<String> getLsFileRDF() {
        return lsFileRDF;
    }

    public void setLsFileRDF(List<String> lsFileRDF) {
        this.lsFileRDF = lsFileRDF;
    }

}
