package com.DailyMelody.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherInfo {
    private String temperatureLow;
    private String temperatureHigh;
    public String weather;
    private String fx;
    private String fl;
    private String sunrise;
    private String sunset;

    public WeatherInfo(String temperatureLow, String temperatureHigh, String wea, String fx, String fl, String sunrise, String sunset) {
        this.temperatureLow = temperatureLow;
        this.temperatureHigh = temperatureHigh;
        this.weather = wea;
        this.fx = fx;
        this.fl = fl;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
