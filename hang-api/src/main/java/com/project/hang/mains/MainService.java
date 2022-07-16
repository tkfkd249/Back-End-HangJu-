package com.project.hang.mains;

import com.project.hang.dto.DsSbdDto;
import com.project.hang.dto.HangMasterDto;
import com.project.hang.entity.DsSbd;
import com.project.hang.entity.HangMaster;
import com.project.hang.repository.DsSbdRepository;
import com.project.hang.repository.HangMasterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.modelmbean.ModelMBean;
import java.util.List;

@Service
public class MainService {

    @Autowired
    private ModelMapper modelMapper;

    private final HangMasterRepository hangMasterRepository;
    private final DsSbdRepository dsSbdRepository;

    public MainService(HangMasterRepository hangMasterRepository, DsSbdRepository dsSbdRepository) {
        this.hangMasterRepository = hangMasterRepository;
        this.dsSbdRepository = dsSbdRepository;
    }

    @Transactional
    public void insertHangMasterInfo(List<HangMasterDto> masterList) {

        for(int i=0;i<masterList.size();i++){
            HangMaster hangMaster = modelMapper.map(masterList.get(i),HangMaster.class);
            hangMasterRepository.save(hangMaster);
        }
    }

    @Transactional
    public void insertDsSbdInfo(DsSbdDto dsSbdDto) {
        DsSbd dsSbd = modelMapper.map(dsSbdDto,DsSbd.class);
        dsSbdRepository.save(dsSbd);
    }

    public void deleteAllHangMaster() {
        hangMasterRepository.deleteAll();
    }

    public void deleteAllHangDetail(){
        dsSbdRepository.deleteAll();
    }

    public List<HangMaster> getHangMasterInfo(){
        return hangMasterRepository.findAllByAisTpCdNm("행복주택");
    }
}
