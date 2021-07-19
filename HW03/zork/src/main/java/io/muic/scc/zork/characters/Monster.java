package io.muic.scc.zork.characters;

public class Monster extends Character {
    @Override
    public String getInfo(){
        StringBuilder info = new StringBuilder();
        info.append(String.format("This monster has current health of %s / %s", getCurrentHealth(), getHealth()));
        info.append('\n');
        info.append(String.format("Its attack power is %s", getAttackPower()));
        return info.toString();
    }
}
