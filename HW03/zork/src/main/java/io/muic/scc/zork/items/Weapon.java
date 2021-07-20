package io.muic.scc.zork.items;

public class Weapon extends Item{
    private int attackPower;
    public Weapon(String name) {
        super(name);
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }
}
