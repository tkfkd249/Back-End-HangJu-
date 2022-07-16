package com.project.hang.api;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.project.hang.dto.HangMasterDto;
import com.project.hang.entity.HangMaster;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;

public class LHApi {

    public String getLhList() throws IOException{

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552555/lhLeaseNoticeInfo1/lhLeaseNoticeInfo1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=RqAc81%2FhL2dYXb2wAlZHqV0so079oKqXpk1n1jhanhCcgKxPtXuiGfwW3CbnLxbgNToHzkhJ7hezlfHM5c5wlA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("PG_SZ","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("PAGE","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        // urlBuilder.append("&" + URLEncoder.encode("PAN_NM","UTF-8") + "=" + URLEncoder.encode("대전", "UTF-8")); /*공고명으로 조회*/
        urlBuilder.append("&" + URLEncoder.encode("UPP_AIS_TP_CD","UTF-8") + "=" + URLEncoder.encode("06", "UTF-8")); /*공고유형코드*/
        // urlBuilder.append("&" + URLEncoder.encode("CNP_CD","UTF-8") + "=" + URLEncoder.encode("11", "UTF-8")); /*지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("PAN_SS","UTF-8") + "=" + URLEncoder.encode("공고중", "UTF-8")); /*공고상태코드*/
        // urlBuilder.append("&" + URLEncoder.encode("PAN_NT_ST_DT","UTF-8") + "=" + URLEncoder.encode("2019.07.23", "UTF-8")); /*공고게시일*/
        // urlBuilder.append("&" + URLEncoder.encode("CLSG_DT","UTF-8") + "=" + URLEncoder.encode("2019.08.22", "UTF-8")); /*공고마감일*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return sb.toString();
    }
    public String getLhDetailList(HangMasterDto info) throws IOException{

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552555/lhLeaseNoticeDtlInfo1/getLeaseNoticeDtlInfo1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=RqAc81%2FhL2dYXb2wAlZHqV0so079oKqXpk1n1jhanhCcgKxPtXuiGfwW3CbnLxbgNToHzkhJ7hezlfHM5c5wlA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("SPL_INF_TP_CD","UTF-8") + "=" + URLEncoder.encode(info.getSplInfTpCd(), "UTF-8")); /*분양임대공고문조회 API의 특정 공고의 응답 메시지 중 공급정보구분코드*/
        urlBuilder.append("&" + URLEncoder.encode("CCR_CNNT_SYS_DS_CD","UTF-8") + "=" + URLEncoder.encode(info.getCcrCnntSysDsCd().toString(), "UTF-8")); /*분양임대공고문조회 API의 특정 공고의 응답 메시지 중 고객센터연계시스템구분코드*/
        urlBuilder.append("&" + URLEncoder.encode("PAN_ID","UTF-8") + "=" + URLEncoder.encode(info.getPanId(), "UTF-8")); /*분양임대공고문조회 API의 특정 공고의 응답 메시지 중 공고아이디*/
        urlBuilder.append("&" + URLEncoder.encode("UPP_AIS_TP_CD","UTF-8") + "=" + URLEncoder.encode(info.getUppAisTpCd(), "UTF-8")); /*분양임대공고문조회 API의 특정 공고의 응답 메시지 중 상위매물유형코드*/
        urlBuilder.append("&" + URLEncoder.encode("AIS_TP_CD","UTF-8") + "=" + URLEncoder.encode(info.getAisTpCd().toString(), "UTF-8")); /*분양임대공고문조회 API의 특정 공고의 응답 메시지 중 매물유형코드*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }

    //일단 void로...
    public void getLHHomeMapInfo(){
        Geocoder geocoder = new Geocoder();
        // setAddress : 변환하려는 주소 (경기도 성남시 분당구 등)
        // setLanguate : 인코딩 설정
        //GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(location).setLanguage("ko").getGeocoderRequest();
        GeocodeResponse geocoderResponse;
    }
}
