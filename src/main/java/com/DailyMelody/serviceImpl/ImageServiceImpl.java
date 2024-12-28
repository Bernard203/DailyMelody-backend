package com.DailyMelody.serviceImpl;

import com.DailyMelody.exception.DailyMelodyException;
import com.DailyMelody.service.ImageService;
import com.DailyMelody.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: DingXiaoyu
 * @Date: 12:02 2023/12/13
 * 实现了上传文件的功能。
*/
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    OssUtil ossUtil;

    @Override
    public String upload(MultipartFile file) {
        try {
            return ossUtil.upload(file.getOriginalFilename(),file.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
            throw DailyMelodyException.fileUploadFail();
        }
    }
}
