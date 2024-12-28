package com.DailyMelody.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MusicInfo {
    private String name;
    private String sentence;
    private String musicUrl;
    private String lrcUrl;
    private String imgUrl;
}
