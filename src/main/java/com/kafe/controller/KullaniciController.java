package com.kafe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kafe.dto.KullaniciKayitDto;
import com.kafe.dto.KullaniciResponseDto;
import com.kafe.service.KullaniciService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/kullanicilar")
@RequiredArgsConstructor
public class KullaniciController {

    private final KullaniciService kullaniciService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KullaniciResponseDto kayitOl(@Valid @RequestBody KullaniciKayitDto dto) {
        return kullaniciService.kayitOl(dto);
    }

    @GetMapping("/{id}")
    public KullaniciResponseDto getir(@PathVariable Long id) {
        return kullaniciService.getir(id);
    }
}
