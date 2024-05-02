package jp.co.csii.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.csii.entity.DVDEntity;
import jp.co.csii.repository.DVDRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DVDServiceTest {

    @Autowired
    private DVDService dVDService;

    @Autowired
    private DVDRepository dVDRepository;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    public void testSelectDVDdata() {
        DVDEntity entity;

        List<DVDEntity> dvdList = dVDService.findByName("");

        assertThat((dvdList.size()), is(4));

        entity = dvdList.get(0);
        assertThat(entity.getDvdId(), is(1));
        assertThat(entity.getDvdname(), is("test1"));
        assertThat(entity.getBorrowedDate(), is(LocalDate.of(2023, 12, 01)));
        assertThat(entity.getBorrowedCount(), is(1));
        assertThat(entity.getStatus(), is("0"));

        entity = dvdList.get(1);
        assertThat(entity.getDvdId(), is(2));
        assertThat(entity.getDvdname(), is("test2"));
        assertThat(entity.getBorrowedDate(), is(LocalDate.of(2023, 12, 02)));
        assertThat(entity.getBorrowedCount(), is(2));
        assertThat(entity.getStatus(), is("0"));

        List<DVDEntity> dvdListonlyOne = dVDService.findByName("est3");

        assertThat((dvdListonlyOne.size()), is(1));

        entity = dvdListonlyOne.get(0);
        assertThat(entity.getDvdId(), is(3));
        assertThat(entity.getDvdname(), is("test3"));
        assertThat(entity.getBorrowedDate(), is(LocalDate.of(2023, 12, 03)));
        assertThat(entity.getBorrowedCount(), is(3));
        assertThat(entity.getStatus(), is("0"));

    }

    @Test
    public void testInsertDVDdata() {
        DVDEntity entity = new DVDEntity();

        entity.setDvdname("ttt01");
        entity.setIns_dt(LocalDate.now());
        entity.setIns_by("Jim");

        dVDService.insertDVD(entity);

        List<DVDEntity> dvdList = dVDService.findByName("");

        DVDEntity entityResult = new DVDEntity();

        entityResult = dvdList.get(dvdList.size() - 1);

        assertThat((dvdList.size()), is(5));

        assertThat(entityResult.getDvdname(), is("ttt01"));

    }

    @Test
    public void test_SelectById() {
        DVDEntity entity;

        List<DVDEntity> dvdList = dVDService.findById(1);

        assertThat((dvdList.size()), is(1));

        entity = dvdList.get(0);
        assertThat(entity.getDvdId(), is(1));
        assertThat(entity.getDvdname(), is("test1"));
        assertThat(entity.getBorrowedDate(), is(LocalDate.of(2023, 12, 01)));
        assertThat(entity.getBorrowedCount(), is(1));
        assertThat(entity.getStatus(), is("0"));

    }

    @Test
    public void test_deletetById() {
        DVDEntity entity;

        dVDService.deletebyId(1);

        List<DVDEntity> dvdList = dVDService.findById(1);

        assertThat((dvdList.size()), is(0));

        List<DVDEntity> lsbyName = dVDService.findByName("");

        assertThat((lsbyName.size()), is(3));

        entity = lsbyName.get(0);
        assertThat(entity.getDvdId(), is(2));
        assertThat(entity.getDvdname(), is("test2"));
        assertThat(entity.getBorrowedDate(), is(LocalDate.of(2023, 12, 02)));
        assertThat(entity.getBorrowedCount(), is(2));
        assertThat(entity.getStatus(), is("0"));

    }

    @Test
    public void test_borrowById() {

        DVDEntity entity = new DVDEntity();
        entity.setDvdId(1);
        entity.setBorrowedDate(LocalDate.of(2024, 01, 02));
        entity.setBorrowedCount(2);

        dVDService.borrowById(entity);

        List<DVDEntity> dvdList = dVDService.findById(1);

        assertThat((dvdList.size()), is(1));
        assertThat((dvdList.get(0).getBorrowedDate()), is(LocalDate.of(2024, 01, 02)));
        assertThat((dvdList.get(0).getBorrowedCount()), is(2));
        assertThat((dvdList.get(0).getUpd_by()), is("shop"));


    }

}
