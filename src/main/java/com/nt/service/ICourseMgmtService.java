package com.nt.service;

import com.nt.model.SearchInputs;
import com.nt.model.SearchResult;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Set;

public interface ICourseMgmtService {

    Set<String> showAllCourseCategories();
    Set<String> showAllFacultyNames();
    Set<String> showAllTaringModes();

    List<SearchResult> showCoursesByFilters(SearchInputs inputs);

    void generateExcelReport(SearchInputs inputs, HttpServletResponse httpServletResponse) throws Exception;
    void generatePDFReport(SearchInputs inputs, HttpServletResponse httpServletResponse)  throws Exception;

    void generateAllDataExcelReport(HttpServletResponse httpServletResponse) throws Exception;
    void generateAllDataPDFReport(HttpServletResponse httpServletResponse) throws Exception;
}