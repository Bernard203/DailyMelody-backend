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
    @Column(name = "date")
    private String date;

    @Basic
    @Column(name = "festival")
    private String festival;

    @Basic
    @Column(name = "thought")
    private String thought;

    // 将当前实体对象转换为 VO
    public CollectionInfo toVO() {
        return new CollectionInfo(this.musicId, this.date, this.festival, this.thought);
    }
}

