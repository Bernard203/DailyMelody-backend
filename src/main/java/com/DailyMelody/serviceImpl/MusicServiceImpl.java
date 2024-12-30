package com.DailyMelody.serviceImpl;

import com.DailyMelody.repository.MusicRepository;
import com.DailyMelody.repository.CollectionRepository;
import com.DailyMelody.service.MusicService;
import com.DailyMelody.vo.MusicDetails;
import com.DailyMelody.vo.MusicInfo;
import com.DailyMelody.vo.CollectionInfo;
import com.DailyMelody.po.Music;
import com.DailyMelody.po.Collection;
import com.DailyMelody.vo.WeatherInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        return new MusicInfo(music.getId(), music.getName(), music.getSentence(), music.getMusicUrl(), music.getLrcUrl(), music.getImgUrl(), music.getKeyword());
    }

    @Override
    public void addCollection(CollectionInfo collectionInfo) {
        Collection collection = new Collection();
        collection.setMusicId(collectionInfo.getMusicId());
        collection.setMusicName(collectionInfo.getMusicName());
        collection.setFestival(collectionInfo.getFestival());
        collection.setImgUrl(collectionInfo.getImgUrl());
        collection.setSunSet(collectionInfo.getSunSet());
        collection.setSunRise(collectionInfo.getSunRise());
        collection.setDate(collectionInfo.getDate());
        collection.setSentence(collectionInfo.getSentence());
        collection.setThought(collectionInfo.getThought());
        collectionRepository.save(collection);
    }

    @Override
    public List<CollectionInfo> getCollectionInfo() {
        return collectionRepository.findAll().stream()
                .map(collection -> new CollectionInfo(collection.getId(),collection.getMusicId(), collection.getMusicName(), collection.getDate(), collection.getFestival(), collection.getThought(), collection.getImgUrl(), collection.getSunSet(), collection.getSunRise(), collection.getSentence(), collection.getWeather()))
                .collect(Collectors.toList());
    }

    @Override
    public CollectionInfo getCollectionById(Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new RuntimeException("Collection not found"));
        return new CollectionInfo(collection.getId(), collection.getMusicId(), collection.getMusicName(), collection.getDate(), collection.getFestival(), collection.getThought(), collection.getImgUrl(), collection.getSunSet(), collection.getSunRise(), collection.getSentence(), collection.getWeather());
    }

    @Override
    public MusicDetails getRecommendedMusic() {
        WeatherInfo weather = getWeather();
        String date = getCurrentDate();
        String festival = getFestival();

        // 根据天气、日期和节日推荐歌曲
        Music music = recommendMusicBasedOnConditions(weather.weather, festival);
        if (music == null) {
            throw new RuntimeException("No suitable music found");
        }

        // 返回 MusicInfo
        return new MusicDetails(music.toVO(), weather, festival, date);
    }

    public WeatherInfo getWeather() {
        String url = "http://t.weather.itboy.net/api/weather/city/101190101";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.get("status").equals(200)) {
            throw new RuntimeException("Failed to fetch weather data");
        }

        Map<String, Object> data = (Map<String, Object>) response.get("data");
        if (data == null) {
            throw new RuntimeException("Weather data is null");
        }

        Map<String, Object> todayForecast = ((List<Map<String, Object>>) data.get("forecast")).get(0);

        String temperatureLow = ((String) todayForecast.get("low")).replace("低温 ", "").replace("℃", "").trim();
        String temperatureHigh = ((String) todayForecast.get("high")).replace("高温 ", "").replace("℃", "").trim();
        String weather = (String) todayForecast.get("type");
        String fx = (String) todayForecast.get("fx");
        String fl = (String) todayForecast.get("fl");
        String sunrise = (String) todayForecast.get("sunrise");
        String sunset = (String) todayForecast.get("sunset");
