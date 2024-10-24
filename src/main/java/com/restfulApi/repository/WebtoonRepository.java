package com.restfulApi.repository;

import com.restfulApi.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {
}