package com.DailyMelody.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollectionInfo {
    private Long musicId;
    private String date;
    private String festival;
    private String thought;
}
