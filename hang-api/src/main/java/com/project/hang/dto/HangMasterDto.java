package com.project.hang.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HangMasterDto {

    @JsonProperty(value = "PAN_ID")
    private String panId; //공고아이디

    @JsonProperty(value = "UPP_AIS_TP_NM")
    private String uppAisTpNm; //공고유형

    @JsonProperty(value = "AIS_TP_CD_NM")
    private String aisTpCdNm; //공고세부유형명

    @JsonProperty(value = "PAN_NM")
    private String panNm; //공고명

    @JsonProperty(value = "CNP_CD_NM")
    private String cnpCdNm; //지역명

    @JsonProperty(value = "PAN_NT_ST_DT")
    private String panNtStDt; //공고게시일

    @JsonProperty(value = "CLSG_DT")
    private String clsgDt; //공고마감일

    @JsonProperty(value = "PAN_SS")
    private String panSs; //공고상태

    @JsonProperty(value = "DTL_URL")
    private String dtlUrl; //공고URL

    @JsonProperty(value = "SPL_INF_TP_CD")
    private String splInfTpCd; //공급정보구분코드

    @JsonProperty(value = "CCR_CNNT_SYS_DS_CD")
    private String ccrCnntSysDsCd; //고객센터연계시스템구분코드

    @JsonProperty(value = "UPP_AIS_TP_CD")
    private String uppAisTpCd; //상위매물유형코드

    @JsonProperty(value = "AIS_TP_CD")
    private String aisTpCd; //매물유형코드

    @JsonProperty(value = "PAN_DT")
    private String panDt; //모집공고일

}
