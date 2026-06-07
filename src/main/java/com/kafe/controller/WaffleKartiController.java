package com.kafe.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafe.dto.WaffleEkleDto;
import com.kafe.dto.WaffleKartiResponseDto;
import com.kafe.service.WaffleKartiService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/kartlar")
@RequiredArgsConstructor
public class WaffleKartiController {

    private final WaffleKartiService waffleKartiService;

    @GetMapping("/kullanici/{kullaniciId}")
    public WaffleKartiResponseDto kartGetir(@PathVariable Long kullaniciId) {
        return waffleKartiService.kartGetir(kullaniciId);
    }

    @PostMapping("/kullanici/{kullaniciId}/waffle")
    public WaffleKartiResponseDto waffleEkle(
            @PathVariable Long kullaniciId,
            @Valid @RequestBody WaffleEkleDto dto) {
        return waffleKartiService.waffleEkle(kullaniciId, dto);
    }

    @PostMapping("/kullanici/{kullaniciId}/bedava")
    public WaffleKartiResponseDto bedavaWaffleVer(@PathVariable Long kullaniciId) {
        return waffleKartiService.bedavaWaffleVer(kullaniciId);
    }

    @DeleteMapping("/kullanici/{kullaniciId}/waffle/{index}")
    public WaffleKartiResponseDto waffleSil(
            @PathVariable Long kullaniciId,
            @PathVariable int index) {
        return waffleKartiService.waffleSil(kullaniciId, index);
    }

    @PostMapping("/kullanici/{kullaniciId}/temizle")
    public WaffleKartiResponseDto kartlariTemizle(@PathVariable Long kullaniciId) {
        return waffleKartiService.kartlariTemizle(kullaniciId);
    }
}
