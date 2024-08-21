package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.AdsDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;

/**
 * Сервис для работы с объявлениями.
 */
@Service
public class AdServiceImpl {
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    /**
     * Сохранение объявления.
     * @param adDTO DTO объявления
     * @return объект DTO {@link AdDto}
     */
    public AdDto saveAd(AdDto adDTO) {
        Ad ad = adMapper.adDTOtoAd(adDTO);
        Ad adSave = adRepository.save(ad);
        return adMapper.adToAdDTO(adSave);
    }

    /**
     * Возращает список всех объявлений.
     * @return объект класса {@link AdsDto}
     */
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
    public ExtendedAdDto getAdInfo(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return adMapper.adToExtendedAd(ad);
    }

    /**
     * Удаляет объяевление.
     * @param id айди объявления
     */
    public void deleteAd(Integer id) {
        adRepository.deleteById(id);
    }

    /**
     * Редактирует информацию в объявлении.
     * @param id айди объявления
     * @param createOrUpdateAdDto обновленная информация
     * @return объект {@link AdDto}
     */
    public AdDto updateInfoAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad ad = adRepository.findById(id).orElseThrow();
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        return adMapper.adToAdDTO(adRepository.save(ad));
    }

    /**
     * Возращает список объявлений пользователя.
     * @param id айди пользователя
     * @return список объявлений {@link AdsDto}
     */
    public AdsDto findMyAds(Integer id) {
        List<Ad> ads = adRepository.findByAuthorId(id);
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(adMapper.toListAdDTO(ads));
        return adsDto;
    }

}
