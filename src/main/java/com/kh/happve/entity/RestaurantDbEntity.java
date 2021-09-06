package com.kh.happve.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="RestaurantDbEntity")
public class RestaurantDbEntity {

        @Id
        @Column(name="restaurant_id")
        private Long restaurantId; //식당 고유 id


        
        @NotBlank
        private String title;  //식당 이름

        @Column(nullable=true)
        private String category; // 업태명 (ex 한식, 경양식, 카페 등)

        @Column(nullable=true)
        private String address; // 도로명 상세주소

        @NotBlank
        private String roadAddress; //도로명주소

        private String tel;  //업체 전화번호

        @Column(nullable=true)
        private String imageLink; //이미지 링크

        @Column(nullable=false)
        private String menu;




}
