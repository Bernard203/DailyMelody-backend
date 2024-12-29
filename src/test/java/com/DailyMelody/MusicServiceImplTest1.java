package com.DailyMelody;

import com.DailyMelody.serviceImpl.MusicServiceImpl;
import com.DailyMelody.vo.WeatherInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MusicServiceImplTest1 {

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testGetWeather() {
        MusicServiceImpl musicService = new MusicServiceImpl();
        WeatherInfo weather = musicService.getWeather();
//        System.out.println(weather.weather);
        assertNotNull(weather, "Festival should not be null");
    }
}