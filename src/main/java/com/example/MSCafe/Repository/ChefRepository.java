package com.example.MSCafe.Repository;

import com.example.MSCafe.Model.Chef;
import com.example.MSCafe.enums.Cuisine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository  extends JpaRepository<Chef, Long> {

    //Experimental insertion (highly nor recommended)
    @Modifying
    @Transactional
    @Query(value =  "INSERT INTO chef (name, experience, cuisine) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertData(String name, Double experience, String cuisine);

    default void insertData(String name, Double experience, Cuisine cuisine) {
        insertData(name, experience, cuisine.name());
    }
}
