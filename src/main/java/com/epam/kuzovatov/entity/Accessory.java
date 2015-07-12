package com.epam.kuzovatov.entity;

import java.util.UUID;

/**
 * Created by Pavel on 01.06.2015.
 */
public class Accessory extends AbstractBouquetComponent {
    private String color;

    public Accessory(){}

    public Accessory(UUID id, String name, double price, String color) {
        super(id, name, price);
        this.color = color;
    }

    public Accessory(UUID id, String name, double price) {
        super(id, name, price);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(Double price) {
        super.setPrice(price);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
