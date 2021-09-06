package com.kh.happve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kh.happve.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	
	//식당 고유 번호별 이미지 리스트
	List<Image> findByCrtfcUpsoMgtSno(Integer reviewIdcrtfcUpsoMgtSno);
	
    void deleteAllByReviewId(Long reviewId);
	
	List<Image> findByReviewId(Long reviewId);

}
