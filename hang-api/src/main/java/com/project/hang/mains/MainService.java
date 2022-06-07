package com.project.hang.mains;

import com.project.hang.dto.HangMasterDto;
import com.project.hang.entity.HangMaster;
import com.project.hang.repository.HangMasterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MainService {

    private final HangMasterRepository hangMasterRepository;

    public MainService(HangMasterRepository hangMasterRepository) {
        this.hangMasterRepository = hangMasterRepository;
    }

    @Transactional
    public void insertHangMasterInfo(List<HangMaster> masterList) {

        for(int i=0;i<masterList.size();i++){
            hangMasterRepository.save(masterList.get(i));
        }
    }

    public void deleteAllHangMaster() {
        hangMasterRepository.deleteAll();
    }
}
