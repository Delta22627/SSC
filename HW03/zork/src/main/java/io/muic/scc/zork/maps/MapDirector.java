package io.muic.scc.zork.maps;

import io.muic.scc.zork.commands.CommandWord;
import io.muic.scc.zork.items.Weapon;

import java.util.HashMap;
import java.util.List;

public class MapDirector {
    private static HashMap<String, MapSelection> validMaps;
    private static final MapDirector INSTANCE = new MapDirector();
    private MapDirector(){
        validMaps = new HashMap<>();
        validMaps.put("muic",MapSelection.MUIC);
        validMaps.put("2",MapSelection.TWO);
    }

    public static MapDirector getInstance(){
        return INSTANCE;
    }

    public static MapRoom createMap(String map) {
        MapSelection mapSelection = validMaps.get(map);
        System.out.println(mapSelection);
        switch (mapSelection) {
            case MUIC:
                return createFirstMap();
            case TWO:
                return createSecondMap();
            default:
                System.out.println("no select map");
        }
        return null;
    }

    public static MapRoom createFirstMap(){
        MapRoom outside, comSciFourth, library, comSciFifth, bioLab, chemLab, sciDivision, secretaryDesk, kanatOffice, pitiOffice, twlOffice;

        // create the rooms
        outside = new MapRoom("outside the main entrance of Mahidol University International College old building");
        comSciFourth = new MapRoom("in 1406 Computer Science Lounge.");
        library = new MapRoom("in Library.");
        comSciFifth = new MapRoom("in 1502 Computer Science Lounge.");
        bioLab = new MapRoom("in 3508 Biology Lab.");
        chemLab = new MapRoom("in 3504 Chemistry Lab.");
        sciDivision = new MapRoom("in Science Division.");
        secretaryDesk = new MapRoom("at Science Division Secretary Desk.");
        kanatOffice = new MapRoom("at AJ Kanat office.");
        pitiOffice = new MapRoom("at AJ Piti Office.");
        twlOffice = new MapRoom("at AJ Taweetham Office.");

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

        comSciFifth.setMonsters(2);
        library.setMonsters(1);

        Weapon axe = new Weapon("axe");
        library.addItem(axe);
        return outside;  // start game outside
        // update the value of the class variables
    }

    public static MapRoom createSecondMap(){
            MapRoom outside, comSciFourth, library, comSciFifth, bioLab, chemLab, sciDivision, secretaryDesk, kanatOffice, pitiOffice, twlOffice;
        System.out.println("second map selected");
            // create the rooms
            outside = new MapRoom("outside the main entrance of Mahidol University International College old building");
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

            comSciFifth.setMonsters(2);
            library.setMonsters(1);


            return outside;  // start game outside
    }
}
