package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Hunter extends Animal implements Predator {
    // Characteristics shared by all hunters (class variables).

    // The age at which a hunter can start to breed.
    private static final int BREEDING_AGE = 25;
    // The age to which a hunter can live.
    // The likelihood of a hunter breeding.
    private static final double BREEDING_PROBABILITY = 0.01;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a hunter can go before it has to eat again.
    // Random generator
    private static final Random RANDOM = new Random();

    // The hunter's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a hunter. A hunter can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the hunter will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(boolean randomAge, Field field, Location location) {
        age = 0;
        setAlive(true);
        this.field = field;
        setLocation(location);
        foodLevel = RANDOM.nextInt(9);
    }

    /**
     * This is what the hunter does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param field The field currently occupied.
     * @param newHunters A list to return newly born hunters.
     */
    @Override
    public void action(List<Animal> newHunters) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newHunters);
            // Move towards a source of food if found.
            Location newLocation = hunt();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = field.freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            }
        }
    }

    @Override
    public int getMaxAge() {
        return 1_000_000_000;
    }


    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location hunt() {
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = rabbit.getFoodValue();
                    return where;
                }
            }
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    foodLevel = fox.getFoodValue();
                    return where;
                }
            }
            if (animal instanceof Tiger) {
                Tiger tiger = (Tiger) animal;
                if (tiger.isAlive()) {
                    tiger.setDead();
                    foodLevel = tiger.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }

    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected Animal createYoung(boolean randomAge, Field field, Location location) {
        return new Hunter(randomAge, field, location);
    }
}
