package io.muic.ooc.fab;

import io.muic.ooc.fab.*;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Tiger extends Animal implements Prey, Predator{
    // Characteristics shared by all tigers (class variables).

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 30;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 500;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 8;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a tiger can go before it has to eat again.

    private static final int FOOD_VALUE = 0;
    // Random generator
    private static final Random RANDOM = new Random();

    // The tiger's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a tiger. A tiger can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tiger(boolean randomAge, Field field, Location location) {
        age = 0;
        setAlive(true);
        this.field = field;
        setLocation(location);
        if (randomAge) {
            age = RANDOM.nextInt(MAX_AGE);
        }
        foodLevel = RANDOM.nextInt(9);
    }

    /**
     * This is what the tiger does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param field The field currently occupied.
     * @param newTigers A list to return newly born tigers.
     */
    @Override
    public void action(List<Animal> newTigers) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newTigers);
            // Move towards a source of food if found.
            Location newLocation = hunt();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = field.freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            }else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Make this tiger more hungry. This could result in the tiger's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
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
        }
        return null;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
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
        return new Tiger(randomAge, field, location);
    }

    public int getFoodValue() {
        return FOOD_VALUE;
    }
}
