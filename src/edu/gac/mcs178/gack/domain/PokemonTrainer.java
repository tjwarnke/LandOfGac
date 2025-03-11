package edu.gac.mcs178.gack.domain;

import java.util.ArrayList;
import java.util.List;
import edu.gac.mcs178.gack.Utility;
import java.util.Random;

public class PokemonTrainer extends AutoPerson {

    private Place pokeball;
    private float catchRate; // Percentage chance (0-100) of catching a person
    private List<CaughtPerson> caughtPeople; // Track people who have been caught

    private static class CaughtPerson {
        Person person;
        int turnsCaught;

        CaughtPerson(Person person) {
            this.person = person;
            this.turnsCaught = 0;
        }
    }

    public PokemonTrainer(String name, Place place, int threshold, Place pokeball, float catchRate) {
        super(name, place, threshold);
        this.pokeball = pokeball;
        this.catchRate = catchRate;
        this.caughtPeople = new ArrayList<>();
    }

    @Override
    public void act() {
        // Check if any caught person should be released
        List<CaughtPerson> toRelease = new ArrayList<>();
        for (CaughtPerson cp : caughtPeople) {
            cp.turnsCaught++;
            if (cp.turnsCaught >= 1) { // Release after one turn
                throw_em(cp.person);
                toRelease.add(cp);
            }
        }
        caughtPeople.removeAll(toRelease);

        // Attempt to catch someone if no one is being released
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

        // Determine if the catch is successful
        boolean caught = Utility.randInt(100) < this.catchRate;

        if (caught) {
            catch_em(person);
            say("1... 2... Gotcha! I caught a wild " + person + "!");
        } else {
            person.say("Yes, I'm free!");
            say("Ah, he got away!");
        }
    }

    public void catch_em(Person person) {
        person.say("Ah, I've been caught!");
        person.moveTo(pokeball);
        caughtPeople.add(new CaughtPerson(person)); // Track caught person
    }

    public void throw_em(Person person) {
        say("Now... Pokemon, I choose you!");

        // Move to a random place
        List<Place> allPlaces = Place.getAllPlaces(); // Assuming there's a way to retrieve all places
        if (!allPlaces.isEmpty()) {
            Place randomPlace = allPlaces.get(Utility.randInt(allPlaces.size()));
            person.moveTo(randomPlace);
            person.say("I'm free! But where am I?");
        }
    }
}