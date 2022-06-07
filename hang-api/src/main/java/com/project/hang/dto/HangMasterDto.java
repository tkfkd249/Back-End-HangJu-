package com.project.hang.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HangMasterDto {

    private String panId; //공고아이디
    private String uppAisTpNm; //공고유형
    private String aisTpCdNm; //공고세부유형명
    private String panNm; //공고명
    private String cnpCdNm; //지역명
    private String panNtStDt; //공고게시일
    private String clsgDt; //공고마감일
    private String panSs; //공고상태
    private String dtlUrl; //공고URL
    private String splInfTpCd; //공급정보구분코드
    private String ccrCnntSysDsCd; //고객센터연계시스템구분코드
    private String uppAisTpCd; //상위매물유형코드
    private String aisTpCd; //매물유형코드
    private String panDt; //모집공고일

}
