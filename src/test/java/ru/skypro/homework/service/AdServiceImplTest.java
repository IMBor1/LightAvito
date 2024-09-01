package ru.skypro.homework.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.ImageAd;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageAdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AdServiceImplTest {

    @InjectMocks
    private AdServiceImpl adService;

    @Mock
    private AdRepository adRepository;

    @Mock
    private AdMapper adMapper;

    @Mock
    private ImageAdRepository imageAdRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultipartFile file;

    @Value("/path/to/ad/images")
    private String imageDir = "test/images";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        adService = new AdServiceImpl(adRepository, adMapper, imageAdRepository, userRepository);
        ReflectionTestUtils.setField(adService, "imageDir", "/path/to/ad/images"); // Установите значение для imageDir
    }


    @Test
    public void testGetAdInfo() {
        Ad ad = new Ad();
        ad.setId(1);

        when(adRepository.findById(1)).thenReturn(Optional.of(ad));
        when(adMapper.adToExtendedAd(ad)).thenReturn(new ExtendedAdDto());

        ExtendedAdDto result = adService.getAdInfo(1);

        assertNotNull(result);
        verify(adRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteAd() {
        adService.deleteAd(1);
        verify(adRepository, times(1)).deleteById(1);
    }

    @Test
    public void testUpdateInfoAd() {
        CreateOrUpdateAdDto updateAdDto = new CreateOrUpdateAdDto();
        updateAdDto.setTitle("Updated Title");
        updateAdDto.setPrice(150);
        updateAdDto.setDescription("Updated Description");

        Ad ad = new Ad();
        ad.setId(1);

        when(adRepository.findById(1)).thenReturn(Optional.of(ad));
        when(adRepository.save(ad)).thenReturn(ad);
        when(adMapper.adToAdDTO(ad)).thenReturn(new AdDto());

        AdDto result = adService.updateInfoAd(1, updateAdDto);

        assertNotNull(result);
        verify(adRepository, times(1)).findById(1);
        verify(adRepository, times(1)).save(ad);
    }

    @Test
    public void testFindMyAds() {
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("user@example.com");

        List<Ad> ads = new ArrayList<>();
        when(adRepository.findByAuthorEmail("user@example.com")).thenReturn(ads);
        when(adMapper.toListAdDTO(ads)).thenReturn(new ArrayList<>());

        AdsDto result = adService.findMyAds();

        assertNotNull(result);
        assertEquals(0, result.getCount());
        verify(adRepository, times(1)).findByAuthorEmail("user@example.com");
    }

    @Test
    public void testUpdateAdImage() throws IOException {
        Ad ad = new Ad();
        ad.setId(1);
        ImageAd imageAd = new ImageAd();

        when(adRepository.getReferenceById(1)).thenReturn(ad);
        when(imageAdRepository.findImageAdByAdId(1)).thenReturn(imageAd);
        when(file.getOriginalFilename()).thenReturn("image.jpg");
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(file.getSize()).thenReturn(1024L);
        when(file.getContentType()).thenReturn("image/jpeg");

        // Установка значения для imageDir
        ReflectionTestUtils.setField(adService, "imageDir", "/path/to/ad/images");

        ImageAd result = adService.updateAdImage(1, file);

        assertNotNull(result);
        assertEquals("image/jpeg", result.getMediaType());
        verify(adRepository, times(1)).getReferenceById(1);
    }
}