//
//        System.out.println("temperatureLow: " + temperatureLow);
//        System.out.println("temperatureHigh: " + temperatureHigh);
//        System.out.println("weather: " + weather);
//        System.out.println("fx: " + fx);
//        System.out.println("fl: " + fl);
//        System.out.println("sunrise: " + sunrise);
//        System.out.println("sunset: " + sunset);

        return new WeatherInfo(temperatureLow, temperatureHigh, weather, fx, fl, sunrise, sunset);
    }

    private String getCurrentDate() {
        // 获取当前日期，格式为 yyyy-MM-dd
        return java.time.LocalDate.now().toString();
    }

    private String getCurrentDateFormatted() {
        // 将当前日期格式化为 "yyyyMMdd" 格式（如 20241229）
        return java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public String getFestival() {
        // 根据日期判断节日，因为节日太多，目前仅map部分节日的英文名
        Map<String, String> festivalMap = new HashMap<>();
        festivalMap.put("春节", "Spring Festival");
        festivalMap.put("元旦", "New Year's Day");
        festivalMap.put("国庆节", "National Day");
        festivalMap.put("中秋节", "Mid-Autumn Festival");
        festivalMap.put("端午节", "Dragon Boat Festival");
        festivalMap.put("清明节", "Tomb-sweeping Day");
        festivalMap.put("劳动节", "Labour Day");
        festivalMap.put("儿童节", "Children's Day");
        festivalMap.put("重阳节", "Double Ninth Festival");
        festivalMap.put("愚人节", "April Fool's Day");
        festivalMap.put("圣诞节", "Christmas");
        festivalMap.put("植树节", "Arbor Day");
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.apihubs.cn/holiday/get?field=date,workday,holiday,holiday_today&cn=1&size=366&year=2024";
        try {
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // 检查返回状态码
            if (rootNode.get("code").asInt() != 0) {
                return "";
            }

            // 解析 "data -> list" 节点，找到当天的节日信息
            JsonNode listNode = rootNode.get("data").get("list");
            String currentDate = getCurrentDateFormatted(); // 获取当前日期（格式：20241229）

            for (JsonNode node : listNode) {
                if (node.get("date").asText().equals(currentDate)) {
                    // 返回部分节日的英文名，如果没有找到则返回 "Unknown Festival"
                    return festivalMap.getOrDefault(node.get("holiday").asText(), "");
                }
            }
            return ""; // 如果没有找到节日，返回默认值

        } catch (Exception e) {
            e.printStackTrace();
            return ""; // 如果调用失败或解析错误，返回默认值
        }
    }

    private Music recommendMusicBasedOnConditions(String weather, String festival) {
        if (!"unknown".equals(weather) && !"".equals(festival)) {
            List<Music> weatherAndFestivalMusic = musicRepository.findByNameContainingBothKeywords(weather, festival);
            if (!weatherAndFestivalMusic.isEmpty()) {
                return weatherAndFestivalMusic.get(0); // 返回匹配天气和节日关键词的第一首音乐
            }
        }

        if (!"".equals(festival)) {
            List<Music> festivalMusic = musicRepository.findByKeywordContaining(festival);
            if (!festivalMusic.isEmpty()) {
                return festivalMusic.get(0); // 返回匹配节日关键词的第一首音乐
            }
        }

        if (!"unknown".equals(weather)) {
            List<Music> weatherMusic = musicRepository.findByKeywordContaining(weather);
            if (!weatherMusic.isEmpty()) {
                return weatherMusic.get(0); // 返回匹配天气关键词的第一首音乐
            }
        }

        List<Music> defaultMusic = musicRepository.findByNameContainingBothKeywords(weather, festival);
        if (!defaultMusic.isEmpty()) {
            return defaultMusic.get(0); // 返回匹配的第一首音乐
        }

        // 如果没有任何匹配项，返回数据库中的第一首歌曲作为默认推荐
        List<Music> allMusic = musicRepository.findAll();
        if (!allMusic.isEmpty()) {
            return allMusic.get(0);
        }

        return null;
    }
}

