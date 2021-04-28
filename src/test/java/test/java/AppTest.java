package test.java;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  public DatabaseRule database;
  @Before
  public void setUpDatabase() {
    database = new DatabaseRule();
  }

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  public static ServerRule server;
  @Before
  public void setUpServer() {
    server = new ServerRule();
  }



  // checkout if the webpage code contains something with the string "Pokedex", that could be used to check if the webserver is working
  // Unit test
  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Pokedex");
  }

  // check if the whole pokemon page is displayed
  // Unit test
  @Test
  public void allPokemonPageIsDisplayed() {
    goTo("http://localhost:4567/");
    click("#viewDex");
    assertThat(pageSource().contains("Ivysaur"));
    assertThat(pageSource().contains("Charizard"));
  }

  // checkout if the individual pokemon page is displayed
  // Unit test
  @Test
  public void individualPokemonPageIsDisplayed() {
    goTo("http://localhost:4567/pokepage/6");
    assertThat(pageSource().contains("Charizard"));
  }

  // checkout if arrow click is working as it should, if clicking at the button and we are at he pokemon 6 if goes to the right next pokemon
  // Unit Test
  @Test
  public void arrowsCycleThroughPokedexCorrectly() {
    goTo("http://localhost:4567/pokepage/6");
    click(".glyphicon-triangle-right");
    assertThat(pageSource().contains("Squirtle"));
  }

  // checkout if searching for a pokemon if it is possible to find one
  // Unit Test
  @Test
  public void searchResultsReturnMatches() {
    goTo("http://localhost:4567/pokedex");
    fill("#name").with("char");
    assertThat(pageSource().contains("Charizard"));
  }

  //checkout if nothing found if displaying the right error
  // Unit Test
  @Test
  public void searchResultsReturnNoMatches() {
    goTo("http://localhost:4567/pokedex");
    fill("#name").with("x");
    assertThat(pageSource().contains("No matches for your search results"));
  }

}
