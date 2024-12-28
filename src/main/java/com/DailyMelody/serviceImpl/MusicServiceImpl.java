package com.DailyMelody.serviceImpl;

import com.DailyMelody.repository.MusicRepository;
import com.DailyMelody.repository.CollectionRepository;
import com.DailyMelody.service.MusicService;
import com.DailyMelody.vo.MusicInfo;
import com.DailyMelody.vo.CollectionInfo;
import com.DailyMelody.po.Music;
import com.DailyMelody.po.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Override
    public void createMusic(MusicInfo musicInfo) {
        Music music = new Music();
        music.setName(musicInfo.getName());
        music.setSentence(musicInfo.getSentence());
        music.setMusicUrl(musicInfo.getMusicUrl());
        music.setLrcUrl(musicInfo.getLrcUrl());
        music.setImgUrl(musicInfo.getImgUrl());
        musicRepository.save(music);
    }

    @Override
    public MusicInfo getMusicById(Long musicId) {
        Music music = musicRepository.findById(musicId).orElseThrow(() -> new RuntimeException("Music not found"));
        return new MusicInfo(music.getName(), music.getSentence(), music.getMusicUrl(), music.getLrcUrl(), music.getImgUrl());
    }

    @Override
    public void addCollection(CollectionInfo collectionInfo) {
        Collection collection = new Collection();
        collection.setMusicId(collectionInfo.getMusicId());
        collection.setDate(collectionInfo.getDate());
        collection.setFestival(collectionInfo.getFestival());
        collection.setThought(collectionInfo.getThought());
        collectionRepository.save(collection);
    }

    @Override
    public List<CollectionInfo> getCollectionInfo() {
        return collectionRepository.findAll().stream()
                .map(collection -> new CollectionInfo(collection.getMusicId(), collection.getDate(), collection.getFestival(), collection.getThought()))
                .collect(Collectors.toList());
    }

    @Override
    public CollectionInfo getCollectionById(Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new RuntimeException("Collection not found"));
        return new CollectionInfo(collection.getMusicId(), collection.getDate(), collection.getFestival(), collection.getThought());
    }
}
