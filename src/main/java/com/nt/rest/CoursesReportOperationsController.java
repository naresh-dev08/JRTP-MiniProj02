package com.nt.rest;

import com.nt.model.SearchInputs;
import com.nt.model.SearchResult;
import com.nt.service.ICourseMgmtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reporting-api")
public class CoursesReportOperationsController {

    @Autowired
    public ICourseMgmtService courseService;

    @GetMapping("/course-Categories")
    public ResponseEntity<?> fetchCourseCategories() {
        try {
            //use service
            Set<String> courseCategoriesInfo = courseService.showAllCourseCategories();
            return new ResponseEntity<>(courseCategoriesInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course-faculties")
    public ResponseEntity<?> fetchFaculties() {
        try {
            //use service
            Set<String> facultyNamesInfo = courseService.showAllFacultyNames();
            return new ResponseEntity<>(facultyNamesInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/course-trainingModes")
    public ResponseEntity<?> fetchTrainingModes() {
        try {
            //use service
            Set<String> trainingModesInfo = courseService.showAllTaringModes();
            return new ResponseEntity<>(trainingModesInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<?> fetchCoursesByFilters(@RequestBody SearchInputs inputs) {
        try {
            //use service
            List<SearchResult> searchResultsList = courseService.showCoursesByFilters(inputs);
            return new ResponseEntity<>(searchResultsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pdf-report")
    public void showPDFReport(@RequestBody SearchInputs inputs, HttpServletResponse httpServletResponse) {
        try {
            //set the Response content type
            httpServletResponse.setContentType("application/pdf");
            //set the content-disposition header to response content going to browser as downloadable file
            httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=courses.pdf");
            //use service
            courseService.generatePDFReport(inputs, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//method() end

    @PostMapping("/excel-report")
    public void showExcelReport(@RequestBody SearchInputs inputs, HttpServletResponse httpServletResponse) {
        try {
            //set the Response content type
            httpServletResponse.setContentType("application/vnd.ms-excel");
            //set the content-disposition header to response content going to browser as downloadable file
            httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=courses.xls");
            //use service
            courseService.generateExcelReport(inputs, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//method() end

    @GetMapping("/all-pdf-report")
    public void showPDFReportAllData(HttpServletResponse httpServletResponse) {
        try {
            //set the Response content type
            httpServletResponse.setContentType("application/pdf");
            //set the content-disposition header to response content going to browser as downloadable file
            httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=courses.pdf");
            //use service
            courseService.generateAllDataPDFReport(httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//method() end

    @GetMapping("/all-excel-report")
    public void showExcelReportAllData(HttpServletResponse httpServletResponse) {
        try {
            //set the Response content type
            httpServletResponse.setContentType("application/vnd.ms-excel");
            //set the content-disposition header to response content going to browser as downloadable file
            httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=courses.xls");
            //use service
            courseService.generateAllDataExcelReport(httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//method() end

}
