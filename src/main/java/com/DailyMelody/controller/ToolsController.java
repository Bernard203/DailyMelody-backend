package com.DailyMelody.controller;

import com.DailyMelody.service.ExcelService;
import com.DailyMelody.service.ImageService;
import com.DailyMelody.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ToolsController {
    @Autowired
    ImageService imageService;

    @Autowired
    ExcelService excelService;

    @PostMapping("/images")
    public ResultVO<String> upload(@RequestParam MultipartFile file){
        return ResultVO.buildSuccess(imageService.upload(file));
    }

    @GetMapping("/excel")
    public ResultVO<String> export(){
        return ResultVO.buildSuccess(excelService.export());
    }
}