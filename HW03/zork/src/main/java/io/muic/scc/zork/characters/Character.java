package io.muic.scc.zork.characters;

public abstract class Character {
    private int health = 100;
    private int attackPower = 50;
    private int currentHealth = 100;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void attack(Character character){
        character.setCurrentHealth(character.getCurrentHealth()-attackPower);
    }

    public abstract String getInfo();
}
