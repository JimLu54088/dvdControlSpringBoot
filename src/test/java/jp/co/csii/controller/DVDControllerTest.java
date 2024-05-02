package jp.co.csii.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import jp.co.csii.entity.DVDEntity;
import jp.co.csii.service.DVDService;

@SpringBootTest
class DVDControllerTest {

    @Spy
    @InjectMocks
    private DVDController controller;

    @Mock
    private DVDService service;

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
    void test_checkifdatafull_full() {


        List<DVDEntity> DVDEntityList = generateModelList(10);

        when(service.findByName(any())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.checkifdatafull(model);

        assertEquals("dvdkanri", result);
    }

    @Test
    void test_checkifdatafull_notfull() {
        List<DVDEntity> DVDEntityList = generateModelList(1);

        when(service.findByName(any())).thenReturn(DVDEntityList);


        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.checkifdatafull(model);

        assertEquals("dvdhinki", result);

    }


    @Test
    void test_search() {
        List<DVDEntity> DVDEntityList = generateModelList(1);

        when(service.findByName(any())).thenReturn(DVDEntityList);


        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.search("", model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_getsubject() {
        List<DVDEntity> DVDEntityList = generateModelList(1);

        when(service.findByName(any())).thenReturn(DVDEntityList);


        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.getsubject(model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_add_null() {

        DVDEntity dvdEntity = new DVDEntity();

        dvdEntity.setDvdname("");

        Model model = Mockito.mock(Model.class);


        // 执行 controller 方法
        String result = controller.add(dvdEntity, model);

        assertEquals("dvdkanri", result);


    }

	@Test
	void test_add_includingSpace() {

		DVDEntity dvdEntity = new DVDEntity();

		dvdEntity.setDvdname("trick ");

		Model model = Mockito.mock(Model.class);


		// 执行 controller 方法
		String result = controller.add(dvdEntity, model);

		assertEquals("dvdkanri", result);


	}

	@Test
	void test_add_includingSpace2() {

		DVDEntity dvdEntity = new DVDEntity();

		dvdEntity.setDvdname(" trick");

		Model model = Mockito.mock(Model.class);


		// 执行 controller 方法
		String result = controller.add(dvdEntity, model);

		assertEquals("dvdkanri", result);


	}

	@Test
	void test_add_includingSpace3() {

		DVDEntity dvdEntity = new DVDEntity();

		dvdEntity.setDvdname("tr ick");

		Model model = Mockito.mock(Model.class);


		// 执行 controller 方法
		String result = controller.add(dvdEntity, model);

		assertEquals("dvdkanri", result);


	}

    @Test
    void test_add_successfully() {

        DVDEntity dvdEntity = new DVDEntity();

        dvdEntity.setDvdname("trick");

        Model model = Mockito.mock(Model.class);


        // 执行 controller 方法
        String result = controller.add(dvdEntity, model);

        assertEquals("dvdkanri", result);


    }

	@Test
	void test_checkdeleteCondition_noDataInDatabase() {


	        when(service.findByName(any())).thenReturn(null);

	        // 创建模拟的 Model 对象
	        Model model = Mockito.mock(Model.class);

	        // 执行 controller 方法
	        String result = controller.checkdeleteCondition(model);

	        assertEquals("dvdkanri", result);


	}

	@Test
	void test_checkdeleteCondition_noBorrowable() {


	        when(service.findByName(any())).thenReturn(generateModelList_noBorrowable(4));

	        // 创建模拟的 Model 对象
	        Model model = Mockito.mock(Model.class);

	        // 执行 controller 方法
	        String result = controller.checkdeleteCondition(model);

	        assertEquals("dvdkanri", result);


	}

	@Test
	void test_checkdeleteCondition_deleteSuccessfully() {


	        when(service.findByName(any())).thenReturn(generateModelList_deleteSuccessfully(4));

	        // 创建模拟的 Model 对象
	        Model model = Mockito.mock(Model.class);

	        // 执行 controller 方法
	        String result = controller.checkdeleteCondition(model);

	        assertEquals("dvdDelete", result);


	}

    @Test
    void test_borrowable_check_status_1(){

        List<DVDEntity> DVDEntityList = generateModelList_noBorrowable(3);
        when(service.findById(anyInt())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.borrowDVDbyID(DVDEntityList.get(0).getDvdId(), model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_borrowable_check_status_0(){

        List<DVDEntity> DVDEntityList = generateModelList_deleteSuccessfully(3);
        when(service.findById(anyInt())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.borrowDVDbyID(DVDEntityList.get(0).getDvdId(), model);

        assertEquals("dvdborrow", result);

    }

    @Test
    void test_borrowEntity(){

        List<DVDEntity> DVDEntityList = generateModelList_deleteSuccessfully(3);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.borrowentity(DVDEntityList.get(0), model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void back_status_0(){

        List<DVDEntity> DVDEntityList = generateModelList_deleteSuccessfully(3);
        when(service.findById(anyInt())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.backDVD(DVDEntityList.get(0).getDvdId(), model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void back_status_1(){

        List<DVDEntity> DVDEntityList = generateModelList_status1forBorrowDate(3);
        when(service.findById(anyInt())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.backDVD(DVDEntityList.get(0).getDvdId(), model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_del_outOfRangeLargerThanMAX(){

        List<DVDEntity> DVDEntityList = generateModelList_status1forBorrowDate(3);
        when(service.findByName(any())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.del(100, model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_del_outOfRangeSmallerThanMIN(){

        List<DVDEntity> DVDEntityList = generateModelList_status1forBorrowDateForSmall(20);
        when(service.findByName(any())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.del(5, model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_del_outOfRangeSmallerThanMIN_desc(){

        List<DVDEntity> DVDEntityList = generateModelList_status1forBorrowDateForSmallDESC(20);
        when(service.findByName(any())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.del(5, model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_del_notInAnyOfDataBase(){

        List<DVDEntity> DVDEntityList = generateModelList_notInAnyOfDataBase(3);
        when(service.findByName(any())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.del(1, model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_del_status1(){

        List<DVDEntity> DVDEntityList = generateModelList_status1forBorrowDate(3);
        when(service.findByName(any())).thenReturn(DVDEntityList);
        when(service.findById(anyInt())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.del(1, model);

        assertEquals("dvdkanri", result);

    }

    @Test
    void test_del_status0(){

        List<DVDEntity> DVDEntityList = generateModelList_deleteSuccessfully(3);
        when(service.findByName(any())).thenReturn(DVDEntityList);
        when(service.findById(anyInt())).thenReturn(DVDEntityList);

        // 创建模拟的 Model 对象
        Model model = Mockito.mock(Model.class);

        // 执行 controller 方法
        String result = controller.del(1, model);

        assertEquals("dvdkanri", result);

    }


    private List<DVDEntity> generateModelList(int count) {
        List<DVDEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DVDEntity resultModel = new DVDEntity();

            resultModel.setDvdId(i);
            resultModel.setDvdname("test");
            resultModel.setIns_dt(LocalDate.now());
            resultModel.setIns_by("Jim");

            list.add(resultModel);

        }
        return list;

    }

    private List<DVDEntity> generateModelList_noBorrowable(int count) {
        List<DVDEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DVDEntity resultModel = new DVDEntity();

            resultModel.setDvdId(i);
            resultModel.setDvdname("test");
            resultModel.setStatus("1");
            resultModel.setIns_dt(LocalDate.now());
            resultModel.setIns_by("Jim");

            list.add(resultModel);

        }
        return list;

    }

    private List<DVDEntity> generateModelList_deleteSuccessfully(int count) {
        List<DVDEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DVDEntity resultModel = new DVDEntity();

            resultModel.setDvdId(i);
            resultModel.setDvdname("test");
            resultModel.setStatus("0");
            resultModel.setIns_dt(LocalDate.now());
            resultModel.setIns_by("Jim");

            list.add(resultModel);

        }
        return list;

    }

    private List<DVDEntity> generateModelList_status1forBorrowDate(int count) {
        List<DVDEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DVDEntity resultModel = new DVDEntity();

            resultModel.setDvdId(i);
            resultModel.setDvdname("test");
            resultModel.setStatus("1");
            resultModel.setBorrowedDate(LocalDate.of(2023,10,01));
            resultModel.setIns_dt(LocalDate.now());
            resultModel.setIns_by("Jim");

            list.add(resultModel);

        }
        return list;

    }

    private List<DVDEntity> generateModelList_status1forBorrowDateForSmall(int count) {
        List<DVDEntity> list = new ArrayList<>();
        for (int i = 10; i < count; i++) {
            DVDEntity resultModel = new DVDEntity();

            resultModel.setDvdId(i);
            resultModel.setDvdname("test");
            resultModel.setStatus("1");
            resultModel.setBorrowedDate(LocalDate.of(2023,10,01));
            resultModel.setIns_dt(LocalDate.now());
            resultModel.setIns_by("Jim");

            list.add(resultModel);

        }
        return list;

    }

    private List<DVDEntity> generateModelList_status1forBorrowDateForSmallDESC(int count) {
        List<DVDEntity> list = new ArrayList<>();
        for (int i = count - 1; i >= 10; i--) {
            DVDEntity resultModel = new DVDEntity();

            resultModel.setDvdId(i);
            resultModel.setDvdname("test");
            resultModel.setStatus("1");
            resultModel.setBorrowedDate(LocalDate.of(2023,10,01));
            resultModel.setIns_dt(LocalDate.now());
            resultModel.setIns_by("Jim");

            list.add(resultModel);

        }
        return list;

    }


    private List<DVDEntity> generateModelList_notInAnyOfDataBase(int count) {
        List<DVDEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i += 2) {
            DVDEntity resultModel = new DVDEntity();

            resultModel.setDvdId(i);
            resultModel.setDvdname("test");
            resultModel.setStatus("1");
            resultModel.setBorrowedDate(LocalDate.of(2023,10,01));
            resultModel.setIns_dt(LocalDate.now());
            resultModel.setIns_by("Jim");

            list.add(resultModel);

        }
        return list;

    }

}
