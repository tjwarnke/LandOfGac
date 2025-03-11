package edu.gac.mcs178.gack.domain;

import java.util.ArrayList;
import java.util.List;
import edu.gac.mcs178.gack.Utility;
import java.util.Random;

public class PokemonTrainer extends AutoPerson {

    private Place pokeball;
    private float catchRate;
    private List<CaughtPerson> caughtPeople;

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
            if (cp.turnsCaught >= 1) {
                throw_em(cp.person);
                toRelease.add(cp);
            }
        }
        caughtPeople.removeAll(toRelease);

        // Check for people to catch
        List<Person> others = otherPeopleAtSamePlace();
        if (!others.isEmpty()) {
            Person victim = others.get(Utility.randInt(others.size()));
            catchTarget(victim);
            return;
        }

        // Check for wild Pokémon
        List<Pokemon> wildPokemon = getPlace().getWildPokemon();
        if (!wildPokemon.isEmpty()) {
            Pokemon target = wildPokemon.get(Utility.randInt(wildPokemon.size()));
            catchTarget(target);
            return;
        }

        // Default action
        super.act();
    }

    public void catchTarget(Person target) {
        say("I'm gonna catch you, " + target.getName() + "!");
        target.say("No! I've gotta get out of here!");

        boolean caught = Utility.randInt(100) < this.catchRate;

        if (caught) {
            catch_em(target);
            say("1... 2... Gotcha! I caught a wild " + target.getName() + "!");
        } else {
            target.say("Yes, I'm free!");
            say("Ah, they got away!");
        }
    }

    public void catch_em(Person target) {
        target.say("Ah, I've been caught!");
        target.moveTo(pokeball);
        caughtPeople.add(new CaughtPerson(target));
    }

    public void throw_em(Person target) {
        say("Now... " + target.getName() + ", I choose you!");

        List<Place> allPlaces = Place.getAllPlaces();
        if (!allPlaces.isEmpty()) {
            Place randomPlace = allPlaces.get(Utility.randInt(allPlaces.size()));
            target.moveTo(randomPlace);
            target.say("I'm free! But where am I?");
        }
    }
}