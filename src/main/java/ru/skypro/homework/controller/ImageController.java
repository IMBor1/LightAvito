package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.AvatarRepository;

import java.util.Optional;
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {
    private final AvatarRepository avatarRepository;
    private final AdRepository adRepository;

    public ImageController(AvatarRepository avatarRepository, AdRepository adRepository) {
        this.avatarRepository = avatarRepository;
        this.adRepository = adRepository;
    }

    @GetMapping(value = "/image-avatar/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, "image/*"})
    public byte[] getImageAvatar(@PathVariable Integer id) {
        Optional<Avatar> imageByUserId = avatarRepository.findImageByUserId(id);
        return imageByUserId.orElseThrow().getData();
    }
    @GetMapping(value = "/image-ad/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, "image/*"})
    public byte[] getImageAd(@PathVariable Integer id) {
        return adRepository.findById(id).orElseThrow().getImage().getData();
    }
}
