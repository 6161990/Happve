package com.kh.happve.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kh.happve.entity.Review;


@Transactional(readOnly=true)
public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query(value = "select r from Review r where r.crtfcUpsoMgtSno=:crtfcUpsoMgtSno order by regdate desc")
	List<Review> findByCrtfcUpsoMgtSno(Integer crtfcUpsoMgtSno);
	
	//상세페이지 별 리뷰 수
	@Query(value="select count(r) from Review r where r.crtfcUpsoMgtSno=:crtfcUpsoMgtSno")
	Integer findByReviewId(@Param("crtfcUpsoMgtSno") Integer crtfcUpsoMgtSno);
	
	@Query(value="select round(avg(r.rating),1) from Review r where r.crtfcUpsoMgtSno=:crtfcUpsoMgtSno")
	Double findByCrtfcUpsoMgtSno1(@Param("crtfcUpsoMgtSno") Integer crtfcUpsoMgtSno);
	
	@Query(value="select count(r) from Review r where r.crtfcUpsoMgtSno=:crtfcUpsoMgtSno group by r.rating order by rating asc")
	List<Integer> findByRatingAndCrtfcUpsoMgtSno(@Param("crtfcUpsoMgtSno") Integer crtfcUpsoMgtSno);

	@Query(value = "select r from Review r order by regdate desc")
	List<Review> findReviewsOrderByRegDateDesc();
	
	//리뷰 삭제
	public void deleteById(Long reviewId);

}