package edu.gac.mcs178.gack.domain;

import edu.gac.mcs178.gack.Utility;
import edu.gac.mcs178.gack.ui.MessageDisplay;

import java.util.List;

public class Pokemon extends Person {
    private String type;
    private int level;
    private int health;
    private static MessageDisplay messageDisplay;

    public Pokemon(String name, String type, int level, Place startingPlace) {
        super(name, startingPlace);
        this.type = type;
        this.level = level;
        this.health = 100;
    }

    public static void setMessageDisplay(MessageDisplay display) {
        messageDisplay = display;
    }

    public static void spawnWildPokemon(List<Place> places, List<Pokemon> pokemonList) {
        for (Pokemon p : pokemonList) {
            Place randomPlace = places.get(Utility.randInt(places.size()));
            randomPlace.addWildPokemon(p);
            if (messageDisplay != null) {
                messageDisplay.displayMessage(p.getName() + " appeared in " + randomPlace.getName() + "!");
            }
        }
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public void levelUp() {
        level++;
        health = 100;
        if (messageDisplay != null) {
            messageDisplay.displayMessage(name + " leveled up to level " + level + "!");
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            if (messageDisplay != null) {
                messageDisplay.displayMessage(name + " fainted!");
            }
        } else {
            if (messageDisplay != null) {
                messageDisplay.displayMessage(name + " took damage! Health is now " + health);
            }
        }
    }

    @Override
    public void say(String message) {
        if (messageDisplay != null) {
            messageDisplay.displayMessage(name + " (a " + type + " Pokémon): " + message);
        }
    }

}