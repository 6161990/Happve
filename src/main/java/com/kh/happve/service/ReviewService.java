package com.kh.happve.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.happve.dto.ReviewForm;
import com.kh.happve.entity.Member;
import com.kh.happve.entity.Review;
import com.kh.happve.repository.ImageRepository;
import com.kh.happve.repository.ReviewRepository;
import com.kh.happve.util.FileUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ImageRepository imageRepository;
    private final FileUtils fileUtils;

	public void saveReview(ReviewForm reviewForm, Member member, Integer crtfcUpsoMgtSno,MultipartFile[] files) throws Exception {
		Review review = Review.builder()
				              .content(reviewForm.getContent())
				              .rating(reviewForm.getRating()).member(member)
				              .crtfcUpsoMgtSno(crtfcUpsoMgtSno).build();

		reviewRepository.save(review);
		imageRepository.saveAll(fileUtils.uploadFiles(files, review.getReviewId(),review.getCrtfcUpsoMgtSno()));
	}

	public int replyCnt(Integer crtfcUpsoMgtSno) {
		return reviewRepository.findByReviewId(crtfcUpsoMgtSno);
	}
	
	public double reviewAvg(Integer crtfcUpsoMgtSno) {
		return reviewRepository.findByCrtfcUpsoMgtSno1(crtfcUpsoMgtSno);
	}

	public List<Integer> replycntByRatingandCrtfc(Integer crtfcUpsoMgtSno) {
		return reviewRepository.findByRatingAndCrtfcUpsoMgtSno(crtfcUpsoMgtSno);
	}

	public List<Review> findByRestaurantId(Integer crtfcUpsoMgtSno) {
		List<Review> reviews = reviewRepository.findByCrtfcUpsoMgtSno(crtfcUpsoMgtSno);
		return reviews;
	}

	public List<Review> findAllReviews(){
		List<Review> findAllReviews = reviewRepository.findReviewsOrderByRegDateDesc();
		return findAllReviews;
	}
	
	@Transactional
	public void reviewDelete(Long reviewId) {
		if(imageRepository.findByReviewId(reviewId)!=null) {
			imageRepository.deleteAllByReviewId(reviewId);
		}
		reviewRepository.deleteById(reviewId);
	}

}
