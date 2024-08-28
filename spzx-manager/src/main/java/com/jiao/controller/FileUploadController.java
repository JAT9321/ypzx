package com.jiao.controller;

import com.jiao.model.vo.common.Result;
import com.jiao.model.vo.common.ResultCodeEnum;
import com.jiao.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件上传 ，头像上传")
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;


    @Operation(summary = "上传文件")
    @PostMapping(value = "/fileUpload")
    public Result<String> fileUpload(@RequestParam(value = "file") MultipartFile multipartFile) {
        String fileUrl = fileUploadService.uploadFile(multipartFile);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }

}
