package com.DailyMelody.serviceImpl;

import com.DailyMelody.repository.MusicRepository;
import com.DailyMelody.repository.CollectionRepository;
import com.DailyMelody.service.MusicService;
import com.DailyMelody.vo.MusicInfo;
import com.DailyMelody.vo.CollectionInfo;
import com.DailyMelody.po.Music;
import com.DailyMelody.po.Collection;
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
            // 调用 API 并获取返回结果
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // 检查返回状态码
            if (rootNode.get("code").asInt() != 0) {
                return "Unknown Festival";
            }

            // 解析 "data -> list" 节点，找到当天的节日信息
            JsonNode listNode = rootNode.get("data").get("list");
            String currentDate = getCurrentDateFormatted(); // 获取当前日期（格式：20241229）

            for (JsonNode node : listNode) {
                if (node.get("date").asText().equals(currentDate)) {
                    // 返回部分节日的英文名，如果没有找到则返回 "Unknown Festival"
                    return festivalMap.getOrDefault(node.get("holiday").asText(), "Unknown Festival");
                }
            }
            return "No Festival"; // 如果没有找到节日，返回默认值

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown Festival"; // 如果调用失败或解析错误，返回默认值
        }
    }

    private Music recommendMusicBasedOnConditions(String weather, String date, String festival) {
        if (!"unknown".equals(weather) && !"Unknown Festival".equals(festival)) {
            List<Music> weatherAndFestivalMusic = musicRepository.findByNameContainingBothKeywords(weather, festival);
            if (!weatherAndFestivalMusic.isEmpty()) {
                return weatherAndFestivalMusic.get(0); // 返回匹配天气和节日关键词的第一首音乐
            }
        }

        if (!"Unknown Festival".equals(festival) && !"No Festival".equals(festival)) {
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

        List<Music> defaultMusic = musicRepository.findByNameContainingBothKeywords("love", "sunshine");
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

