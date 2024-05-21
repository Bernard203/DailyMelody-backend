package com.seecoder.BlueWhale.serviceImpl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.BlueWhale.service.ExcelService;
import com.seecoder.BlueWhale.service.OrderService;
import com.seecoder.BlueWhale.util.ExcelUtil;
import com.seecoder.BlueWhale.util.OssUtil;

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
