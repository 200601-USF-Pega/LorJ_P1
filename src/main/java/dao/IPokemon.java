package dao;

import models.Pokemon;
import models.Trainer;

import java.util.List;

public interface IPokemon
{
    void addPokemon(Pokemon pokemon);
    void removePokemon(int id);
    Pokemon getPokemon(int id);
    Pokemon withdrawPokemon(int id);
    List<Pokemon> getTrainerPokemon(Trainer t);
    List<Pokemon> getAllPokemon();

}
