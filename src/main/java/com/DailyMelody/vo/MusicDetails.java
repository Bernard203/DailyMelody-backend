package com.DailyMelody.vo;

public class MusicDetails {
    private MusicInfo musicInfo;
    private WeatherInfo weatherInfo;
    private String festival;
    private String time;

    public MusicDetails(MusicInfo vo, WeatherInfo weather, String festival, String date) {
        this.musicInfo = vo;
        this.weatherInfo = weather;
        this.festival = festival;
        this.time = date;
    }
}
