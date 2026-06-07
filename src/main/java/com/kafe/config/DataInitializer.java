package com.kafe.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kafe.entity.Kasiyer;
import com.kafe.repository.KasiyerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final KasiyerRepository kasiyerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!kasiyerRepository.existsByKullaniciAdi("admin")) {
            kasiyerRepository.save(Kasiyer.builder()
                .kullaniciAdi("admin")
                .sifre(passwordEncoder.encode("admin123"))
                .rol("KASIYER")
                .build());
        }
    }
}
