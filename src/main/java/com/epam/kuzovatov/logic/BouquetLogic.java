package com.epam.kuzovatov.logic;

import com.epam.kuzovatov.entity.AbstractBouquetComponent;
import com.epam.kuzovatov.entity.BouquetComponent;
import com.epam.kuzovatov.entity.Flower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.epam.kuzovatov.entity.Bouquet.getFlowers;

/**
 * Created by Pavel on 04.06.2015.
 */
public class BouquetLogic {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(BouquetLogic.class);
    public enum SearchProperty {DAY,NAME,PRICE}
    /**
     *
     * @param bouquetItems - List of the bouquet components
     * @param minStemLength - minimal stem length
     * @param maxStemLength - maximal stem length
     * @return
     */
    public static List<Flower> searchFlowerByStemLength(List<BouquetComponent> bouquetItems, double minStemLength, double maxStemLength) {
        LOGGER.info("Search by range of stem length started!");
        List<Flower> flowerList = getFlowers(bouquetItems);
        List foundedFlowers = new ArrayList();
        try {
            for (Flower flower : flowerList) {
                if (flower.getStemLength() >= minStemLength && flower.getStemLength() <= maxStemLength)
                    foundedFlowers.add(flower);
            }
        }
        catch (Exception error){
            LOGGER.error("Something wrong in search: "+error);
        }
        LOGGER.info("Search done!");
        return foundedFlowers;
    }

    /**
     *
     * @param bouquetItems - List of the bouquet components
     * @param searchProperty - property of the sorting (day, name, price)
     * @return
     */
    public static List<Flower> sortFlowersByProperty(List<BouquetComponent> bouquetItems, SearchProperty searchProperty) {
        LOGGER.info("Sort by "+searchProperty+" started!");
        List<Flower> unsortedFlowers = getFlowers(bouquetItems);
        List<Flower> sortedFlowers = new ArrayList<>();
        try {
            if (searchProperty.toString() == "DAY") {
                for (int i = 0; i < unsortedFlowers.size(); i++)
                    sortedFlowers.add(unsortedFlowers.get(i));
                AbstractBouquetComponent.DayOldComparator dayOldComparator = new AbstractBouquetComponent.DayOldComparator();
                Collections.sort(sortedFlowers, dayOldComparator);
            }
            if (searchProperty.toString() == "PRICE") {
                for (int i = 0; i < unsortedFlowers.size(); i++)
                    sortedFlowers.add(unsortedFlowers.get(i));
                AbstractBouquetComponent.PriceComparator priceComparator = new AbstractBouquetComponent.PriceComparator();
                Collections.sort(sortedFlowers, priceComparator);
            }
            if (searchProperty.toString() == "NAME") {
                for (int i = 0; i < unsortedFlowers.size(); i++)
                    sortedFlowers.add(unsortedFlowers.get(i));
                AbstractBouquetComponent.NameComparator nameComparator = new AbstractBouquetComponent.NameComparator();
                Collections.sort(sortedFlowers, nameComparator);
            }
        }
        catch (Exception error){
            LOGGER.error("Something wrong in sorting: "+error);
        }
        LOGGER.info("Sort by "+searchProperty+" done!");
        return sortedFlowers;
    }
}
