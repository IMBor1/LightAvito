package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.exeption.AdNotFoundExeseption;
import ru.skypro.homework.exeption.CommentNotFoundExeption;
import ru.skypro.homework.exeption.UserNotFaundExeption;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final EntityManager entityManager;

    public CommentServiceImpl(AdRepository adRepository,
                              CommentRepository commentRepository,
                              UserRepository userRepository,
                              CommentMapper commentMapper,
                              EntityManager entityManager) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
        this.entityManager = entityManager;
    }

    /**
     * Получает коментраии по айди объявления
     * @param id айди объявления
     * @return объект {@link CommentsDto} список коментариев и их количество
     */
    @Override
    public CommentsDto getComments(Integer id) {
        List<Comment> comments = commentRepository.findAllByAdId(id);
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(comments.size());
        commentsDto.setResults(commentMapper.commentsToCommentsDTO(comments));
        return commentsDto;
    }

    /**
     * Добавляет комментарий к объявлению
     * @param adId айди объявления
     * @param createOrUpdateCommentDto текст объявления {@link CreateOrUpdateCommentDto}
     * @param userName имя пользователя
     * @return информация о коментарии {@link CommentDto}
     */
    @Override
    public CommentDto setComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto, String userName) {
        Ad ad = adRepository.getReferenceById(adId);
        User user = userRepository.findByEmail(userName).orElseThrow(UserNotFaundExeption::new);
        Comment comment = new Comment();
        comment.setAd(ad);
        comment.setText(createOrUpdateCommentDto.getText());
        comment.setAuthor(user);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentRepository.save(comment);
        ad.setComment(comment);
        adRepository.save(ad);
        return commentMapper.commentToCommentDTO(comment);
    }

    /**
     * Удаляет комментарий к объявлению
     * @param adId айди оюъявления
     * @param commentId айди коментария
     */
    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer commentId) {
        Ad ad = adRepository.getReferenceById(adId);
        Comment comment = commentRepository.getReferenceById(commentId);

        entityManager.createNativeQuery("DELETE FROM ad_comments WHERE comments_id = ?")
                .setParameter(1, commentId)
                .executeUpdate();

        adRepository.save(ad);
        commentRepository.deleteById(commentId);
    }

    /**
     * Изменяет существующий комментарий к объявлению.
     * @param adId айди объявления
     * @param commentId айди комментария
     * @param createOrUpdateCommentDto текст комментария {@link CreateOrUpdateCommentDto}
     * @return информатия о комментарии {@link CommentDto}
     */
    @Override
    @Transactional
    public CommentDto updateComment(Integer adId,
                                    Integer commentId,
                                    CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundExeseption::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundExeption::new);
        comment.setText(createOrUpdateCommentDto.getText());

        entityManager.createNativeQuery("DELETE FROM ad_comments WHERE comments_id = ?")
                .setParameter(1, commentId)
                .executeUpdate();

        commentRepository.save(comment);
        ad.setComment(comment);
        adRepository.save(ad);
        return commentMapper.commentToCommentDTO(comment);
    }

}
