package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.ImageAdRepository;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class ImageController {
    private final AvatarRepository avatarRepository;
    private final ImageAdRepository imageAdRepository;
    @GetMapping(value = "/image-avatar/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, "image/*"})
    public byte[] getAvatar(@PathVariable Integer id) {
        return avatarRepository.findImageByUserId(id).orElseThrow().getData();
    }
    @GetMapping(value = "/image-ad/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, "image/*"})
    public byte[] getImageAd(@PathVariable Integer id) {
        return imageAdRepository.findImageAdByAdId(id).orElseThrow().getData();
    }

}
