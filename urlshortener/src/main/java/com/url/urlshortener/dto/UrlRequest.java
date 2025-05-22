package com.url.urlshortener.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlRequest {
    private String longUrl;
    private String customAlias; // optional
    private LocalDateTime expiryDate; // optional
}

