package io.muic.scc.zork.maps;

import java.util.List;

public class MapDirector {

    public void createFirstMap(){
        MapRoom outside, comSciFourth, library, comSciFifth, bioLab, chemLab, sciDivision, secretaryDesk, kanatOffice, pitiOffice, twlOffice;

        // create the rooms
        outside = new MapRoom("outside the main entrance of the Mahidol University International College old building");
        comSciFourth = new MapRoom("in 1406 Computer Science Lounge");
        library = new MapRoom("in Library");
        comSciFifth = new MapRoom("in 1502 Computer Science Lounge");
        bioLab = new MapRoom("in 3508 Biology Lab");
        chemLab = new MapRoom("in 3504 Chemistry Lab");
        sciDivision = new MapRoom("in Science Division");
        secretaryDesk = new MapRoom("at Science Division Secretary Desk");
        kanatOffice = new MapRoom("at AJ Kanat office, surely you have question to ask.");
        pitiOffice = new MapRoom("at AJ Piti Office and he is not there.");
        twlOffice = new MapRoom("at AJ Taweetham Office. Have you done his work?");

        // initialise room exits
        outside.setExits("up", library);
        comSciFourth.setExits("up", bioLab);
        library.setExits("north", comSciFourth);
        bioLab.setExits("west", chemLab);
        chemLab.setExits("west", sciDivision);
        sciDivision.setExits("south", comSciFifth);
        comSciFifth.setExits("down", library);
        secretaryDesk.setExits("east", sciDivision);
        kanatOffice.setExits("north", secretaryDesk);
        pitiOffice.setExits("west", kanatOffice);
        twlOffice.setExits("north", comSciFifth);

        currentMapRoom = outside;  // start game outside
        // update the value of the class variables
        connected = kanatOffice;
    }

    public void createSecondMap(){

    }
}
