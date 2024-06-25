package com.nt.service;

import com.nt.model.SearchInputs;
import com.nt.model.SearchResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

public interface ICourseMgmtService {

    public Set<String> showAllCourseCategories();
    public Set<String> showAllFacultyNames();
    public Set<String> showAllTaringModes();

    public List<SearchResult> showCoursesByFilters(SearchInputs inputs);

    public void generateExcelReport(SearchInputs inputs, HttpServletResponse httpServletResponse);
    public void generatePDFReport(SearchInputs inputs, HttpServletResponse httpServletResponse);
}
