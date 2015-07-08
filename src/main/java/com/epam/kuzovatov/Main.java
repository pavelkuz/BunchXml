package com.epam.kuzovatov;

import com.epam.kuzovatov.parser.BouquetSaxParser;

public class Main {
    public static void main(String[] args) {
        BouquetSaxParser bouquetSaxParser = new BouquetSaxParser();
        bouquetSaxParser.parse("Bouquet.xml");

//        List<BouquetComponent> myBouquet = createBouquet(7, true, true, true);
//        System.out.println(Boon.toPrettyJson(searchFlowerByStemLength(myBouquet, 50.1, 72.5)));
//        System.out.println(Boon.toPrettyJson(sortFlowersByProperty(myBouquet, DAY)));
//        System.out.println(Boon.toPrettyJson(getBouquetPrice(myBouquet)));
    }
}