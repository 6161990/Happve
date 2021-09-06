package com.kh.happve.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
/*@JsonNaming(value = (PropertyNamingStrategies.SNAKE_CASE))*/
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestaurantApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonProperty("CRTFC_UPSO_MGT_SNO")
    private Integer crtfc_upso_mgt_sno;  //식당 고유 id ( 식품 인증 업소 관리 일련 번호)  10881

    @JsonProperty("UPSO_SNO")
    private String upso_sno; //업소 번호

    @JsonProperty("UPSO_NM")
    @NotBlank
    private String upso_nm;  //업소명  본죽 (한성대입구)

    @JsonProperty("CGG_CODE")
    @NotBlank
    private String cgg_code;  //자치구 코드  3070000

    @JsonProperty("CGG_CODE_NM")
    @NotBlank
    private String cgg_code_nm;  //자치구 명  성북구

    @JsonProperty("COB_CODE_NM")
    private String cob_code_nm; //업종명  일반음식점

    @JsonProperty("BIZCND_CODE_NM")
    @Column(nullable=true)
    private String bizcnd_code_nm; // 업태명 (한식, 경양식, 카페 등)  한식

    @JsonProperty("OWNER_NM")
    private String owner_nm;  //업주명

    @JsonProperty("CRTFC_GBN")
    private String crtfc_gbn; // 식품인증구분  14

    @JsonProperty("CRTFC_GBN_NM")
    private String crtfc_gbn_nm; //식품인증구분명  채식음식점

    @JsonProperty("CRTFC_CHR_NM")
    private String crtfc_chr_nm; //담당자 이름  시스템관리자

    @JsonProperty("CRTFC_CHR_ID")
    private String crtfc_chr_id; //담당자 id  admin

    @JsonProperty("CRTFC_YMD")
    private String crtfc_ymd; //인증일자  2020-12-11

    @JsonProperty("USE_YN")
    private String use_yn; //사용여부  Y

    @JsonProperty("MAP_INDICT_YN")
    private String map_indict_yn; //지도 표시 여부  Y

    @JsonProperty("CRTFC_CLASS")
    private String crtfc_class; //식품인증업소 등급  37.5878566

    @JsonProperty("Y_DNTS")
    @NotBlank
    private String y_dnts; //지도 y좌표  37.5878566

    @JsonProperty("X_CNTS")
    @NotBlank
    private String x_cnts; // 지도 x좌표 127.0067781

    @JsonProperty("TEL_NO")
    @NotBlank
    private String tel_no; //전화번호  02-741-6233

    @JsonProperty("RDN_DETAIL_ADDR")
    private String rdn_detail_addr; //도로명 상세 주소

    @JsonProperty("RDN_ADDR_CODE")
    private String rdn_addr_code; //도로명 주소 코드

    @JsonProperty("RDN_CODE_NM")
    @NotBlank
    private String rdn_code_nm;  //도로명주소  서울 성북구 삼선교로 8

    @JsonProperty("BIZCND_CODE")
    @Nullable
    private String bizcnd_code; // 업태코드  10101

    @JsonProperty("COB_CODE")
    private String cob_code; // 업종코드  101

    @JsonProperty("CRTFC_SNO")
    private String crtfc_sno; //허가번호

    @JsonProperty("CRT_TIME")
    private String crt_time; // 최초 작성일자

    @JsonProperty("CRT_USR")
    private String crt_usr; // 최초 작성자  admin

    @JsonProperty("UPD_TIME")
    private String upd_time; //최초 수정일자

    @JsonProperty("FOOD_MENU")
    @NotBlank
    private String food_menu; //업소 인증 메뉴  특전복내장죽(페스코), 특전복죽(페스코), 진전복죽(페스코) ...

    @JsonProperty("GNT_NO")
    private String gnt_no; //교부번호

    @JsonProperty("CRTFC_YN")
    private String crtfc_yn; //인증여부
}