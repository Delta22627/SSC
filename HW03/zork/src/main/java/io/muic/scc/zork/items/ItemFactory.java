package io.muic.scc.zork.items;

import java.util.HashMap;

public class ItemFactory {
    private static HashMap<String, ItemsList> validItems;
    private static final ItemFactory INSTANCE = new ItemFactory();
    private static final Weapon axe = new Weapon("axe");
    private static final Weapon sword = new Weapon("sword");
    private static final Weapon knife = new Weapon("knife");
    private static final Weapon spear = new Weapon("sword");

    private ItemFactory() {
        validItems = new HashMap<>();
        validItems.put("axe",ItemsList.AXE);
        validItems.put("sword",ItemsList.SWORD);
        validItems.put("knife",ItemsList.KNIFE);
        validItems.put("spear",ItemsList.SPEAR);

    }

    public static ItemFactory getInstance(){
        return INSTANCE;
    }

    public Item createItem(ItemsList itemsList){
        Weapon weapon;
        switch (itemsList){
            case AXE:
                weapon = axe;
                weapon.setAttackPower(50);
                break;
            case SWORD:
                weapon = sword;
                weapon.setAttackPower(100);
                break;
            case KNIFE:
                weapon = knife;
                weapon.setAttackPower(25);
                break;
            case SPEAR:
                weapon = spear;
                weapon.setAttackPower(75);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + itemsList);
        }
        return weapon;
    }

    public ItemsList getItem(String name){
        return validItems.get(name);
    }
}
