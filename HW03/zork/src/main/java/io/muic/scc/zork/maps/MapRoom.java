package io.muic.scc.zork.maps;

import io.muic.scc.zork.characters.Monster;
import io.muic.scc.zork.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west, up, down.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class MapRoom
{   // Room description
    public String description;
    // HashMap that holds all valid direction String as key and ENUM Direction as value
    private HashMap<String, Direction> validDirections;
    // all the rooms that this room has exit to.
    public MapRoom northExit = null;
    public MapRoom southExit = null;
    public MapRoom eastExit = null;
    public MapRoom westExit = null;
    public MapRoom downExit = null;
    public MapRoom upExit = null;
    public List<Monster> monsters;
    public List<Item> items = null;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * Constructor - initialise the directions.
     * Build HashMap String of directions and their ENUM equivalent
     * @param description The room's description.
     *
     */
    public MapRoom(String description)
    {
        validDirections = new HashMap<>();
        validDirections.put("north", Direction.NORTH);
        validDirections.put("front", Direction.NORTH);
        validDirections.put("east", Direction.EAST);
        validDirections.put("right", Direction.EAST);
        validDirections.put("south", Direction.SOUTH);
        validDirections.put("back", Direction.SOUTH);
        validDirections.put("west", Direction.WEST);
        validDirections.put("left", Direction.WEST);
        validDirections.put("down", Direction.DOWN);
        validDirections.put("up", Direction.UP);
        this.description = description;
    }

    /**
     * Define the exit of this room and the equivalent exit to the neighbor room.
     * This will reset the direction in the neighbor room.
     * For case SPECIAL, the room points to the neighbor reference.
     * @param direction take it the exit position of the room.
     * @param neighbor the room that the direction lead to.
     */
    public void setExits(String direction, MapRoom neighbor){
        Direction setDirection = this.getDirection(direction);
        switch (setDirection){
            case NORTH:
                this.northExit = neighbor;
                neighbor.southExit = this;
                break;
            case EAST:
                this.eastExit = neighbor;
                neighbor.westExit = this;
                break;
            case SOUTH:
                this.southExit = neighbor;
                neighbor.northExit = this;
                break;
            case WEST:
                this.westExit = neighbor;
                neighbor.eastExit = this;
                break;
            case DOWN:
                this.downExit = neighbor;
                neighbor.upExit = this;
                break;
            case UP:
                this.upExit = neighbor;
                neighbor.downExit = this;
                break;
            case UNKNOWN:
                break;
        }
    }

    public void setMonsters(int amount){
        if(amount > 0){
            monsters = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                Monster monster = new Monster();
                monsters.add(monster);
            }
            StringBuilder info = new StringBuilder();
            info.append(description);
            if(amount > 1) {
                info.append(String.format(" There are %s monsters in this room.", amount));
            }
            else {
                info.append(" There is 1 monster in this room.");
            }
            description = info.toString();
        }
        else{
            System.out.println("Invalid amount of monsters");
        }
    }

    public void addItem(Item item){
        items = new ArrayList<>();
        items.add(item);
        if(items != null){
            StringBuilder info = new StringBuilder();
            info.append(description);
            if(items.size() > 1){
                info.append(item.getName());
            }
            else{
                info.append(String.format(" There are %s avaliable", item.getName()));
            }
        }
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Change take in String return Direction
     * @param direction as key for the HashMap
     * @return Direction ENUM for switch-case use in setExits function
     */
    public Direction getDirection(String direction)
    {
        Direction rightDirection = validDirections.get(direction);
        if(rightDirection != null){
            return rightDirection;
        }
        return rightDirection.UNKNOWN;
    }
}
