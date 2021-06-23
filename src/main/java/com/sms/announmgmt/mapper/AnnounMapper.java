package com.sms.announmgmt.mapper;

import com.sms.announmgmt.pojo.Announ;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

public interface AnnounMapper {

    /**
     * 添加公告
     */
    @Insert("INSERT INTO tb_sms_notic\n" +
            "VALUES\n" +
            "(default,#{content},#{time},#{type})")
    Integer insAnnoun(Announ announ);


    /**
     * 删除公告
     * @param id
     * @return
     */
    @Delete("DELETE FROM tb_sms_notic WHERE id = #{id}")
    Integer delAnnounById(Integer id);

    /**
     * 查询所有公告
     * @return
     */
    @Select("SELECT * FROM tb_sms_notic")
    ArrayList<Announ> findAllAnnoun();

    /**
     * 通过id查询公告
     * @param id
     * @return
     */
    @Select("SELECT * FROM tb_sms_notic WHERE id = #{id}")
    ArrayList<Announ> findAnnounById(Integer id);

    /**
     * 通过类型查询公告
     * @param type
     * @return
     */
    @Select("SELECT * FROM tb_sms_notic WHERE type = #{type}")
    ArrayList<Announ> findAnnounByType(Integer type);
}
