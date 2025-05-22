package com.url.urlshortener.service;

import com.url.urlshortener.dto.UrlRequest;
import com.url.urlshortener.dto.UrlResponse;
import com.url.urlshortener.entity.Url;
import com.url.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final String BASE_URL = "http://shortify.free.nf/";

    public UrlResponse shortenUrl(UrlRequest request) {
        String code = (request.getCustomAlias() != null && !request.getCustomAlias().isEmpty())
                ? request.getCustomAlias()
                : generateShortCode();

        if (urlRepository.existsByShortCode(code)) {
            throw new RuntimeException("Short code already exists");
        }

        Url url = Url.builder()
                .shortCode(code)
                .longUrl(request.getLongUrl())
                .createdAt(LocalDateTime.now())
                .expiryDate(request.getExpiryDate())
                .build();

        urlRepository.save(url);

        // Set in Redis with optional TTL
        if (request.getExpiryDate() != null) {
            Duration ttl = Duration.between(LocalDateTime.now(), request.getExpiryDate());
            redisTemplate.opsForValue().set(code, request.getLongUrl(), ttl);
        } else {
            redisTemplate.opsForValue().set(code, request.getLongUrl());
        }

        return new UrlResponse(BASE_URL + code);
    }

    public String getOriginalUrl(String code) {
        String longUrl = redisTemplate.opsForValue().get(code);

        if (longUrl != null) return longUrl;

        Optional<Url> urlOpt = urlRepository.findByShortCode(code);
        if (urlOpt.isEmpty()) throw new RuntimeException("Short URL not found");

        Url url = urlOpt.get();

        // Cache it
        if (url.getExpiryDate() != null) {
            Duration ttl = Duration.between(LocalDateTime.now(), url.getExpiryDate());
            redisTemplate.opsForValue().set(code, url.getLongUrl(), ttl);
        } else {
            redisTemplate.opsForValue().set(code, url.getLongUrl());
        }

        return url.getLongUrl();
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
