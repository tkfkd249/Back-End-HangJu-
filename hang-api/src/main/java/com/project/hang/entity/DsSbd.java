package com.project.hang.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DSSBD")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DsSbd {
    @Id
    @Column(name = "PAN_ID")
    private String panId; //공고아이디

    @Column(name = "INFO_INDEX")
    private String infoIndex;

    @Column(name = "LGDN_ADR")
    private String lgdnAdr;

    @Column(name = "LGDN_DTL_ADR")
    private String lgdnDtlAdr;

    @Column(name = "DDO_AR")
    private String ddoAr;

    @Column(name = "HSH_CNT")
    private String hshCnt;

    @Column(name = "HTN_FMLA_DESC")
    private String htnFmlaDesc;

    @Column(name = "MVIN_XPC_YM")
    private String mvinXpcYm;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;
}
