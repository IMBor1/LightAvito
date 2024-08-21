package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.service.impl.AdServiceImpl;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdServiceImpl adService;


    @GetMapping
    public ResponseEntity<AdsDto> findAllAds() {
        AdsDto adsDto = adService.getAllAds();
        return ResponseEntity.ok(adsDto);
    }

    @PostMapping
    public ResponseEntity<AdDto> createAd(@RequestBody AdDto adDTO) {
        AdDto adDto = adService.saveAd(adDTO);
        return ResponseEntity.ok(adDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAdInfo(@PathVariable Integer id) {
        ExtendedAdDto extendedAdDto = adService.getAdInfo(id);
        return ResponseEntity.ok(extendedAdDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable Integer id) {
        adService.deleteAd(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAdInfo(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDto createOrUpdateAd) {
        AdDto adDto = adService.updateInfoAd(id, createOrUpdateAd);
        return ResponseEntity.ok(adDto);
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
