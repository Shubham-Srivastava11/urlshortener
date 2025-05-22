package com.url.urlshortener.controller;

import com.url.urlshortener.dto.UrlRequest;
import com.url.urlshortener.dto.UrlResponse;
import com.url.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shorten(@RequestBody UrlRequest request) {
        return ResponseEntity.ok(urlService.shortenUrl(request));
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode) {
        String longUrl = urlService.getOriginalUrl(shortCode);
        return new RedirectView(longUrl);
    }
}
