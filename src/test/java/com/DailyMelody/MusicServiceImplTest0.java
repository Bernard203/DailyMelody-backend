package com.DailyMelody;

import com.DailyMelody.serviceImpl.MusicServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MusicServiceImplTest0 {

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testGetFestival() {
        MusicServiceImpl musicService = new MusicServiceImpl();
        String festival = musicService.getFestival();
        System.out.println(festival);
        assertNotNull(festival, "Festival should not be null");
    }
}