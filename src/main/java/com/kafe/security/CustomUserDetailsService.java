package com.kafe.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kafe.entity.Kasiyer;
import com.kafe.repository.KasiyerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final KasiyerRepository kasiyerRepository;

    @Override
    public UserDetails loadUserByUsername(String kullaniciAdi) throws UsernameNotFoundException {
        Kasiyer kasiyer = kasiyerRepository.findByKullaniciAdi(kullaniciAdi)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Kasiyer bulunamadı: " + kullaniciAdi));

        return new User(
                kasiyer.getKullaniciAdi(),
                kasiyer.getSifre(),
                List.of(new SimpleGrantedAuthority("ROLE_" + kasiyer.getRol()))
        );
    }
}