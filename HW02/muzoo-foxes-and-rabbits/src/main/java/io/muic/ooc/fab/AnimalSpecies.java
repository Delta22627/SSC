package io.muic.ooc.fab;

public enum AnimalSpecies {
    FOX(Fox::new),
    RABBIT(Rabbit::new);

    private final species;

    AnimalSpecies(Animal species) {
        this.species = species;
    }

    public Animal getAnimal() {
        return this.species;
    }

}
