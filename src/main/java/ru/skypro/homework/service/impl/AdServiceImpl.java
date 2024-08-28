package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис для работы с объявлениями.
 */
@Transactional
@Service
public class AdServiceImpl implements AdService {
    @Value("${path.to.ad-image.folder}")
    private String imageDir;
    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final ImageAdRepository imageAdRepository;

    private final UserRepository userRepository;

    public AdServiceImpl(AdRepository adRepository,
                         AdMapper adMapper,
                         ImageAdRepository imageAdRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.imageAdRepository = imageAdRepository;
        this.userRepository = userRepository;
    }

    /**
     * Сохранение объявления.
     * @param adDTO DTO объявления
     * @param file Файл фотографии объявления
     * @param userName Имя пользователя или емейл
     * @return объект DTO {@link AdDto}
     */
    @Override
    public AdDto saveAd(AdDto adDTO, MultipartFile file, String userName) throws IOException {
        Ad ad = adRepository.save(adMapper.adDTOtoAd(adDTO));
        ad.setAuthor(userRepository.findByEmail(userName).orElseThrow());
        ad.setImage(updateAdImage(ad.getId(),file));
        adRepository.save(ad);
        return adMapper.adToAdDTO(ad);
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

    /**
     * Созранеет или меняет фотографию в объявлении.
     * @param id айди объявления
     * @param file файл объявления {@link MultipartFile}
     * @return Объект класса {@link ImageAd}
     * @throws IOException
     */
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
        ImageAd image = imageAdRepository.findImageAdByAdId(id).orElse(new ImageAd());
        image.setAd(ad);
        image.setFilePath(filePath.toString().replace("." + getExtension(file.getOriginalFilename()),""));
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        image.setData(file.getBytes());
        imageAdRepository.save(image);
        ad.setImage(image);
        adRepository.save(ad);
        return image;
    }

}
