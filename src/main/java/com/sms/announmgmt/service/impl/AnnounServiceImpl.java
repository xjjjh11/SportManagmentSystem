package com.sms.announmgmt.service.impl;

import com.sms.announmgmt.mapper.AnnounMapper;
import com.sms.announmgmt.pojo.Announ;
import com.sms.announmgmt.service.AnnounService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnounServiceImpl implements AnnounService {

    @Autowired
    private AnnounMapper announMapper;

    @Override
    public Integer addAnnoun(Announ announ){
        return announMapper.insAnnoun(announ);
    }

    @Override
    public Integer delAnnounById(Integer id) {
        return announMapper.delAnnounById(id);
    }

    @Override
    public ArrayList<Announ> findAllAnnoun() {
        return announMapper.findAllAnnoun();
    }

    @Override
    public ArrayList<Announ> findAnnounById(Integer id) {
        return announMapper.findAnnounById(id);
    }

    @Override
    public ArrayList<Announ> findAnnounByType(Integer type) {
        return announMapper.findAnnounByType(type);
    }


}
