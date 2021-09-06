package com.kh.happve.repository;

import com.kh.happve.entity.Review;
import com.kh.happve.entity.Suggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly=true)
public interface SuggestionRepository extends JpaRepository<Suggest, Long>{

}