package com.project.hang.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hang_master")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HangMaster {

    @Id
    @Column(name = "PAN_ID")
    private String panId; //공고아이디

    @Column(name = "UPP_AIS_TP_NM")
    private String uppAisTpNm; //공고유형

    @Column(name = "AIS_TP_CD_NM")
    private String aisTpCdNm; //공고세부유형명

    @Column(name = "PAN_NM")
    private String panNm; //공고명

    @Column(name = "CNP_CD_NM")
    private String cnpCdNm; //지역명

    @Column(name = "PAN_NT_ST_DT")
    private String panNtStDt; //공고게시일

    @Column(name = "CLSG_DT")
    private String clsgDt; //공고마감일

    @Column(name = "PAN_SS")
    private String panSs; //공고상태

    @Column(name = "DTL_URL")
    private String dtlUrl; //공고URL

    @Column(name = "SPL_INF_TP_CD")
    private String splInfTpCd; //공급정보구분코드

    @Column(name = "CCR_CNNT_SYS_DS_CD")
    private String ccrCnntSysDsCd; //고객센터연계시스템구분코드

    @Column(name = "UPP_AIS_TP_CD")
    private String uppAisTpCd; //상위매물유형코드

    @Column(name = "AIS_TP_CD")
    private String aisTpCd; //매물유형코드

    @Column(name = "PAN_DT")
    private String panDt; //모집공고일

    @OneToMany
    @JoinTable(
            name = "DSSBD",
            joinColumns = {@JoinColumn(name = "PAN_ID", referencedColumnName = "PAN_ID")})
    private List<DsSbd> dsSbdList;
}
