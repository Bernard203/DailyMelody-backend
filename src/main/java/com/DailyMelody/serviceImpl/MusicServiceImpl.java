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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        music.setKeyword(musicInfo.getKeyword());
        musicRepository.save(music);
    }

    @Override
    public MusicInfo getMusicById(Long musicId) {
        Music music = musicRepository.findById(musicId).orElseThrow(() -> new RuntimeException("Music not found"));
        return new MusicInfo(music.getName(), music.getSentence(), music.getMusicUrl(), music.getLrcUrl(), music.getImgUrl(), music.getKeyword());
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

    @Override
    public MusicInfo getRecommendedMusic() {
        // 获取天气信息（这里假设调用了外部天气 API）
        String weather = getWeather();
        String date = getCurrentDate();
        String festival = getFestival();

        // 根据天气、日期和节日推荐歌曲
        Music music = recommendMusicBasedOnConditions(weather, date, festival);
        if (music == null) {
            throw new RuntimeException("No suitable music found");
        }

        // 返回 MusicInfo
        return music.toVO();
    }

    private String getWeather() {
        // 调用天气 API
        Map<String, String> weatherMap = new HashMap<>();
        weatherMap.put("晴", "sunny");
        weatherMap.put("雨", "rainy");
        weatherMap.put("雪", "snowy");
        weatherMap.put("多云", "cloudy");
        weatherMap.put("阴", "overcast");
        weatherMap.put("雾", "foggy");
        String weather = "";
        try{
            URL url = new URL("http://t.weather.itboy.net/api/weather/city/101250601");
            InputStreamReader isReader =  new InputStreamReader(url.openStream(),"UTF-8");//“UTF- 8”万国码，可以显示中文，这是为了防止乱码
            BufferedReader br = new BufferedReader(isReader);//采用缓冲式读入
            String str;
            while((str = br.readLine()) != null){
                String regex="\\p{Punct}+";
                String digit[]=str.split(regex);
//                System.out.println('\n'+"天气:"+digit[67]+" "+digit[63]+digit[65]);
                weather = digit[67];
            }
            br.close();
            isReader.close();
        }
        catch(Exception exp){
            System.out.println(exp);
        }

        return weatherMap.getOrDefault(weather, "unknown");
    }

    private String getCurrentDate() {
        // 获取当前日期，格式为 yyyy-MM-dd
        return java.time.LocalDate.now().toString();
    }

    private String getFestival() {
        // 根据日期判断节日，这里模拟返回
        return "Christmas";
    }

    private Music recommendMusicBasedOnConditions(String weather, String date, String festival) {
        // 示例逻辑：根据条件筛选音乐（这里直接从数据库随机选一首）
        List<Music> musicList = musicRepository.findAll();

        if (festival.equals("Christmas")) {
            return musicList.stream()
                    .filter(music -> music.getName().contains("Christmas"))
                    .findFirst()
                    .orElse(null);
        } else if (weather.equals("Sunny")) {
            return musicList.stream()
                    .filter(music -> music.getName().contains("Sunny"))
                    .findFirst()
                    .orElse(null);
        } else {
            // 默认返回第一首
            return musicList.isEmpty() ? null : musicList.get(0);
        }
    }
}

