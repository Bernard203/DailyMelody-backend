package com.DailyMelody.repository;

import com.DailyMelody.po.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
