package com.kafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafe.dto.WaffleEkleDto;
import com.kafe.dto.WaffleKartiResponseDto;
import com.kafe.entity.Kullanici;
import com.kafe.entity.WaffleKarti;
import com.kafe.exception.IsKuraliException;
import com.kafe.repository.KullaniciRepository;
import com.kafe.repository.WaffleKartiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaffleKartiService {

    private static final java.util.Set<String> GECERLI_TURLER =
        java.util.Set.of("BARDAK", "KASE");

    private final WaffleKartiRepository waffleKartiRepository;
    private final KullaniciRepository kullaniciRepository;

    public WaffleKartiResponseDto kartGetir(Long kullaniciId) {
        WaffleKarti kart = kartBul(kullaniciId);
        return toDto(kart);
    }

    @Transactional
    public WaffleKartiResponseDto waffleEkle(Long kullaniciId, WaffleEkleDto dto) {
        String tur = dto.getWaffleTuru().trim().toUpperCase();
        if (!GECERLI_TURLER.contains(tur)) {
            throw new IsKuraliException("Geçersiz waffle türü. BARDAK veya KASE seçin.");
        }

        WaffleKarti kart = kartBul(kullaniciId);

        if (kart.bedavaHakKazandiMi()) {
            throw new IsKuraliException(
                "Kart dolu. Önce 7. bedava waffle verilmeli, sonra yeni sipariş eklenebilir.");
        }

        try {
            kart.waffleEkle(tur);
        } catch (IllegalStateException ex) {
            throw new IsKuraliException(ex.getMessage());
        }

        return toDto(waffleKartiRepository.save(kart));
    }

    @Transactional
    public WaffleKartiResponseDto bedavaWaffleVer(Long kullaniciId) {
        WaffleKarti kart = kartBul(kullaniciId);

        try {
            kart.bedavaWaffleKullan();
        } catch (IllegalStateException ex) {
            throw new IsKuraliException(ex.getMessage());
        }

        return toDto(waffleKartiRepository.save(kart));
    }

    @Transactional
    public WaffleKartiResponseDto waffleSil(Long kullaniciId, int index) {
        WaffleKarti kart = kartBul(kullaniciId);

        try {
            kart.waffleSil(index);
        } catch (IllegalStateException ex) {
            throw new IsKuraliException(ex.getMessage());
        }

        return toDto(waffleKartiRepository.save(kart));
    }

    @Transactional
    public WaffleKartiResponseDto kartlariTemizle(Long kullaniciId) {
        WaffleKarti kart = kartBul(kullaniciId);

        if (!kart.kartDoluMu()) {
            throw new IsKuraliException("Kutuları temizlemek için 6 kutu dolu olmalı.");
        }

        try {
            kart.kartlariTemizle();
        } catch (IllegalStateException ex) {
            throw new IsKuraliException(ex.getMessage());
        }

        return toDto(waffleKartiRepository.save(kart));
    }

    private WaffleKarti kartBul(Long kullaniciId) {
        Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
            .orElseThrow(() -> new IsKuraliException("Kullanıcı bulunamadı: " + kullaniciId));

        return waffleKartiRepository.findByKullaniciId(kullanici.getId())
            .orElseThrow(() -> new IsKuraliException("Waffle kartı bulunamadı"));
    }

    private WaffleKartiResponseDto toDto(WaffleKarti kart) {
        Kullanici k = kart.getKullanici();
        return WaffleKartiResponseDto.builder()
            .kartiId(kart.getId())
            .kullaniciId(k.getId())
            .kullaniciAdSoyad(k.getAd() + " " + k.getSoyad())
            .waffleListesi(kart.waffleListesiGetir())
            .toplamSayi(kart.getToplamSayi())
            .kartDolu(kart.kartDoluMu())
            .bedavaHakVar(kart.bedavaHakKazandiMi())
            .bedavaKullanildi(kart.isBedavaKullanildi())
            .sonGuncelleme(kart.getSonGuncelleme())
            .build();
    }
}
