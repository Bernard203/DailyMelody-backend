package com.DailyMelody.po;

import com.DailyMelody.vo.MusicInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Music {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "sentence")
    private String sentence;

    @Basic
    @Column(name = "music_url")
    private String musicUrl;

    @Basic
    @Column(name = "lrc_url")
    private String lrcUrl;

    @Basic
    @Column(name = "img_url")
    private String imgUrl;

    // 将当前实体对象转换为 VO
    public MusicInfo toVO() {
        return new MusicInfo(this.name, this.sentence, this.musicUrl, this.lrcUrl, this.imgUrl);
    }
}

