package com.neuedu.controller.backend;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.vo.ImageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.UUID;

@Controller
@RequestMapping("/manage/")
public class UploadController {
    @Value("${shopping.image}")
    private String image;
    @GetMapping(value = "/upload")
    public String upload(){
        return "upload";
    }
    @PostMapping(value = "upload")
    @ResponseBody
    public ServerResponse upload(@RequestParam("uploadfile") MultipartFile uploadfile){
        if(uploadfile==null&&uploadfile.getOriginalFilename().equals("")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR,"必须上传图片");
        }
        //获取上传图片的名称
        String oldFileName=uploadfile.getOriginalFilename();
        //获取文件扩展名
        String extendName=oldFileName.substring(oldFileName.lastIndexOf('.'));
        //生成新的文件
        String newFileName= UUID.randomUUID().toString()+extendName;
        File mkdir=new File("f:/upload");
        if(!mkdir.exists()){
            mkdir.mkdirs();
        }
        File newFile=new File(mkdir,newFileName);
        try{
            uploadfile.transferTo(newFile);
            //http://localhost/filename
            ImageVO imageVO=new ImageVO(newFileName,image+newFileName);
            return  ServerResponse.serverResponseBySuccess(imageVO);
        }catch(IOException e){
            e.printStackTrace();
        }
        return ServerResponse.serverResponseByError();
    }
}
