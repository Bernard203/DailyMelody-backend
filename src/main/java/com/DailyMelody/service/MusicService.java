package com.DailyMelody.service;

import com.DailyMelody.vo.MusicInfo;
import com.DailyMelody.vo.CollectionInfo;

import java.util.List;

public interface MusicService {
    void createMusic(MusicInfo musicInfo);
    MusicInfo getMusicById(Long musicId);
    MusicInfo getRecommendedMusic();
    void addCollection(CollectionInfo collectionInfo);
    List<CollectionInfo> getCollectionInfo();
    CollectionInfo getCollectionById(Long collectionId);
}

