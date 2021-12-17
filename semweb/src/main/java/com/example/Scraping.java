package com.example;

import java.io.IOException;

import org.apache.jena.riot.SysRIOT;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraping {
    public String url;
    Document content;

    public Scraping(String url) throws IOException {
        this.url = url;
        this.content = Jsoup.connect(this.url).get();

    }

    public void parseTableRow() {
        Elements rows = this.content.getElementsByTag("table").get(7).getElementsByTag("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            for (int j = 0; j < cols.size(); j++) {
                System.out.print(cols.get(j).text() + " ");

            }
            System.out.println("------------");

        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.meteociel.fr/temps-reel/obs_villes.php?code2=7475";
        Scraping scrap = new Scraping(url);
        scrap.parseTableRow();

    }

}
