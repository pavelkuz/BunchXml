package com.epam.kuzovatov.entity;

import java.util.Comparator;
import java.util.UUID;

/**
 * Created by Pavel on 21.05.2015.
 */
public abstract class AbstractBouquetComponent extends AbstractBaseEntity implements BouquetComponent {
    private String name;
    private double price;

    public AbstractBouquetComponent(){
        super(UUID.randomUUID());
    }

    public AbstractBouquetComponent(UUID id, String name, double price) {
        super(id);
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = (int) (31 * result + price);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flower flower = (Flower) o;

        if (price != flower.getPrice()) return false;
        if (name != null ? !name.equals(flower.getName()) : flower.getName() != null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "\n" + "Bouquet item name: " + getName() + ", wich price is: " + getPrice();
    }

    public static class DayOldComparator implements Comparator<Flower> {
        public int compare(Flower o1, Flower o2) {
            return o1.calculateDayAfterCut(o1.getCutDate()) - o2.calculateDayAfterCut(o2.getCutDate());
        }
    }

    public static class PriceComparator implements Comparator<Flower> {
        public int compare(Flower o1, Flower o2) {
            return (int) (o1.getPrice() - o2.getPrice());
        }
    }

    public static class NameComparator implements Comparator<Flower> {
        public int compare(Flower o1, Flower o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
