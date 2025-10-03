package com.example.MSCafe.Repository;

import com.example.MSCafe.model.Dish;
import com.example.MSCafe.enums.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    //Custom Finder method
    List<Dish> findByNameContaining(String name);

    List<Dish> findByNameContainingAndPriceBetween(String name, Double min, Double max);

    //Custom JPQL(Java Persistence Query Language)
    @Query("SELECT d FROM Dish d WHERE d.id = :identity")
    Dish getDishById(@Param("identity") Long id);

    //Native SQL Query
    @Query(value = "SELECT d.* FROM dish d WHERE d.category = :category", nativeQuery = true)
    List<Dish> getAllDishByCategory(@Param("category") String category);

    @Query(value = "SELECT d.* FROM dish d WHERE d.price BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Dish> getAllDishByPriceRange(Double min, Double max);

    default List<Dish> getAllDishByCategory(DishCategory dishCategory) {
      return getAllDishByCategory(dishCategory.name());
    }
}
