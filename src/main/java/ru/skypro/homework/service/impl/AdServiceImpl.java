package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.ImageAd;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageAdRepository;
import ru.skypro.homework.service.AdService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис для работы с объявлениями.
 */
@Service
public class AdServiceImpl implements AdService {
    @Value("${path.to.ad-image.folder}")
    private String imageDir;
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    private final ImageAdRepository imageAdRepository;

    public AdServiceImpl(AdRepository adRepository,
                         AdMapper adMapper,
                         ImageAdRepository imageAdRepository) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.imageAdRepository = imageAdRepository;
    }

    /**
     * Сохранение объявления.
     * @param adDTO DTO объявления
     * @return объект DTO {@link AdDto}
     */
    @Override
    public AdDto saveAd(AdDto adDTO) {
        Ad ad = adMapper.adDTOtoAd(adDTO);
        Ad adSave = adRepository.save(ad);
        return adMapper.adToAdDTO(adSave);
    }

    /**
     * Возращает список всех объявлений.
     * @return объект класса {@link AdsDto}
     */
    @Override
    public AdsDto getAllAds() {
        List<AdDto> listAdDTO = adMapper.toListAdDTO(adRepository.findAll());
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(listAdDTO.size());
        adsDto.setResults(listAdDTO);
        return adsDto;
    }

    /**
     * Возращает расширенную информацию по объявлению
     * @param id айди объявления
     * @return объект {@link ExtendedAdDto}
     */
    @Override
    public ExtendedAdDto getAdInfo(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return adMapper.adToExtendedAd(ad);
    }

    /**
     * Удаляет объяевление.
     * @param id айди объявления
     */
    @Override
    public void deleteAd(Integer id) {
        adRepository.deleteById(id);
    }

    /**
     * Редактирует информацию в объявлении.
     * @param id айди объявления
     * @param createOrUpdateAdDto обновленная информация
     * @return объект {@link AdDto}
     */
    @Override
    public AdDto updateInfoAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad ad = adRepository.findById(id).orElseThrow();
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        return adMapper.adToAdDTO(adRepository.save(ad));
    }

    /**
     * Возращает список объявлений пользователя.
     * @return список объявлений {@link AdsDto}
     */
    @Override
    public AdsDto findMyAds() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Ad> ads = adRepository.findByAuthorEmail(auth.getName());
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(adMapper.toListAdDTO(ads));
        return adsDto;
    }

    @Override
    public ImageAd updateAdImage(Integer id, MultipartFile file) throws IOException {
        Ad ad = adRepository.getReferenceById(id);
        Path filePath = Path.of(imageDir, id + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        ImageAd image = imageAdRepository.findImageAdByAdId(id);
        image.setAd(ad);
        image.setFilePath(filePath.toString());
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        image.setData(file.getBytes());
        ad.setImage(image);
        adRepository.save(ad);
        return image;
    }

}
