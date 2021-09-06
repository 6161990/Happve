package com.kh.happve.service;

import com.kh.happve.dto.CommuDTO;
import com.kh.happve.entity.Image;
import com.kh.happve.entity.Review;
import com.kh.happve.repository.ImageRepository;
import com.kh.happve.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommuService {

    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    public List<CommuDTO> findReviewForCommu(){
        List<CommuDTO> commuDTOList = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findReviewsOrderByRegDateDesc();

        for(int i=0; i<reviewList.size(); i++){
            Integer findRestID = reviewList.get(i).getCrtfcUpsoMgtSno(); //식당 id
            Long findReviewId = reviewList.get(i).getReviewId(); //리뷰 id
            List<String> findImage = new ArrayList<>();
            imageRepository.findByReviewId(findReviewId).stream().map(m->m.getSaveName()).forEach(s-> findImage.add(s)); //리뷰 id로 image 가져오기
            CommuDTO commuDTO = new CommuDTO();
            commuDTO.setReview(reviewList.get(i));
            commuDTO.setImage(findImage);
            commuDTOList.add(commuDTO);
            System.out.println(commuDTO.getReview().getContent());
            System.out.println(commuDTO.getImage());
        }
        return commuDTOList;
    }

}
