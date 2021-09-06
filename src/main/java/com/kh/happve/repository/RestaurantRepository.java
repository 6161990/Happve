package com.kh.happve.repository;



import com.kh.happve.entity.RestaurantDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantDbEntity,Long> {


    //식당 이름으로 검색
    @Query(value="SELECT * from restaurant_db_entity where restaurant_db_entity.title like %?1%",nativeQuery = true)
    List<RestaurantDbEntity> getListByTitle(String keyword);


    //메뉴로 검색
    @Query(value="SELECT * from restaurant_db_entity where restaurant_db_entity.menu like %?1%",nativeQuery = true)
    List<RestaurantDbEntity> getListByMenu(String keyword);

    //상단검색 ( 식당+이름 검색)
    @Query(value="SElECT * from restaurant_db_entity " +
            "where restaurant_db_entity.menu like %:keyword% or" +
            " restaurant_db_entity.title like %:keyword% ",nativeQuery = true)
    List<RestaurantDbEntity> getListByAllSearch(@Param("keyword") String keyword);

}
