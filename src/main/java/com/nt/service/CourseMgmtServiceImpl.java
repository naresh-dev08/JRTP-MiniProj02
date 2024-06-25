package com.nt.service;

import com.nt.model.SearchInputs;
import com.nt.model.SearchResult;
import com.nt.repo.ICourseDetailsRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CourseMgmtServiceImpl implements ICourseMgmtService{

    @Autowired
    public ICourseDetailsRepo iCourseDetailsRepo;


    @Override
    public Set<String> showAllCourseCategories() {
        return null;
    }

    @Override
    public Set<String> showAllFacultyNames() {
        return null;
    }

    @Override
    public Set<String> showAllTaringModes() {
        return null;
    }

    @Override
    public List<SearchResult> showCoursesByFilters(SearchInputs inputs) {
        return null;
    }

    @Override
    public void generateExcelReport(SearchInputs inputs, HttpServletResponse httpServletResponse) {

    }

    @Override
    public void generatePDFReport(SearchInputs inputs, HttpServletResponse httpServletResponse) {

    }
}
