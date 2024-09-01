package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.ImageAd;

import java.util.Optional;
@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Integer> {
    Optional<Avatar> findImageByUserId(Integer id);
}
