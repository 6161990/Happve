package com.kh.happve.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.happve.entity.Image;
import com.kh.happve.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

	private final ImageRepository imageRepository;

	public List<Image> findRestaurantId(Integer crtfcUpsoMgtSno) {
		return imageRepository.findByCrtfcUpsoMgtSno(crtfcUpsoMgtSno);
	}

}
