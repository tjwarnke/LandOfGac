package edu.gac.mcs178.gack.domain;

import java.util.ArrayList;
import java.util.List;

import edu.gac.mcs178.gack.Utility;

public class PokemonTrainer extends AutoPerson {

    private Place pokeball;

    public PokemonTrainer(String name, Place place, int threshold, Place pokeball) {
        super(name, place, threshold);
        this.pokeball = pokeball;
    }

    @Override
    public void act() {
        List<Person> others = otherPeopleAtSamePlace();
        if (!others.isEmpty()) {
            Person victim = others.get(Utility.randInt(others.size()));
            curse(victim);
        } else {
            super.act();
        }
    }

    public void curse(Person person) {
        say("I'm gonna catch you, Pokemon!");
        catch_em(person);
        say("1... 2... Gotcha! " + person + " has been caught!");
    }

    public void catch_em(Person person) {
        // need to copy person.getPossessions() in order to avoid a ConcurrentModificationException

        person.say("No! Let me out of here!");
        person.moveTo(pokeball);
    }
}
