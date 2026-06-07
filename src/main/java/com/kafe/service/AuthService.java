package com.kafe.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kafe.dto.LoginDto;
import com.kafe.dto.LoginResponseDto;
import com.kafe.entity.Kasiyer;
import com.kafe.repository.KasiyerRepository;
import com.kafe.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final KasiyerRepository kasiyerRepository;

    public LoginResponseDto login(LoginDto dto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getKullaniciAdi(), dto.getSifre()));

        Kasiyer kasiyer = kasiyerRepository.findByKullaniciAdi(dto.getKullaniciAdi())
            .orElseThrow();

        UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(kasiyer.getKullaniciAdi())
            .password(kasiyer.getSifre())
            .authorities("ROLE_" + kasiyer.getRol())
            .build();

        return LoginResponseDto.builder()
            .token(jwtUtil.generateToken(userDetails))
            .kullaniciAdi(kasiyer.getKullaniciAdi())
            .rol(kasiyer.getRol())
            .build();
    }
}
