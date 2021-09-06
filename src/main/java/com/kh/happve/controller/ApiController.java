package com.kh.happve.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.happve.entity.Image;
import com.kh.happve.entity.Member;
import com.kh.happve.entity.RestaurantApi;
import com.kh.happve.entity.Review;
import com.kh.happve.service.ImageService;
import com.kh.happve.service.ReviewService;
import com.kh.happve.validator.CurrentAccount;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
	
	private final ReviewService reviewService;
	private final ImageService imageService;

	
	@GetMapping("/{crtfc_upso_mgt_sno}")
	public String basic(@PathVariable("crtfc_upso_mgt_sno") Integer crtfc_upso_mgt_sno,@CurrentAccount Member member,
						Model model) {
		
		if(member != null) {
			model.addAttribute(member);
		}

		StringBuffer result = new StringBuffer();
		RestaurantApi ra = new RestaurantApi();
		JSONArray rowrow = null;

		try {
			StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL 가져오려는 공공데이터 엔드포인트 주소 */
			urlBuilder.append("/" + "6f4a424463716c773834794d516d44"); /*Service Key 공공데이터포털에서 받은 인증키*/
			urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*호출문서 형태*/
			urlBuilder.append("/" + URLEncoder.encode("CrtfcUpsoInfo", "UTF-8")); /*서비스명*/
			urlBuilder.append("/" + 1); //*한 페이지 결과 수*/
			urlBuilder.append("/" + 5 ); /*페이지번호*/
			urlBuilder.append("/" + crtfc_upso_mgt_sno); /*페이지번호*/
			urlBuilder.append("/" );

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line + "\n");
			}
			rd.close();
			conn.disconnect();

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());
			JSONObject CrtfcUpsoInfo = (JSONObject) jsonObject.get("CrtfcUpsoInfo");
			rowrow = (JSONArray) CrtfcUpsoInfo.get("row");

			JSONObject arrayToJson = null;
			ObjectMapper mapper = new ObjectMapper();

			/* Restaurant getOne */

			for(int i =0; i< rowrow.size(); i++) {
				arrayToJson = (JSONObject) rowrow.get(i);

				ra = mapper.readValue(arrayToJson.toString(), RestaurantApi.class);

				log.info("arrayToJson ={}", arrayToJson);
			}

			//전체 리뷰 수
			model.addAttribute("reviewcnt",reviewService.replyCnt(crtfc_upso_mgt_sno));
			
			//식당별 별점 평균값
			model.addAttribute("reviewavg",reviewService.reviewAvg(crtfc_upso_mgt_sno));

			List<Integer> rating = new ArrayList<>();
			List<Integer> integers = reviewService.replycntByRatingandCrtfc(crtfc_upso_mgt_sno);
			for(int i=0; i<integers.size(); i++){
				Stream<Integer> ratingStream = integers.stream();
				ratingStream.filter(s-> s != null).forEach(s-> rating.add(s));

				//식당에 대한 각 별점 평가 수
				model.addAttribute(String.valueOf(i), rating.get(i));
			}



		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Review> reviewlist = reviewService.findByRestaurantId(crtfc_upso_mgt_sno);
		List<Image> imagelist = imageService.findRestaurantId(crtfc_upso_mgt_sno);

		List<String> imageToView = new ArrayList<>();

		if(imagelist != null){
			Stream<Image> imageStream = imagelist.stream();
			imageStream.limit(5).map(c-> c.getSaveName()).forEach(s-> imageToView.add(s));

			// 대문 이미지 리스트
			model.addAttribute("imageToView", imageToView);
			log.info("imageToView ={}", imageToView);
		}


		//식당 정보 getOne
		model.addAttribute("ra",ra);

		//리뷰 리스트
		model.addAttribute("reviewlist",reviewlist);

		//리뷰 이미지 리스트
		model.addAttribute("imagelist",imagelist);


		return "detail";

	}

	/* Restaurant getList */
	@GetMapping("/restaurants")
	private List<RestaurantApi> restaurants(JSONArray jsonArray) {
		List<RestaurantApi> list = new ArrayList<>();
		RestaurantApi restaurant = null;

		JSONObject arrayToJson = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			for(int i =0; i< jsonArray.size(); i++){
				arrayToJson = (JSONObject) jsonArray.get(i);
				System.out.println("arrayToJson ===> " + arrayToJson);
				restaurant = mapper.readValue(arrayToJson.toString(), RestaurantApi.class);

				list.add(restaurant);
				System.out.println("===== getCrtfc_upso_mgt_sno() ===> " + restaurant.getCrtfc_upso_mgt_sno());
			}

			System.out.println("list data ===> " + list);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}
}