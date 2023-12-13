package edu.tinkoff.tinkoffbackendacademypetproject.service;

import edu.tinkoff.tinkoffbackendacademypetproject.CommonAbstractTest;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.*;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.*;
import edu.tinkoff.tinkoffbackendacademypetproject.services.NewsPublicationService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.PublicationService;
import edu.tinkoff.tinkoffbackendacademypetproject.services.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NewsPublicationServiceTest extends CommonAbstractTest {
    @Autowired
    private NewsPublicationService newsPublicationService;

    @Autowired
    private NewsPublicationRepository newsPublicationRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @AfterEach
    void clear() {
        publicationRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE publication_pk_seq RESTART");
        accountRepository.deleteAll();
        jdbcTemplate.execute("ALTER SEQUENCE account_pk_seq RESTART");
    }

    @DisplayName("Find all publications in news")
    @ParameterizedTest(name = "{index} - find all publications in news")
    @CsvSource(value = {
            "0, 50",
    })
    void getPublicationsInNewsTest(Integer pageNumber, Integer pageSize) throws RoleNotFoundException {
        // given
        createBeforeStart();
        createPublications();
        // when
        var publicationsWhen = newsPublicationService.getPublicationsInNews(pageNumber, pageSize);

        // then
        var publications = newsPublicationRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id").reverse()));
        assertEquals(publicationsWhen.getSize(), publications.getSize());
        for (int i = 0; i < publications.getContent().size(); i++) {
            assertEquals(publications.getContent().get(i).getId(), publicationsWhen.getContent().get(i).getId());
        }
    }

    private void createBeforeStart() throws RoleNotFoundException {
        accountRepository.save(new Account(null, "dan@dam.ru", "1234", "Daniil K", null, null, null, null, null, false, roleService.getRoleByName(Role.ROLE_USER), null, null, null));
    }

    private void createPublications() {
        var savedPublication = publicationRepository.save(new PublicationEntity(null, "Первая публикация", "First", null, true,  null, accountRepository.findById(1L).get(), null, null, null));
        newsPublicationRepository.save(new NewsPublicationEntity(savedPublication.getId(), savedPublication));

        savedPublication = publicationRepository.save(new PublicationEntity(null, "Вторая публикация", "Second",null, true,  null, accountRepository.findById(1L).get(), null, null, null));
        newsPublicationRepository.save(new NewsPublicationEntity(savedPublication.getId(), savedPublication));

        savedPublication = publicationRepository.save(new PublicationEntity(null, "Третья публикация", "Third",null, true,  null, accountRepository.findById(1L).get(), null, null, null));
        newsPublicationRepository.save(new NewsPublicationEntity(savedPublication.getId(), savedPublication));
    }
}