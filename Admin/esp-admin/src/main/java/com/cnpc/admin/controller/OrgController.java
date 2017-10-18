package com.cnpc.admin.controller;

import com.cnpc.common.controller.BaseController;
import com.cnpc.admin.entity.Org;
import com.cnpc.admin.service.OrgService;
import com.cnpc.common.message.ObjectRestResponse;
import com.cnpc.common.util.JsonUtil;
import com.cnpc.common.util.PinYingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/org")
public class OrgController extends BaseController<OrgService, Org> {

    @Autowired
    private OrgService orgService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Org> add(@RequestBody Org org){
        String spell = PinYingUtil.convertHanzi2Pinyin(org.getName(), true);
        org.setSpell(spell);
        baseService.insertSelective(org);
        return new ObjectRestResponse<Org>().rel(true);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Org> update(@RequestBody Org org){
        String spell = PinYingUtil.convertHanzi2Pinyin(org.getName(), true);
        org.setSpell(spell);
        baseService.updateSelectiveById(org);
        return new ObjectRestResponse<Org>().rel(true);
    }

    @GetMapping("orgTree")
    public ObjectRestResponse orgTree(Org org){
        List<Map> orgs = baseService.getTree(org);
        String str = JsonUtil.ObjectToJson(orgs).replaceAll("name","label");
        return new ObjectRestResponse<String>().rel(true).data(str);
    }

    @GetMapping(value = "/getNextCode")
    public ObjectRestResponse getNextCode(String parentId,String levelCode){
        String res = orgService.getNextLevelCode(parentId,levelCode);
        return new ObjectRestResponse<String>().rel(true).data(res);
    }

    @GetMapping(value = "/checkCode")
    public Boolean checkCode(String id , String code){
        Boolean res = orgService.checkCode(id,code);
        return res;
    }

}
