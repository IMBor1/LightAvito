package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.ImageAd;

public interface AvatarRepository extends JpaRepository<Avatar,Integer> {
    Avatar findImageByUserId(Integer id);
}
