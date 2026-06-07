package com.kafe.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kafe.dto.HataResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IsKuraliException.class)
    public ResponseEntity<HataResponseDto> isKurali(IsKuraliException ex) {
        return hata(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HataResponseDto> validation(MethodArgumentNotValidException ex) {
        String mesaj = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
        return hata(HttpStatus.BAD_REQUEST, mesaj);
    }

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<HataResponseDto> authHatasi(RuntimeException ex) {
        return hata(HttpStatus.UNAUTHORIZED, "Kullanıcı adı veya şifre hatalı");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HataResponseDto> genel(Exception ex) {
        return hata(HttpStatus.INTERNAL_SERVER_ERROR, "Beklenmeyen bir hata oluştu");
    }

    private ResponseEntity<HataResponseDto> hata(HttpStatus durum, String mesaj) {
        return ResponseEntity.status(durum).body(HataResponseDto.builder()
            .durum(durum.value())
            .mesaj(mesaj)
            .zaman(LocalDateTime.now())
            .build());
    }
}
