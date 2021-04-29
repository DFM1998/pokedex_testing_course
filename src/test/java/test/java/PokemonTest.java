package test.java;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runners.Parameterized;

import main.java.Move;
import main.java.Pokemon;

public class PokemonTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  // checkout if a pokemon get right instantiates correctly
  // Unit Test
  @Test
  public void Pokemon_instantiatesCorrectly_true() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals(true, myPokemon instanceof Pokemon);
  }

  // after instantiates a pokemon check if it is possible to get his name
  // Unit Test
  @Test
  public void getName_pokemonInstantiatesWithName_String() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals("Squirtle", myPokemon.getName());
  }

  // checkout if the pokemon class is empty
  // Unit Test
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Pokemon.all().size(), 0);
  }

  // checkout if inserting twice the same pokemon if they are equal
  // Unit Test
  @Test
  public void equals_returnsTrueIfPokemonAreTheSame_true() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    Pokemon secondPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertTrue(firstPokemon.equals(secondPokemon));
  }

  // checkout if the pokemon has been saved correctly
  // Integration Test
  @Test
  public void save_savesPokemonCorrectly_1() {
    Pokemon newPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    newPokemon.save();
    assertEquals(1, Pokemon.all().size());
  }

  // check at the database if you can find the pokemon
  // Integration Test
  @Test
  public void find_findsPokemonInDatabase_true() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    Pokemon savedPokemon = Pokemon.find(myPokemon.getId());
    assertTrue(myPokemon.equals(savedPokemon));
  }

  // Add a move to a pokemon and save it at the database
  // Integration Test
  @Test
  public void addMove_addMoveToPokemon() {
    Move myMove = new Move("Punch", "Normal", 50.0, 100);
    myMove.save();
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    myPokemon.addMove(myMove);
    Move savedMove = myPokemon.getMoves().get(0);
    assertTrue(myMove.equals(savedMove));
  }

  // Delete all the pokemon with the corresponding moves
  // Integration Test
  @Test
  public void delete_deleteAllPokemonAndMovesAssociations() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    Move myMove = new Move("Bubble", "Water", 50.0, 100);
    myMove.save();
    myPokemon.addMove(myMove);
    myPokemon.delete();
    assertEquals(0, Pokemon.all().size());
    assertEquals(0, myPokemon.getMoves().size());
  }

  // Search for a pokemon at the database
  // Integration Test
  @Test
  public void searchByName_findAllPokemonWithSearchInputString_List() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    assertEquals(myPokemon, Pokemon.searchByName("squir").get(0));
  }

  // Testing a normal fight
  // Integration Test
  @ParameterizedTest()
  @CsvSource(value = {
          "Squirtle:Water:Normal:A cute turtle:50.0:12:16:false:Bubble:Water:50.0:100:300"
  })
  public void fighting_damagesDefender(String pokemonName, String type1, String type2, String description, double weight, int height, int evolves, boolean mega_evolves, String moveName, String type, double power, int accuracy, int result) {
    Pokemon myPokemon = new Pokemon( pokemonName,  type1,  type2,  description,  weight,  height,  evolves,  mega_evolves);
    myPokemon.save();
    myPokemon.hp = 500;
    Move myMove = new Move(moveName, type, power, accuracy);
    myMove.attack(myPokemon);
    System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
        System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
        System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
    assertEquals(result, myPokemon.hp);
  }


}
