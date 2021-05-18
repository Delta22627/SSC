package io.muic.ooc.fab;

public class AnimalFactory {

    Double animalProbability;
    Field field;
    Location location;

    public AnimalFactory(Double animalProbability, Field field, Location location){
        this.animalProbability = animalProbability;
        this.field = field;
        this.location = location;

    }

    public Animal getAnimal(Double random){
        if (random <= this.animalProbability){
            return ;
        }

    }
}
