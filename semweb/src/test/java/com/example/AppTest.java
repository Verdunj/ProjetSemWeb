package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    void testApp() {
        assertEquals(1, 1);
    }

    @Test
    void browseDirTest(){
        browseDir("https://territoire.emse.fr/kg/");
    }

    void browseDir(String url){
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
            e.printStackTrace();
        }
    }

    @Test
    void downloadRDF(){
        try {
            String savePath = "src\\main\\java\\com\\example\\data\\";
            String url = "https://territoire.emse.fr/kg/emse/fayol/";
            Document doc = Jsoup.connect(url).get();


            // Elements links = doc.select("a[href$=.rdf]"); //LINK is the unique identifier of "t=60"
            // Elements links = doc.select("a[href$=/]"); //LINK is the unique identifier of "t=60"
            // Elements links = doc.select("a[href~=(?!i)\\.(rdf|jfonld|n3|nt|ttl|sh)]"); //LINK is the unique identifier of "t=60"
            Elements links = doc.select("a[href~=(?i)[a-z]\\/]"); //LINK is the unique identifier of "t=60"
            System.out.println(links.remove(0));
            int linksSize = links.size();
            if (linksSize > 0) {
                if (linksSize > 1) {
                    System.out.println("Warning: more than one link found.  Downloading first match.");
                }
                Element link = links.first();
                //this returns an absolute URL
                String  linkUrl = link.attr("abs:href");
                System.out.println(link.text());
                System.out.println(linkUrl);

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
                FileOutputStream fos = new FileOutputStream(savePath + savedFileName);
                fos.write(bytes);
                fos.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            assertEquals(0, 1);
        }
        
        
    }
}
