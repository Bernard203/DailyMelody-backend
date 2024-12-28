package com.DailyMelody.controller;

import com.DailyMelody.service.MusicService;
import com.DailyMelody.vo.MusicInfo;
import com.DailyMelody.vo.CollectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @PostMapping("/")
    public void createMusic(@RequestBody MusicInfo musicInfo) {
        musicService.createMusic(musicInfo);
    }

    @GetMapping("/{musicId}")
    public MusicInfo getMusicById(@PathVariable Long musicId) {
        return musicService.getMusicById(musicId);
    }

    @GetMapping("/getMusic")
    public MusicInfo getMusic() {
        return musicService.getRecommendedMusic();
    }

    @PostMapping("/collection")
    public void addCollection(@RequestBody CollectionInfo collectionInfo) {
        musicService.addCollection(collectionInfo);
    }

    @GetMapping("/collection/all")
    public List<CollectionInfo> getCollectionInfo() {
        return musicService.getCollectionInfo();
    }

    @GetMapping("/collection/{collectionId}")
    public CollectionInfo getCollectionById(@PathVariable Long collectionId) {
        return musicService.getCollectionById(collectionId);
    }
}

