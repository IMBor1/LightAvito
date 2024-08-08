package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ads.AdDTO;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {
    @GetMapping
    public ResponseEntity<AdsDTO> findAllAds() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<AdDTO> createAd(@RequestBody AdDTO adDTO) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAdInfo(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAdInfo(@PathVariable Integer id, @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDTO> findMyAds() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity updateAdImage(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

}
