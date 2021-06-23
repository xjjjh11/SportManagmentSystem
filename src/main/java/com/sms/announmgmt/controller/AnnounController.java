package com.sms.announmgmt.controller;

import com.sms.announmgmt.pojo.Announ;
import com.sms.announmgmt.service.AnnounService;
import com.sms.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "公告操作接口")
@Slf4j
@RestController
@RequestMapping("/announ")
public class AnnounController {

    @Autowired
    private AnnounService announService;

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/admin/addAnnounce")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content",value = "公告内容",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "time",value = "公告发送时间",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "type",value = "公告类型",dataType = "Integer",dataTypeClass = Integer.class),
    })
    @ApiOperation(value = "添加公告",notes = "管理员添加公告")
    public Map<String, Object> addAnnoun(@RequestParam("content") String content,@RequestParam("time") String time,
                                         @RequestParam("type") Integer type){
        Map<String,Object> map = new HashMap<>();
        Announ announ = new Announ();
        announ.setContent(content);
        announ.setTime(time);
        announ.setType(type);
        if (announService.addAnnoun(announ) == 1){
            map.put("title","成功添加公告!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "添加公告失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryAnnounce")
    @ApiOperation(value = "查询所有公告",notes = "查询所有公告")
    public Map<String, Object> showAllAnnoun(){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Announ> allAnnouns = announService.findAllAnnoun();
        if (allAnnouns != null){
            map.put("title","公告查询成功!");
            map.put("datas",allAnnouns);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "公告查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryAnnounceById")
    @ApiImplicitParam(name = "id",value = "公告id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "通过id查询公告",notes = "通过id查询公告")
    public Map<String, Object> showAnnounById(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Announ> allAnnouns = announService.findAnnounById(id);
        if (allAnnouns != null){
            map.put("title","公告查询成功!");
            map.put("datas",allAnnouns);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "公告查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryAnnounceByType")
    @ApiImplicitParam(name = "type",value = "公告类型",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "通过类型查询公告",notes = "通过类型查询公告")
    public Map<String, Object> showAnnounByType(@RequestParam("type") Integer type){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Announ> allAnnouns = announService.findAnnounByType(type);
        if (allAnnouns != null){
            map.put("title","公告查询成功!");
            map.put("datas",allAnnouns);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "公告查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/admin/deleteAnnounce")
    @ApiImplicitParam(name = "id",value = "公告id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "删除公告",notes = "管理员删除公告")
    public Map<String, Object> deleteAnnounce(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        if (announService.delAnnounById(id) == 1){
            map.put("title","成功删除公告!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "删除公告失败!");
        return ResultUtil.resultError(map);
    }
}
