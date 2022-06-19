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
public class DsSbdDto {

    @JsonProperty(value = "PAN_ID")
    private String panId; //공고아이디

    @JsonProperty(value = "INFO_INDEX")
    private String infoIndex;

    @JsonProperty(value = "LGDN_ADR")
    private String lgdnAdr;

    @JsonProperty(value = "LGDN_DTL_ADR")
    private String lgdnDtlAdr;

    @JsonProperty(value = "DDO_AR")
    private String ddoAr;

    @JsonProperty(value = "HSH_CNT")
    private String hshCnt;

    @JsonProperty(value = "HTN_FMLA_DESC")
    private String htnFmlaDesc;

    @JsonProperty(value = "MVIN_XPC_YM")
    private String mvinXpcYm;

    @JsonProperty(value = "LATITUDE")
    private String latitude;

    @JsonProperty(value = "LONGITUDE")
    private String longitude;
}
