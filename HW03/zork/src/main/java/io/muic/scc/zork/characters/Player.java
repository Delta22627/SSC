package io.muic.scc.zork.characters;

import io.muic.scc.zork.items.Item;

import java.util.ArrayList;

public class Player extends Character {
    private ArrayList<String> items = new ArrayList<>();

    @Override
    public String getInfo() {
        return null;
    }

    public void take(String item){
        items.add(item);
    }

    public void drop(String item){
        items.remove(item);
    }

    public ArrayList<String> getItems(){
        return items;
    }
}
