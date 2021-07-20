package io.muic.scc.zork;

import io.muic.scc.zork.characters.Monster;
import io.muic.scc.zork.characters.Player;
import io.muic.scc.zork.commands.Command;
import io.muic.scc.zork.commands.CommandWord;
import io.muic.scc.zork.commands.Parser;
import io.muic.scc.zork.items.Item;
import io.muic.scc.zork.items.ItemFactory;
import io.muic.scc.zork.items.ItemsList;
import io.muic.scc.zork.items.Weapon;
import io.muic.scc.zork.maps.Direction;
import io.muic.scc.zork.maps.MapDirector;
import io.muic.scc.zork.maps.MapRoom;

import java.util.List;
import java.util.Scanner;

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
    private Player player;
    private ItemFactory itemFactory;
    private MapDirector mapDirector;
    private boolean activeMap = false;

        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() {

        
        parser = new Parser();
    }


    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        String word1 = null;
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        if(!activeMap){
            Scanner reader = new Scanner(System.in);
            System.out.print("> ");     // print prompt
            String inputLine = reader.nextLine();
            Scanner tokenizer = new Scanner(inputLine);
            if (tokenizer.hasNext()) {
                word1 = tokenizer.next();
            }
            map(word1);
            
        }
            mapDirector = MapDirector.getInstance();
            itemFactory = ItemFactory.getInstance();
            player = new Player();
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
        System.out.println("Welcome to Zork!");
        System.out.println(" ");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("Please choose the map [muic] or [2]");

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {

        boolean wantToQuit = false;
        if(player.getCurrentHealth() <= 0){
            System.out.println("YOU DIED");
            return true;
        }

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord){
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
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
            case INFO:
                System.out.println(getInfo());
                break;
            case ATTACK:
                System.out.println(playerAttack(command));
                break;
            case TAKE:
                playerTakeItem(command);
                break;
            case DROP:
                playerDropItem(command);
                break;
        }


        return wantToQuit;
    }

    private void map(String word){
        if(activeMap){
            System.out.println("The map is already chosen.");
            return;
        }
        if(word == null) {
            System.out.println("Default map is chosen");
            currentMapRoom = mapDirector.createMap("muic");
        }else {
            currentMapRoom = mapDirector.createMap(word);

        }
        activeMap = true;
        System.out.println(getLocationInfo());

    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        if(activeMap) {
            System.out.println("You are lost. You are alone. You wander");
            System.out.println("around at the university.");
            System.out.println();
            System.out.println("Your command words are:");
            System.out.println("   help, go, back, look, info, take, drop, attack, quit, bye");
        }else {
            System.out.println("Please choose the map [muic] or [2]");
        }
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
            case UNKNOWN:
                break;
        }

        if (nextMapRoom == null) {
            System.out.println("There is no door!, You shall not pass!");
        }
        else {
            int health = player.getCurrentHealth() + 50;
            if(health > player.getHealth()){
                player.setCurrentHealth(player.getHealth());
            }else {
                player.setCurrentHealth(health);
            }
            previousMapRoom = currentMapRoom;
            currentMapRoom = nextMapRoom;
            System.out.println(getLocationInfo());
            System.out.println(String.format("Your current health is %s / %s.", player.getCurrentHealth(), player.getHealth()));
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

    private String getInfo(){
        StringBuilder info = new StringBuilder();
        List<Monster> monsters = currentMapRoom.monsters;
        List<String> items = currentMapRoom.getItemList();
        info.append(getLocationInfo());
        info.append('\n');
        info.append(String.format("Your current health is %s / %s.", player.getCurrentHealth(), player.getHealth()));
        info.append('\n');
        if(monsters == null){
            info.append("There is no monster.");
        }else {
            for (Monster monster : monsters) {
                info.append(monster.getInfo());
                info.append('\n');
            }
        }
        if (items == null){
            info.append("There is no item.");
        }else {
            info.append("There are ");
            for (String item : items) {
                info.append(item);
                info.append(" ");
            }
            info.append("in available in this room.");
            info.append('\n');
        }
        return info.toString();
    }

    private String playerAttack(Command command){
        StringBuilder info = new StringBuilder();
        List<Monster> monsters = currentMapRoom.monsters;
        if(monsters == null){
            info.append("There is no monster");
            return info.toString();
        }
        Monster monster = monsters.get(0);

        if(command.hasSecondWord()) {
            String weaponName = command.getSecondWord();
            if (player.getItems().contains(weaponName)) {
                ItemsList item = itemFactory.getItem(weaponName);
                Weapon weapon = (Weapon) itemFactory.createItem(item);
                player.setAttackPower(weapon.getAttackPower());
                info.append(String.format("You attack with %s",weaponName));
            }else {
                info.append(String.format("You dont have %s",weaponName));
                return info.toString();
            }
        }else{
            player.setAttackPower(10);
            info.append("You attack with your bare hands!");
        }
        info.append('\n');
        player.attack(monster);
        if(monster.getCurrentHealth() <= 0){
            monsters.remove(0);
            info.append("You killed a monster!");
        }else{
            monster.attack(player);
            info.append("The monster attacks you back!");
        }
        info.append('\n');
        info.append(String.format("Your current health is %s / %s.", player.getCurrentHealth(), player.getHealth()));
        return info.toString();
    }





    private void playerTakeItem(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return;
        }

        String itemName = command.getSecondWord();
        if(!currentMapRoom.getItemList().contains(itemName)){
            System.out.println(String.format("%s does not exist in this room.", itemName));
            return;
        }
        currentMapRoom.removeItem(itemName);
        player.take(itemName);
        System.out.println(String.format("You took %s.", itemName));

    }

    private void playerDropItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        if(!player.getItems().contains(itemName)){
            System.out.println(String.format("You do not have %s in your bag.", itemName));
            return;
        }
        currentMapRoom.addItem(itemName);
        player.drop(itemName);
        System.out.println(String.format("You dropped %s.", itemName));
    }
}


