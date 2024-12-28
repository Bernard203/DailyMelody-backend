package com.DailyMelody.serviceImpl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DailyMelody.service.ExcelService;
import com.DailyMelody.service.OrderService;
import com.DailyMelody.util.ExcelUtil;
import com.DailyMelody.util.OssUtil;

@Service
public class ExcelServiceImpl implements ExcelService{

    @Autowired
    ExcelUtil excelUtil;

    @Autowired
    OssUtil ossUtil;

    @Autowired
    OrderService orderService;

    @Override
    public String export() {
        InputStream inputStream = excelUtil.exportExcel(orderService.getAllOrders());
        String url = ossUtil.upload("order.xlsx", inputStream);
        return url;
    }
    
}
