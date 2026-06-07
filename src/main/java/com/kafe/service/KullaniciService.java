package com.kafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafe.dto.KullaniciKayitDto;
import com.kafe.dto.KullaniciResponseDto;
import com.kafe.entity.Kullanici;
import com.kafe.entity.WaffleKarti;
import com.kafe.exception.IsKuraliException;
import com.kafe.repository.KullaniciRepository;
import com.kafe.repository.WaffleKartiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final WaffleKartiRepository waffleKartiRepository;

    @Transactional
    public KullaniciResponseDto kayitOl(KullaniciKayitDto dto) {
        if (kullaniciRepository.existsByTelefon(dto.getTelefon())) {
            throw new IsKuraliException("Bu telefon numarası zaten kayıtlı");
        }

        Kullanici kullanici = kullaniciRepository.save(Kullanici.builder()
            .ad(dto.getAd())
            .soyad(dto.getSoyad())
            .telefon(dto.getTelefon())
            .build());

        waffleKartiRepository.save(WaffleKarti.builder()
            .kullanici(kullanici)
            .build());

        return toDto(kullanici);
    }

    public KullaniciResponseDto getir(Long id) {
        Kullanici kullanici = kullaniciRepository.findById(id)
            .orElseThrow(() -> new IsKuraliException("Kullanıcı bulunamadı: " + id));
        return toDto(kullanici);
    }

    private KullaniciResponseDto toDto(Kullanici kullanici) {
        return KullaniciResponseDto.builder()
            .id(kullanici.getId())
            .ad(kullanici.getAd())
            .soyad(kullanici.getSoyad())
            .telefon(kullanici.getTelefon())
            .kayitTarihi(kullanici.getKayitTarihi())
            .build();
    }
}
