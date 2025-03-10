package edu.gac.mcs178.gack.domain;

import java.util.ArrayList;
import java.util.List;
import edu.gac.mcs178.gack.Utility;
import java.util.Random;

public class PokemonTrainer extends AutoPerson {

    private Place pokeball;
    private float catchRate; // a number between 0 and 100 which signifies the % likelihood that a
    // trainer successfully catches a different person

    public PokemonTrainer(String name, Place place, int threshold, Place pokeball, float catchRate) {
        super(name, place, threshold);
        this.pokeball = pokeball;
        this.catchRate = catchRate;
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
        say("I'm gonna catch you, Pokemon " + person + "!");
        person.say("No! I've gotta get out of here!");

        // add an element of randomness to the catch, so sometimes it works and sometimes
        // it doesn't

        // create variables needed for the randomization
        Random rand = new Random();
        boolean caught = false;

        // calculate if the catch happens or not
        int r = rand.nextInt(101);
        if (r < this.catchRate) caught = true;

        // if
        if (caught) {
            catch_em(person);
            say("1... 2... Gotcha! I caught a wild " + person + "!");
            throw_em(person);
        }

        else {
            person.say("Yes, I'm free!");
            say("Ah, he got away!");
        }
    }

    public void catch_em(Person person) {
        // need to copy person.getPossessions() in order to avoid a ConcurrentModificationException

        person.say("Ah, I've been caught!");
        person.moveTo(pokeball);
    }

    public void throw_em(Person person) {
        // TODO move the person to a new random place after moving them to the pokeball
        say("Now... Pokemon, I chose you!");
        // person.moveTo();
    }
}
