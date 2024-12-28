package com.DailyMelody.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.DailyMelody.service.StoreService;
import com.DailyMelody.vo.ResultVO;
import com.DailyMelody.vo.StoreVO;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    
    @Autowired
    StoreService storeService;

    @PostMapping
    public ResultVO<Boolean> create(@RequestBody StoreVO storeVO){
        return ResultVO.buildSuccess(storeService.create(storeVO));
    }

    @GetMapping("/{id}")
    public ResultVO<StoreVO> getStore(@PathVariable(value = "id") Integer id){
        return ResultVO.buildSuccess(storeService.getStore(id));
    }

    @GetMapping("/all")
    public ResultVO<List<StoreVO>> getAllStores(){
        return ResultVO.buildSuccess(storeService.getAllStores());
    }

}
