package com.epam.kuzovatov.entity;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Pavel on 21.05.2015.
 */
public class BouquetCreator {
    private static final Logger LOGGER = Logger.getLogger(BouquetCreator.class);
    private static final double ROSE_PRICE = 1000;
    private static final double ROSE_LENGTH = 73.5;
    private static final double CAMOMILE_PRICE = 175;
    private static final double CAMOMILE_LENGTH = 50.1;
    private static final double TULIP_PRICE = 350;
    private static final double TULIP_LENGTH = 72.4;
    private static Random RANDOM = new Random();

    private enum FlowerType {ROSE, CAMOMILE, TULIP}
    private enum AccessoryType {GRASS, ENVELOPE, BOW}
    private enum Color{RED,WHITE,YELLOW}

    private static FlowerType randomFlower(){
        BouquetCreator.FlowerType[] values = FlowerType.values();
        return values[RANDOM.nextInt(values.length)];
    }

    private static String randomColor(){
        BouquetCreator.Color[] values = Color.values();
        return values[RANDOM.nextInt(values.length)].toString();
    }
    /**
     * @param countOfFlowers - count of flowers in the bouquet
     * @param withEnvelope   - envelope for the packaging flowers needed
     * @param withBow        - bow for tie of flowers needed
     * @param withGrass      - gipsofila grass needed for decoration
     * @return - return complete bouquet
     */
    public static List<BouquetComponent> createBouquet(int countOfFlowers, boolean withEnvelope, boolean withBow, boolean withGrass) {
        LOGGER.info("Start bouquet creation!");
        List<BouquetComponent> bouquet = new ArrayList();
        try {
            if (withEnvelope == true) bouquet.add(createAccessory("Envelope"));
            if (withBow == true) bouquet.add(createAccessory("Bow"));
            if (withGrass == true) bouquet.add(createAccessory("Grass"));
            for (int i = 0; i < countOfFlowers; i++) {
                bouquet.add(createFlower());
            }
        }
        catch (Exception error) {
            LOGGER.error("Somthing wrong with bouquet creation: " + error);
        }
        LOGGER.info("Creation succeed!");
            return bouquet;
    }

    public static Flower createFlower() {
        FlowerType flowerType = randomFlower();
        Date currentDate = new Date();
        int randomDayBeforeCurrent = RANDOM.nextInt(currentDate.getDate()-1) + 1;
        Date cutDate = new Date(2015, 6, randomDayBeforeCurrent);
        switch (flowerType) {
            case ROSE:
                return new Flower(UUID.randomUUID(), "Rose", ROSE_PRICE, cutDate, ROSE_LENGTH, randomColor());
            case CAMOMILE:
                return new Flower(UUID.randomUUID(), "Camomile", CAMOMILE_PRICE, cutDate, CAMOMILE_LENGTH, randomColor());
            case TULIP:
                return new Flower(UUID.randomUUID(), "Tulip", TULIP_PRICE, cutDate, TULIP_LENGTH, randomColor());
            default:
                throw new EnumConstantNotPresentException(FlowerType.class, flowerType.name());
        }
    }

    /**
     * @param name - choose one accessory from available: gipsofila, bow, envelope
     * @return
     */
    public static Accessory createAccessory(String name) {
        AccessoryType accessory = AccessoryType.valueOf(name.toUpperCase());
        switch (accessory) {
            case GRASS:
                return new Accessory(UUID.randomUUID(), "Gipsofila", 50, "Green");
            case ENVELOPE:
                return new Accessory(UUID.randomUUID(), "Envelope", 25, randomColor());
            case BOW:
                return new Accessory(UUID.randomUUID(), "Bow", 150, randomColor());
            default:
                throw new EnumConstantNotPresentException(AccessoryType.class, accessory.name());
        }
    }
}