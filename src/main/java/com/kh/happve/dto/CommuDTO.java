package com.kh.happve.dto;

import com.kh.happve.entity.Image;
import com.kh.happve.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommuDTO {

    private Review review;

    private List<String> image;



}
