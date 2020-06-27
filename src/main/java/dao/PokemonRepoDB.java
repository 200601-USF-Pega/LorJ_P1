package dao;

import models.Genders;
import models.Natures;
import models.Pokemon;
import models.Trainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonRepoDB implements IPokemon
{

    private Connection cm;

    public PokemonRepoDB(Connection cm)
    {
        this.cm = cm;
    }

    public void addPokemon(Pokemon pokemon)
    {
        try
        {
            // Insert into evs_table
            PreparedStatement evStatement = cm.prepareStatement("INSERT INTO evs_table (hp, atk, def, spa, spd, spe) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            int [] ev = pokemon.getEvs();
            evStatement.setInt(1, ev[0]);
            evStatement.setInt(2, ev[1]);
            evStatement.setInt(3, ev[2]);
            evStatement.setInt(4, ev[3]);
            evStatement.setInt(5, ev[4]);
            evStatement.setInt(6, ev[5]);
            evStatement.execute();

            // Get Serial ID from ev_table
            ResultSet rs = evStatement.getGeneratedKeys();
            int evid = -1;
            if(rs.next())
            {
                evid = rs.getInt(1);
            }

            // Insert into pokemon_table
            PreparedStatement pokeStatement = cm.prepareStatement("INSERT INTO pokemon_table (pokemon_name, nickname, lvl, ability, item, evid, nature, gender, shiny, ot) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            pokeStatement.setString(1, pokemon.getName());
            pokeStatement.setString(2, pokemon.getNickname());
            pokeStatement.setInt(3, pokemon.getLevel());
            pokeStatement.setString(4, pokemon.getAbility());
            pokeStatement.setString(5, pokemon.getItem());
            pokeStatement.setInt(6, evid);
            pokeStatement.setString(7, pokemon.getNature().toString());
            pokeStatement.setString(8, pokemon.getGender().toString());
            pokeStatement.setBoolean(9, pokemon.isShiny());
            pokeStatement.setInt(10, pokemon.getOT());
            pokeStatement.execute();
            rs = pokeStatement.getGeneratedKeys();
            int p_id = -1;
            if(rs.next())
            {
                p_id = rs.getInt(1);
            }

            // Insert into moves_table
            String [] moves = pokemon.getMoveset();
            PreparedStatement moveStatement = cm.prepareStatement("INSERT INTO moves_table (move_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            int [] move_ids = new int[4];
            for(int i = 0; i < 4; i++)
            {
                if(!moves[i].isEmpty())
                {
                    moveStatement.setString(1, moves[i]);
                    moveStatement.execute();
                    rs = moveStatement.getGeneratedKeys();
                    if(rs.next())
                    {
                        move_ids[i] = rs.getInt(1);
                    }
                }
            }

            // Insert into pokemon_moves table
            PreparedStatement pm = cm.prepareStatement("INSERT INTO pokemon_moves (p_id, move_id) VALUES (?, ?)");
            pm.setInt(1, p_id);
            for(int i = 0; i < 4; i++)
            {
                if(move_ids[i] != 0)
                {
                    pm.setInt(2, move_ids[i]);
                    pm.execute();
                }
            }

        }
        catch (Exception e)
        {
            //Do nothing
        }
    }

    public void removePokemon(int id)
    {
        try
        {
            Statement s = cm.createStatement();
            s.execute("SELECT * FROM pokemon_moves WHERE p_id=" + id);
            ResultSet rs = s.getResultSet();
            int [] m_id = new int[4];
            int i = 0;
            while(rs.next())
            {
                m_id[i] = rs.getInt("move_id");
                i++;
            }

            s.execute("DELETE FROM pokemon_moves WHERE p_id=" + id);
            s.execute("DELETE FROM pokemon_table WHERE p_id=" + id);
            for(i = 0; i < 4; i++)
            {
                if(m_id[i] != 0)
                {
                    s.execute("DELETE FROM moves_table WHERE move_id=" + m_id[i]);
                }
            }
        }
        catch(SQLException e)
        {
            e.getStackTrace();
        }
    }

    public Pokemon getPokemon(int id)
    {
        try
        {
            PreparedStatement ps = cm.prepareStatement
                    ("SELECT * FROM pokemon_table\n" +
                    "LEFT OUTER JOIN evs_table\n" +
                    "\tON pokemon_table.evid = evs_table.evid\n" +
                    "LEFT OUTER JOIN pokemon_moves\n" +
                    "\tON pokemon_table.p_id = pokemon_moves.p_id\n" +
                    "LEFT OUTER JOIN moves_table\n" +
                    "\tON pokemon_moves.move_id = moves_table.move_id\n" +
                    "WHERE pokemon_table.p_id = ?");
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if(rs.next())
            {
                Pokemon p = new Pokemon();
                p.setName(rs.getString("pokemon_name"));
                p.setNickname(rs.getString("nickname"));
                p.setLevel(rs.getInt("lvl"));
                p.setAbility(rs.getString("ability"));
                p.setItem(rs.getString("item"));
                p.setNature(Natures.valueOf(rs.getString("nature")));
                p.setGender(Genders.valueOf(rs.getString("gender")));
                p.setShiny(rs.getBoolean("shiny"));
                p.setOT(rs.getInt("ot"));
                p.setP_id(rs.getInt("p_id"));

                int [] evs = new int[6];
                evs[0] = rs.getInt("hp");
                evs[1] = rs.getInt("atk");
                evs[2] = rs.getInt("def");
                evs[3] = rs.getInt("spa");
                evs[4] = rs.getInt("spd");
                evs[5] = rs.getInt("spe");
                p.setEvs(evs);

                String [] moveset = new String[4];
                moveset[0] = rs.getString("move_name");
                int i = 1;
                while(rs.next())
                {
                    if(!rs.getString("move_name").isEmpty())
                    {
                        moveset[i] = rs.getString("move_name");
                    }
                    i++;
                }
                p.setMoveset(moveset);
                return p;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Pokemon withdrawPokemon(int id)
    {
        try
        {
            PreparedStatement ps = cm.prepareStatement
                    ("SELECT * FROM pokemon_table\n" +
                            "LEFT OUTER JOIN evs_table\n" +
                            "\tON pokemon_table.evid = evs_table.evid\n" +
                            "LEFT OUTER JOIN pokemon_moves\n" +
                            "\tON pokemon_table.p_id = pokemon_moves.p_id\n" +
                            "LEFT OUTER JOIN moves_table\n" +
                            "\tON pokemon_moves.move_id = moves_table.move_id\n" +
                            "WHERE pokemon_table.p_id = ?");
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if(rs.next())
            {
                int p_id = rs.getInt("p_id");
                Pokemon p = new Pokemon();
                p.setName(rs.getString("pokemon_name"));
                p.setNickname(rs.getString("nickname"));
                p.setLevel(rs.getInt("lvl"));
                p.setAbility(rs.getString("ability"));
                p.setItem(rs.getString("item"));
                p.setNature(Natures.valueOf(rs.getString("nature")));
                p.setGender(Genders.valueOf(rs.getString("gender")));
                p.setShiny(rs.getBoolean("shiny"));
                p.setOT(rs.getInt("ot"));
                p.setP_id(p_id);

                int [] evs = new int[6];
                evs[0] = rs.getInt("hp");
                evs[1] = rs.getInt("atk");
                evs[2] = rs.getInt("def");
                evs[3] = rs.getInt("spa");
                evs[4] = rs.getInt("spd");
                evs[5] = rs.getInt("spe");
                p.setEvs(evs);

                String [] moveset = new String[4];
                moveset[0] = rs.getString("move_name");
                int i = 1;
                while(rs.next())
                {
                    if(!rs.getString("move_name").isEmpty())
                    {
                        moveset[i] = rs.getString("move_name");
                    }
                    i++;
                }
                p.setMoveset(moveset);

                // Delete entry from db
                Statement s = cm.createStatement();
                s.execute("SELECT * FROM pokemon_moves WHERE p_id=" + p_id);
                rs = s.getResultSet();
                int [] m_id = new int[4];
                i = 0;
                while(rs.next())
                {
                    m_id[i] = rs.getInt("move_id");
                    i++;
                }
                s.execute("DELETE FROM pokemon_moves WHERE p_id=" + p_id);
                s.execute("DELETE FROM pokemon_table WHERE p_id=" + p_id);
                for(i = 0; i < 4; i++)
                {
                    if(m_id[i] != 0)
                    {
                        s.execute("DELETE FROM moves_table WHERE move_id=" + m_id[i]);
                    }
                }
                return p;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public List<Pokemon> getTrainerPokemon(int ot)
    {
        List<Pokemon> result = new ArrayList<Pokemon>();
        try
        {
            PreparedStatement ps = cm.prepareStatement("SELECT * FROM pokemon_table WHERE ot=?");
            ps.setInt(1, ot);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while(rs.next())
            {
                Pokemon p = new Pokemon();
                p.setName(rs.getString("pokemon_name"));
                p.setNickname(rs.getString("nickname"));
                p.setLevel(rs.getInt("lvl"));
                p.setAbility(rs.getString("ability"));
                p.setItem(rs.getString("item"));
                p.setNature(Natures.valueOf(rs.getString("nature")));
                p.setGender(Genders.valueOf(rs.getString("gender")));
                p.setShiny(rs.getBoolean("shiny"));
                p.setOT(rs.getInt("ot"));
                p.setP_id(rs.getInt("p_id"));
                if(!result.contains(p))
                {
                    result.add(p);
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public List<Pokemon> getAllPokemon()
    {
        List<Pokemon> result = new ArrayList<Pokemon>();
        try
        {
            Statement s = cm.createStatement();
            s.executeQuery("SELECT * FROM pokemon_table;");
            ResultSet rs = s.getResultSet();
            while(rs.next())
            {
                Pokemon p = new Pokemon();
                p.setName(rs.getString("pokemon_name"));
                p.setNickname(rs.getString("nickname"));
                p.setLevel(rs.getInt("lvl"));
                p.setAbility(rs.getString("ability"));
                p.setItem(rs.getString("item"));
                p.setNature(Natures.valueOf(rs.getString("nature")));
                p.setGender(Genders.valueOf(rs.getString("gender")));
                p.setShiny(rs.getBoolean("shiny"));
                p.setOT(rs.getInt("ot"));
                p.setP_id(rs.getInt("p_id"));
                if(!result.contains(p))
                {
                    result.add(p);
                }
            }
        }
        catch(SQLException e)
        {
            e.getStackTrace();
        }
        return result;
    }

}
