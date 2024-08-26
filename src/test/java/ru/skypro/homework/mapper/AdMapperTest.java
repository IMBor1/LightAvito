package ru.skypro.homework.mapper;

/*import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdMapperTest {

    @InjectMocks
    private AdMapper adMapper = Mappers.getMapper(AdMapper.class);

    @Test
    public void testAdToAdDTO() {
        Ad ad = new Ad();
        ad.setId(1);
        User author = new User();
        author.setId(2);
        ad.setAuthor(author);
        Image image = new Image();
        image.setFilePath("path/to/image");
        ad.setImage(image);

        AdDto adDto = adMapper.adToAdDTO(ad);

        assertEquals(1, adDto.getPk());
        assertEquals(2, adDto.getAuthor());
        assertEquals("path/to/image", adDto.getImage());
    }

    @Test
    public void testToListAdDTO() {
        Ad ad1 = new Ad();
        ad1.setId(1);
        User author1 = new User();
        author1.setId(2);
        ad1.setAuthor(author1);
        Image image1 = new Image();
        image1.setFilePath("path/to/image1");
        ad1.setImage(image1);

        Ad ad2 = new Ad();
        ad2.setId(3);
        User author2 = new User();
        author2.setId(4);
        ad2.setAuthor(author2);
        Image image2 = new Image();
        image2.setFilePath("path/to/image2");
        ad2.setImage(image2);

        List<Ad> ads = Arrays.asList(ad1, ad2);
        List<AdDto> adDtos = adMapper.toListAdDTO(ads);

        assertEquals(2, adDtos.size());
        assertEquals(1, adDtos.get(0).getPk());
        assertEquals(2, adDtos.get(0).getAuthor());
        assertEquals("path/to/image1", adDtos.get(0).getImage());
        assertEquals(3, adDtos.get(1).getPk());
        assertEquals(4, adDtos.get(1).getAuthor());
        assertEquals("path/to/image2", adDtos.get(1).getImage());
    }

    @Test
    public void testAdToExtendedAd() {
        Ad ad = new Ad();
        ad.setId(1);
        User author = new User();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setPhone("1234567890");
        author.setEmail("john.doe@example.com");
        ad.setAuthor(author);
        Image image = new Image();
        image.setFilePath("path/to/image");
        ad.setImage(image);

        ExtendedAdDto extendedAdDto = adMapper.adToExtendedAd(ad);

        assertEquals(1L, extendedAdDto.getPk());
        assertEquals("John", extendedAdDto.getAuthorFirstName());
        assertEquals("Doe", extendedAdDto.getAuthorLastName());
        assertEquals("1234567890", extendedAdDto.getPhone());
        assertEquals("john.doe@example.com", extendedAdDto.getEmail());
        assertEquals("path/to/image", extendedAdDto.getImage());
    }

    @Test
    public void testAdDTOtoAd() {
        AdDto adDto = new AdDto();
        adDto.setPk(1);
        adDto.setAuthor(2);
        adDto.setImage("path/to/image");

        Ad ad = adMapper.adDTOtoAd(adDto);

        assertEquals(1, ad.getId());
        assertNull(ad.getAuthor());
        assertNull(ad.getImage());
    }
}*/
