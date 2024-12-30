package com.DailyMelody.controller;

import com.DailyMelody.service.MusicService;
import com.DailyMelody.vo.MusicDetails;
import com.DailyMelody.vo.MusicInfo;
import com.DailyMelody.vo.CollectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @PostMapping("/create")
    public void createMusic(@RequestBody MusicInfo musicInfo) {
        musicService.createMusic(musicInfo);
    }

    @GetMapping("/{musicId}")
    public MusicInfo getMusicById(@PathVariable Long musicId) {
        return musicService.getMusicById(musicId);
    }

    @GetMapping("/getMusic")
    public MusicDetails getMusic() {
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

    @GetMapping("/lrc/{lrcUrl}")
    public String getLrc(@RequestParam String lrcUrl) {
        return musicService.getLrc(lrcUrl);
    }
}

