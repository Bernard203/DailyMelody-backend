package com.DailyMelody.repository;

import com.DailyMelody.po.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByNameContaining(String name);
    List<Music> findByKeywordContaining(String keyword);
    @Query("SELECT m FROM Music m WHERE m.keyword LIKE %:keyword1% AND m.keyword LIKE %:keyword2%")
    List<Music> findByNameContainingBothKeywords(@Param("keyword1") String keyword1, @Param("keyword2") String keyword2);
}
