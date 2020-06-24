package services;

import exceptions.InvalidInputException;
import models.Genders;
import models.Natures;
import models.Pokemon;
import models.Trainer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PokemonFactory {

    public static Pokemon makePokemon(Trainer t)
    {
        Pokemon pokemon = new Pokemon();
        Scanner scan = new Scanner(System.in);
        String line;
        int input;
        try
        {
            System.out.print("Pokemon Name: ");
            pokemon.setName(scan.nextLine());
            System.out.print("Pokemon Nickname (-1 if none): ");
            if (!(line = scan.nextLine()).equals("-1")) {
                pokemon.setNickname(line);
            }
            System.out.print("Pokemon Level: ");
            input = scan.nextInt();
            if(input < 1 || input > 100)
            {
                throw new InvalidInputException();
            }
            pokemon.setLevel(input);
            scan.nextLine();
            System.out.println("List moves separated by commas");
            line = scan.nextLine();
            String [] moves = {"", "", "", ""};
            String [] temp = line.split(", |,");
            if(temp.length > 4)
            {
                throw new InvalidInputException();
            }
            int c = 0;
            for(String s : temp)
            {
                moves[c] = s;
                c++;
            }
            pokemon.setMoveset(moves);
            System.out.print("Pokemon Ability: ");
            pokemon.setAbility(scan.nextLine());
            System.out.print("Hold Item (-1 if none): ");
            if (!(line = scan.nextLine()).equals("-1")) {
                pokemon.setItem(line);
            }
            System.out.println("List EVs separated by commas (HP,ATK,DEF,SPA,SPD,SPE)");
            line = scan.nextLine();
            temp = line.split(", |,");
            if(temp.length != 6)
            {
                throw new InvalidInputException();
            }
            int [] evs = new int[6];
            for(int i = 0; i < 6; i++)
            {
                evs[i] = Integer.parseInt(temp[i]);
            }
            pokemon.setEvs(evs);
            try
            {
                System.out.print("Pokemon Nature: ");
                pokemon.setNature(Natures.valueOf(scan.nextLine()));
                System.out.print("Pokemon Gender (Male, Female, Genderless): ");
                pokemon.setGender(Genders.valueOf(scan.nextLine()));
            }
            catch(IllegalArgumentException e)
            {
                //Do nothing
            }
            System.out.print("Is it shiny? (Y/N) ");
            line = scan.nextLine();
            if(line.equalsIgnoreCase("Y"))
            {
                pokemon.setShiny(true);
            }
            pokemon.setOT(t.getID());
            System.out.println("Pokemon successfully created!");
            return pokemon;
        }
        catch(InvalidInputException e)
        {
            System.out.println(e.getMessage());
        }
        catch(InputMismatchException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
