package com.restfulApi.service;

import com.restfulApi.entity.Webtoon;
import com.restfulApi.payload.WebtoonDto;

import java.util.List;

public interface WebtoonService {
    public WebtoonDto createWebtoon(WebtoonDto webtoonDto);
    void deleteWebtoonById(Long id);
    WebtoonDto updateWebtoon(long id, WebtoonDto webtoonDto);
    List<WebtoonDto> getAllWebtoons();
    WebtoonDto getWebtoonById(Long id);
}
