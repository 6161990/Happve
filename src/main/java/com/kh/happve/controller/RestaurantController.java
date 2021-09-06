package com.kh.happve.controller;




import com.kh.happve.entity.RestaurantDbEntity;
import com.kh.happve.repository.RestaurantRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class RestaurantController {

    @Autowired
    private RestaurantRepository repository;


    @GetMapping("/index")
    public String home(){

        return "index";

    }

    //인덱스 페이지에서 검색
    @GetMapping("/index/search")
    public String search(@RequestParam(value="type")String type,
                         @RequestParam(value="keyword")String keyword,
                         Model model){

        //식당으로 검색
        if(type.equals("title")){

            if(!keyword.isEmpty()){
                List<RestaurantDbEntity> resultByTitle= repository.getListByTitle(keyword);
                model.addAttribute("titleResult",resultByTitle);

                return "searchresult";

            }else{

                return "error";
            }


            //음식으로 검색
        }else if(type.equals("menu")){


            if(!keyword.isEmpty()){
                List<RestaurantDbEntity>  resultByMenu =repository.getListByMenu(keyword);
                model.addAttribute("resultByMenu",resultByMenu);

                return "searchresult2";

            }else{

                return "error";
            }
        }

        return "error";  //오류페이지
    }

    //상단 검색 (식당+메뉴)
    @GetMapping("/index/upSearch")
    public String searchUp(@RequestParam(value="keyword")String keyword, Model model) {

        //키워드가 있으면 검색
        if (!keyword.isEmpty()) {

            List<RestaurantDbEntity> resultByAllSerarch = repository.getListByAllSearch(keyword);

            model.addAttribute("resultByAllSearch", resultByAllSerarch);

            return "searchresult3";

            //키워드 없으면 오류페이지
        } else {

            return "error";
        }
    }
}