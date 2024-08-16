package ru.skypro.homework.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.skypro.homework.dto.ads.AdDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.model.Ad;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdMapper {

    @Mappings({
         @Mapping(target = "author", source = "author.id")
    })
    AdDTO adToAdDTO(Ad ad);

    List<AdDTO> toListAdDTO(List<Ad> ads);

    CreateOrUpdateAdDTO adToCreateOrUpdateAd(Ad ad);

    @Mappings({
            @Mapping(target = "authorFirstName", source = "author.firstName"),
            @Mapping(target = "authorLastName", source = "author.lastName"),
            @Mapping(target = "phone", source = "author.phone"),
            @Mapping(target = "email",source = "author.email")
    })
    ExtendedAdDTO adToExtendedAd(Ad ad);
    @Mapping(target = "author", expression = "java()")
    Ad adDTOtoAd(AdDTO adDTO);






}

