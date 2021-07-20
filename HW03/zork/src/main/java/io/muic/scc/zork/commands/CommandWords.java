package io.muic.scc.zork.commands;

import io.muic.scc.zork.commands.CommandWord;

import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
    // HashMap that holds all valid command words String as key and ENUM CommandWord as value
    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     * Build HashMap String of commands and their ENUM equivalent
     */
    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", CommandWord.GO);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("look", CommandWord.LOOK);
        validCommands.put("back", CommandWord.BACK);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("bye", CommandWord.QUIT);
        validCommands.put("info", CommandWord.INFO);
        validCommands.put("take", CommandWord.TAKE);
        validCommands.put("drop", CommandWord.DROP);
        validCommands.put("attack", CommandWord.ATTACK);
        validCommands.put("play", CommandWord.PLAY);
        validCommands.put("load", CommandWord.LOAD);
        validCommands.put("save", CommandWord.SAVE);
        validCommands.put("exit", CommandWord.EXIT);
        validCommands.put("autopilot", CommandWord.AUTO);

    }

    /**
     * Change take in String return CommandWord
     * @param commandWord String taken as the key for CommandWords HashMap
     * @return CommandWord
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null){
            return command;
        }
        return CommandWord.UNKNOWN;
    }
}
