package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.AdDTO;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;

@Service
public class AdServiceImpl {
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    public AdDTO save(AdDTO adDTO) {
        Ad ad = adMapper.adDTOtoAd(adDTO);
        Ad adSave = adRepository.save(ad);
        return adMapper.adToAdDTO(adSave);
    }
}
