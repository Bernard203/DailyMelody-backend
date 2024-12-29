package com.DailyMelody.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollectionInfo {
    private Long id;
    private Long musicId;
    private String musicName;
    private String date;
    private String festival;
    private String thought;
    private String imgUrl;
    private String sunSet;
    private String sunRise;
    private String sentence;
}
