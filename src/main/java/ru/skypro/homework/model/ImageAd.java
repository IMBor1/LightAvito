package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ImageAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    @OneToOne
    private Ad ad;
}
