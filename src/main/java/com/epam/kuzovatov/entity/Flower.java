package com.epam.kuzovatov.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Created by Pavel on 01.06.2015.
 */
public class Flower extends AbstractBouquetComponent implements Comparator<Flower>, Comparable<Flower> {
    private Date cutDate;
    private double stemLength;
    private String color;

    //public Flower(){}

    public Flower(){
//        super();
//        this.cutDate = null;
//        this.stemLength = 0.0;
//        this.color = null;
    }

    public Flower(UUID id, String name, double price, Date cutDate, double stemLength, String color) {
        super(id, name, price);
        this.cutDate = cutDate;
        this.stemLength = stemLength;
        this.color = color;
    }

    public int calculateDayAfterCut(Date cutDate) {
        Date currentDate = new Date();
        int dayOld = currentDate.getDate() - cutDate.getDate();
        return dayOld;
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    public Date getCutDate() {
        return cutDate;
    }

    public void setCutDate(Date cutDate) {
        this.cutDate = cutDate;
    }

    public double getStemLength() {
        return stemLength;
    }

    public void setStemLength(Double stemLength) {
        this.stemLength = stemLength;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(Double price) {
        super.setPrice(price);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int compareTo(Flower flower) {
        Flower comparedFlower = flower;
        if (comparedFlower.cutDate.getDate() < this.cutDate.getDate())
            return 1;
        if (comparedFlower.cutDate.getDate() > this.cutDate.getDate())
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + ", stem length is: " + getStemLength() + ", day(s) after cut: " + calculateDayAfterCut(getCutDate());
    }

    public int compare(Flower o1, Flower o2) {
        return 0;
    }

    public Comparator<Flower> reversed() {
        return null;
    }

    public Comparator<Flower> thenComparing(Comparator<? super Flower> other) {
        return null;
    }

    public <U> Comparator<Flower> thenComparing(Function<? super Flower, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return null;
    }

    public <U extends Comparable<? super U>> Comparator<Flower> thenComparing(Function<? super Flower, ? extends U> keyExtractor) {
        return null;
    }

    public Comparator<Flower> thenComparingInt(ToIntFunction<? super Flower> keyExtractor) {
        return null;
    }

    public Comparator<Flower> thenComparingLong(ToLongFunction<? super Flower> keyExtractor) {
        return null;
    }

    public Comparator<Flower> thenComparingDouble(ToDoubleFunction<? super Flower> keyExtractor) {
        return null;
    }
}
