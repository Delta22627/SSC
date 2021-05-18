package io.muic.ooc.fab;

public enum AnimalSpecies {
    FOX(Fox::new),
    RABBIT(Rabbit::new);

    private final Animal species;

    AnimalSpecies(Animal species) {
        this.species = species;
    }

    public Animal getSpecies() {
        return this.species;
    }

}
