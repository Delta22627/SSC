package io.muic.ooc.fab;

public class AnimalFactory {

    public static Animal createAnimal(AnimalSpecies animalSpecies, Boolean randomAge, Field field, Location location) throws Exception {
           switch (animalSpecies) {
               case RABBIT:
                   return new Rabbit(randomAge, field, location);
               case FOX:
                   return new Fox(randomAge, field, location);
               case TIGER:
                   return new Tiger(randomAge, field, location);
               case HUNTER:
                   return new Hunter(randomAge, field, location);
               default:
                   throw new Exception("Unknown AnimalSpecies");
       }
   }
}
