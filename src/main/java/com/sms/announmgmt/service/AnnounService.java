package com.sms.announmgmt.service;

import com.sms.announmgmt.pojo.Announ;

import java.util.ArrayList;
import java.util.List;

public interface AnnounService {

    /**
     * 添加公告
     */
    Integer addAnnoun(Announ announ);

    /**
     * 删除公告
     * @param id
     * @return
     */
    Integer delAnnounById(Integer id);

    /**
     * 查找所有公告
     * @return
     */
    ArrayList<Announ> findAllAnnoun();

    /**
     * 通过id查找公告
     * @param id
     * @return
     */
    ArrayList<Announ> findAnnounById(Integer id);

    /**
     * 通过类型查找公告
     * @param type
     * @return
     */
    ArrayList<Announ> findAnnounByType(Integer type);

}
