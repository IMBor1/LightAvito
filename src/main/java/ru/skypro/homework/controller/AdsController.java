package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    @GetMapping
    public ResponseEntity<AdsDto> findAllAds() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<AdDto> createAd(@RequestBody AdDto adDTO) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAdInfo(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAdInfo(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDto createOrUpdateAd) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> findMyAds() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity updateAdImage(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

}
