//package com.punko.service;
//
//import com.punko.ResidentService;
//import com.punko.dao.jdbc.ResidentDaoJdbc;
//import com.punko.model.Apartment;
//import com.punko.model.Resident;
//import com.punko.testdb.SpringJdbcConfig;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@SpringBootTest
//@Import({ResidentDaoJdbc.class, ResidentServiceImpl.class})
//@ContextConfiguration(classes = SpringJdbcConfig.class)
//@PropertySource({"classpath:daoResident.properties"})
//@ComponentScan(basePackages = {"com.punko.dao", "com.punko.testdb"})
//@Transactional
//public class ResidentServiceTestIT {
//
//    @Autowired
//    ResidentService residentService;
//
//    @Test
//    public void getAllResidentTest() {
//        List<Resident> residentList = residentService.findAll();
//        Assertions.assertNotNull(residentList);
//        Assertions.assertTrue(residentList.size() > 0);
//    }
//
////    @Test
////    public void createResidentTest() {
////        List<Resident> residentList = residentService.findAll();
////        Assertions.assertTrue(residentList.size() > 0);
////
////        LocalDate arrivalTime = LocalDate.of(2020, 03, 20);
////        LocalDate departureTime = LocalDate.of(2021, 05, 21);
//////        Date arrivalTime = Date.valueOf("2020-02-12");
//////        Date departureTime = Date.valueOf("2020-04-25");
////        Resident resident = new Resident("Name", "Surname", "test@test", arrivalTime, departureTime, 102);
////        residentService.create(resident);
////
////        List<Resident> modifiedList = residentService.findAll();
////        Assertions.assertNotNull(modifiedList);
////        Assertions.assertTrue(modifiedList.size() > 0);
////        Assertions.assertEquals(residentList.size() + 1, modifiedList.size());
////    }
////
////    @Test
////    public void isResidentEmailUniqueTest() {
////        List<Resident> residentList = residentService.findAll();
////        Assertions.assertTrue(residentList.size() > 0);
////
////        LocalDate arrivalTime = LocalDate.of(2020, 03, 20);
////        LocalDate departureTime = LocalDate.of(2021, 05, 21);
////        Resident resident = new Resident("Name", "Surname", residentList.get(0).getEmail(), arrivalTime, departureTime, 102);
////        Assertions.assertThrows(IllegalArgumentException.class, () -> residentService.create(resident));
////
////    }
////
////    @Test
////    public void getAllApartmentNumberTest() {
////        List<Apartment> residentList = residentService.getAllApartmentNumber();
////        Assertions.assertNotNull(residentList);
////        Assertions.assertTrue(residentList.size() > 0);
////    }
////
////    @Test
////    public void findByIdResidentTest() {
////        List<Resident> residentList = residentService.findAll();
////        Assertions.assertTrue(residentList.size() > 0);
////
////        int id = residentList.get(0).getResidentId();
////        Resident resident = residentService.findById(id);
////
////        Assertions.assertNotNull(resident);
////        Assertions.assertEquals(residentList.get(0), resident);
////    }
////
////    @Test
////    public void deleteResidentTest() {
////        List<Resident> residentList = residentService.findAll();
////        Assertions.assertTrue(residentList.size() > 0);
////
////        residentService.delete(residentList.get(0).getResidentId());
////        List<Resident> afterDeleteList = residentService.findAll();
////
////        Assertions.assertEquals(residentList.size(), afterDeleteList.size() + 1);
////
////    }
//
//}
