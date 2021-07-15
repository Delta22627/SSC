package io.muic.scc.zork;

import io.muic.scc.zork.commands.Command;
import io.muic.scc.zork.commands.CommandWord;
import io.muic.scc.zork.commands.Parser;
import io.muic.scc.zork.maps.Direction;
import io.muic.scc.zork.maps.MapRoom;

import java.util.List;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game
{
    private final Parser parser;
    private MapRoom currentMapRoom;
    private MapRoom previousMapRoom;
    private int magic_counter = 5; // the counter that help change the transporterRoom location
    private List<MapRoom> allMapRooms; // list of all rooms that is not transporterRoom
    private MapRoom connected; // the room that ''go magic'' will transport to
    private MapRoom transporterMapRoom; // the magic room that will change randomly

        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together. Except the room that the magic room is transport to.
     */
    private void createRooms()
    {
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
        transporterMapRoom = new MapRoom("Where are you?");
        
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
        transporterMapRoom.setExits("north", kanatOffice);

        currentMapRoom = outside;  // start game outside
        // update the value of the class variables
        connected = kanatOffice;
        allMapRooms = List.of(outside, comSciFourth, library, comSciFifth, bioLab, chemLab, sciDivision, secretaryDesk, kanatOffice, pitiOffice, twlOffice);

    }

    /**
     * Link the magic room to where it transport to
     * @return the room that the transporterRoom will mimic
     */
    private MapRoom magicRoom(){
        int index = magic_counter % allMapRooms.size();
        MapRoom magicNeighbor = allMapRooms.get(index);
        return magicNeighbor;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(getLocationInfo());

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord){
            case UNKNOWN:
                System.out.println("I don't know what you mean...");

            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                if (currentMapRoom == connected) {
                    transporterMapRoom.setExits("special", magicRoom());
                }
                magic_counter += 7;
                break;
            case BACK:
                MapRoom temp = currentMapRoom;
                currentMapRoom = previousMapRoom;
                previousMapRoom = temp;
                System.out.println(getLocationInfo());
                break;
            case LOOK:
                System.out.println(lookAround());
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;

        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   help, go, look, quit, bye");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }


        String direction = command.getSecondWord();
        Direction goDirection = currentMapRoom.getDirection(direction);
        // Try to leave current room.
        MapRoom nextMapRoom = null;
        switch (goDirection){
            case NORTH:
                nextMapRoom = currentMapRoom.northExit;
                break;
            case EAST:
                nextMapRoom = currentMapRoom.eastExit;
                break;
            case SOUTH:
                nextMapRoom = currentMapRoom.southExit;
                break;
            case WEST:
                nextMapRoom = currentMapRoom.westExit;
                break;
            case DOWN:
                nextMapRoom = currentMapRoom.downExit;
                break;
            case UP:
                nextMapRoom = currentMapRoom.upExit;
                break;
            case SPECIAL:
                nextMapRoom = connected;
            case UNKNOWN:
                break;
        }

        if (nextMapRoom == null) {
            System.out.println("There is no door!, You shall not pass!");
        }
        else {
            previousMapRoom = currentMapRoom;
            currentMapRoom = nextMapRoom;
            System.out.println(getLocationInfo());
        }



    }

    /**
     * Look around the room for exits.
     * Return list of exits in the current room
     */
    private String lookAround(){
        StringBuilder info = new StringBuilder();
        info.append("Exits: ");
        if(currentMapRoom.northExit != null) {
            info.append("north ");
        }
        if(currentMapRoom.eastExit != null) {
            info.append("east ");
        }
        if(currentMapRoom.southExit != null) {
            info.append("south ");
        }
        if(currentMapRoom.westExit != null) {
            info.append("west ");
        }
        if(currentMapRoom.upExit != null) {
            info.append("up ");
        }
        if(currentMapRoom.downExit != null) {
            info.append("down ");
        }
        return info.toString();
    }
    /**
     * Return the current location description and details of the current exits
     */
    private String getLocationInfo(){
        StringBuilder info = new StringBuilder();
        info.append("You are " + currentMapRoom.getDescription());
        info.append('\n');
        info.append(lookAround());
        return info.toString();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
