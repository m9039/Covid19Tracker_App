package au.edu.unsw.infs3634.covid19tracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM Country")
    List<Country> getCountries(String countryCode);

    @Insert
    void insert(Country... countries);

    @Query("DELETE FROM Country")
    void deleteAll();

}
