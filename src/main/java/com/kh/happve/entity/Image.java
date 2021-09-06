package com.kh.happve.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file")
public class Image {
	//test
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long fileId; // 파일 번호

	@Column(name="review_id")
	private Long reviewId; //리뷰 번호

	@Column(name = "crtfc_upso_mgt_sno")
	private Integer crtfcUpsoMgtSno; //식당 고유 번호

	@Column(name="original_name",nullable = false)
	private String originalName; // 원본 파일명

	@Column(name="save_name",nullable = false)
	private String saveName; // 저장 파일명

	@Column(nullable = false)
	private String filePath; //파일 경로

	@Column(name="file_size")
	private Long fileSize; // 파일 크기

}
