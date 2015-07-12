package com.epam.kuzovatov;

import com.epam.kuzovatov.parser.SaxBouquetParser;
import com.epam.kuzovatov.parser.StaxBouquetParser;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        SaxBouquetParser saxBouquetParser = new SaxBouquetParser();
        saxBouquetParser.parse("Bouquet.xml");
        StaxBouquetParser staxBouquetParser = new StaxBouquetParser();
        InputStream xmlIn = Main.class.getClassLoader().getResourceAsStream("Bouquet.xml");
        staxBouquetParser.parse(xmlIn);

//        bouquetSaxParser.parse(xmlIn);
        //System.out.println(Boon.toPrettyJson(bouquetSaxParser.parse(xmlIn)));

//        List<BouquetComponent> myBouquet = createBouquet(7, true, true, true);
//        System.out.println(Boon.toPrettyJson(searchFlowerByStemLength(myBouquet, 50.1, 72.5)));
//        System.out.println(Boon.toPrettyJson(sortFlowersByProperty(myBouquet, DAY)));
//        System.out.println(Boon.toPrettyJson(getBouquetPrice(myBouquet)));
    }
}