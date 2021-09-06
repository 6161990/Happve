package com.kh.happve.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.kh.happve.entity.Image;
import com.kh.happve.exception.ImageFileException;

@Component
public class FileUtils {

	// 업로드 경로 ex) C:\\upload
	private final String uploadPath = Paths.get("C:","upload").toString();

	// 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	// 서버에 첨부 파일을 생성하고, 업로드 파일 목록 반환
	public List<Image> uploadFiles(MultipartFile[] files, Long reviewId,Integer crtfcUpsoMgtSno) {

		// 업로드 파일 정보를 담을 비어있는 리스트
		List<Image> imageList = new ArrayList<>();

		// uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉토리를 포함한 모든 디렉토리를 생성
		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		// 파일 개수만큼 forEach 실행
		for (MultipartFile file : files) {
			if(file.getSize()<1) {
				continue;
			}
			try {
				// 파일 확장자
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				// 서버에 저장할 파일명(랜덤 문자열+확장자)
				final String saveName = getRandomString() + "." + extension;

				// 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성
				File target = new File(uploadPath, saveName);
				file.transferTo(target);

				// 파일 정보 저장
				Image image = new Image();
				image.setReviewId(reviewId);
				image.setCrtfcUpsoMgtSno(crtfcUpsoMgtSno);
				image.setOriginalName(file.getOriginalFilename());
				image.setSaveName(saveName);
				image.setFilePath(uploadPath);
				image.setFileSize(file.getSize());
				
				// 파일 정보 추가
				imageList.add(image);
			} catch (IOException e) {
				throw new ImageFileException("[" + file.getOriginalFilename() + "] 이미지 저장 실패..");
			} catch (Exception e) {
				throw new ImageFileException("[" + file.getOriginalFilename() + "] 이미지 저장 실패..");
			}
		}
		System.out.println(imageList);
		return imageList;
	}
}