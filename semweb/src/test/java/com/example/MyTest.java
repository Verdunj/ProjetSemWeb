package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyTest {

    public static void browseDir(String url){
        try {
            Document doc = Jsoup.connect(url).get();

            Elements links = doc.select("a[href~=(?i)\\/]"); //LINK is the unique identifier of "t=60"
            
            links.remove(0);
            System.out.println(links);
            

            for(Element link :links){
                browseDir(url + link.text());
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public static void main(String[] args) {
        browseDir("https://territoire.emse.fr/kg/");
    }
}
