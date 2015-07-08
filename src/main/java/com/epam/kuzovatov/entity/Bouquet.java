package com.epam.kuzovatov.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Pavel on 27.05.2015.
 */
public class Bouquet extends AbstractBaseEntity {
    private List<BouquetComponent> bouquet;
    private String name;

    public Bouquet(){
        super(UUID.randomUUID());
        bouquet = new ArrayList<>();
    }

    public Bouquet(UUID id, String name, List<BouquetComponent> bouquet) {
        super(id);
        this.name = name;
        this.bouquet = bouquet;
    }

    public static List<Flower> getFlowers(List<BouquetComponent> newBouquet) {
        List<Flower> bouquetFlowers = new ArrayList();
        for (int i = 0; i < newBouquet.size(); i++) {
            if (newBouquet.get(i) instanceof Flower)
                bouquetFlowers.add((Flower) newBouquet.get(i));
        }
        return bouquetFlowers;
    }

    public static double getBouquetPrice(List<BouquetComponent> newBouquet) {
        double bouquetPrice = 0;
        for (int i = 0; i < newBouquet.size(); i++) {
            bouquetPrice += newBouquet.get(i).getPrice();
        }
        return bouquetPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getBouquet() {
        List bouquet = this.bouquet;
        return bouquet;
    }

    public void setBouquet(List bouquet) {
        this.bouquet = bouquet;
    }

    public void add(Flower flower) {
        bouquet.add(flower);
    }
}
