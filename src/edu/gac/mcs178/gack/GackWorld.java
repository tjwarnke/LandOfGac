package edu.gac.mcs178.gack;

import edu.gac.mcs178.gack.domain.*;

import java.util.Arrays;
import java.util.List;

public class GackWorld extends World {
	public GackWorld() {
		super();
		Place foodService = new Place("Food Service");
		Place po = new Place("Post Office");
		Place alumniHall = new Place("Alumni Hall");
		Place chamberOfWizards = new Place("Chamber of Wizards");
		Place library = new Place("Library");
		Place goodShipOlin = new Place("Good Ship Olin");
		Place lounge = new Place("Lounge");
		Place computerLab = new Place("Computer Lab");
		Place offices = new Place("Offices");
		Place dormitory = new Place("Dormitory");
		Place pond = new Place("Pond");
		Place lund = new Place("Lund");

		// Create Pokeball location
		Place pokeball = new Place("Pokeball");

		lund.addNewNeighbor("southeast", dormitory);
		dormitory.addNewNeighbor("northwest", lund);
		foodService.addNewNeighbor("down", po);
		po.addNewNeighbor("south", alumniHall);
		alumniHall.addNewNeighbor("north", foodService);
		alumniHall.addNewNeighbor("east", chamberOfWizards);
		alumniHall.addNewNeighbor("west", library);
		chamberOfWizards.addNewNeighbor("west", alumniHall);
		chamberOfWizards.addNewNeighbor("south", dormitory);
		dormitory.addNewNeighbor("north", chamberOfWizards);
		dormitory.addNewNeighbor("west", goodShipOlin);
		library.addNewNeighbor("east", library);
		library.addNewNeighbor("south", goodShipOlin);
		goodShipOlin.addNewNeighbor("north", library);
		goodShipOlin.addNewNeighbor("east", dormitory);
		goodShipOlin.addNewNeighbor("up", lounge);
		lounge.addNewNeighbor("west", computerLab);
		lounge.addNewNeighbor("south", offices);
		computerLab.addNewNeighbor("east", lounge);
		offices.addNewNeighbor("north", lounge);

		// Create characters
		new AutoPerson("Max", dormitory, 2);
		new AutoPerson("Karl", computerLab, 4);
		new Witch("Barbara", offices, 3, pond);
		new Wizard("Elvee", offices, 1, chamberOfWizards);
		new Witch("Jacob", lund, 2, chamberOfWizards);

		// Create a Pokémon Trainer
		new PokemonTrainer("Robby", dormitory, 2, pokeball, 100);

		lounge.gain(new Thing("Karl's glasses"));
		lund.gain(new Thing("Weights"));
		lund.gain(new Thing("jump-rope"));

		library.gain(new Scroll("Scroll of Enlightenment"));
		String[] someTitles = {"War and Peace", "Iliad", "Collected Works of Rilke"};
		for (String title : someTitles) {
			library.gain(new Scroll(title));
		}
		computerLab.gain(new Scroll("Unix Programmers Manual"));
		computerLab.gain(new Scroll("NeXT User's Reference"));
		dormitory.gain(new Scroll("Late Lab Report"));

		setPlayer(new Person("player", dormitory));

		// Create and spawn wild Pokémon
		List<Place> allPlaces = Arrays.asList(foodService, po, alumniHall, chamberOfWizards, library,
				goodShipOlin, lounge, computerLab, offices, dormitory, pond, lund);

		List<Pokemon> wildPokemon = Arrays.asList(
				new Pokemon("Pikachu", "Electric", 5, foodService),
				new Pokemon("Charmander", "Fire", 5, lund),
				new Pokemon("Squirtle", "Water", 5, pond),
				new Pokemon("Bulbasaur", "Grass", 5, library)
		);

		Pokemon.spawnWildPokemon(allPlaces, wildPokemon);
	}
}
