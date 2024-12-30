package com.DailyMelody.po;

import com.DailyMelody.vo.CollectionInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Collection {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "music_id")
    private Long musicId;

    @Basic
    @Column(name = "music_name")
    private String musicName;

    @Basic
    @Column(name = "date")
    private String date;

    @Basic
    @Column(name = "festival")
    private String festival;

    @Basic
    @Column(name = "thought")
    private String thought;

    @Basic
    @Column(name = "imgUrl")
    private String imgUrl;

    @Basic
    @Column(name = "sunSet")
    private String sunSet;

    @Basic
    @Column(name = "sunRise")
    private String sunRise;

    @Basic
    @Column(name = "sentence")
    private String sentence;

    @Basic
    @Column(name = "weather")
    private String weather;

    // 将当前实体对象转换为 VO
    public CollectionInfo toVO() {
        return new CollectionInfo(this.id, this.musicId, this.musicName, this.date, this.festival, this.thought, this.imgUrl, this.sunSet, this.sunRise, this.sentence, this.weather);
    }
}