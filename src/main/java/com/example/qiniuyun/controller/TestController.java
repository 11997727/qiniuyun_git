package com.example.qiniuyun.controller;

import com.example.qiniuyun.util.QNYUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author 吴成卓
 * @version V1.0
 *
 *
 * @Project: qiniuyun
 * @Package com.example.qiniuyun.controller
 * @Description:
 * @date 2020/1/8 星期三 12:12
 */
@Controller
public class TestController {
    @Resource
    private QNYUtils qnyUtils;


    @RequestMapping("/")
    public String init(){
        return "upload";
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file, Model model) {
        String name = file.getOriginalFilename();
        try {
            Response response = qnyUtils.upload(file.getInputStream(), name);
            DefaultPutRet defaultPutRet = qnyUtils.defaultPutRet(response);
            System.out.println(qnyUtils.getPath()+defaultPutRet.key);
            System.out.println(defaultPutRet.toString());
            System.out.println(name);
            model.addAttribute("src",qnyUtils.getPath()+defaultPutRet.key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }catch (IOException io){
            io.printStackTrace();
        }
        return "show";
    }

        @RequestMapping("del")
        @ResponseBody
        public String del(String key){
            boolean delete = qnyUtils.delete(key);
            return new Gson().toJson(delete);
        }

}
