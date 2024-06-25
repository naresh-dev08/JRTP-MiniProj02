package com.nt.service;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.entity.CourseDetails;
import com.nt.model.SearchInputs;
import com.nt.model.SearchResult;
import com.nt.repo.ICourseDetailsRepo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service("courseService")
public class CourseMgmtServiceImpl implements ICourseMgmtService{

    @Autowired
    public ICourseDetailsRepo iCourseDetailsRepo;


    @Override
    public Set<String> showAllCourseCategories() {
        return iCourseDetailsRepo.getUniqueCourseCategories();
    }

    @Override
    public Set<String> showAllFacultyNames() {
        return iCourseDetailsRepo.getUniqueFacultyNames();
    }

    @Override
    public Set<String> showAllTaringModes() {
        return iCourseDetailsRepo.getUniqueTrainingModes();
    }

    @Override
    public List<SearchResult> showCoursesByFilters(SearchInputs inputs) {

      /*  get non-null and non-empty string values from the inputs object and prepare entity
        obj having that non-null data and also place that entity object inside example obj */

        CourseDetails courseDetails = new CourseDetails();

        String category = inputs.getCourseCategory();
//        if (category != null && !category.equals("") && category.length() != 0)
//        if (category != null && !category.isEmpty())
        if (StringUtils.hasLength(category))
            courseDetails.setCourseCategory(category);

        String facultyName=inputs.getFacultyName();
//        if (facultyName != null && !facultyName.equals("") && !facultyName.isEmpty())
//        if (facultyName != null && !facultyName.isEmpty())
        if (StringUtils.hasLength(facultyName))
            courseDetails.setFacultyName(facultyName);

        String trainingMode=inputs.getTrainingMode();
//        if (trainingMode != null && !trainingMode.equals("") && trainingMode.length() != 0)
//        if (trainingMode != null && !trainingMode.isEmpty())
        if (StringUtils.hasLength(trainingMode))
            courseDetails.setTrainingMode(trainingMode);

        LocalDateTime startDate = inputs.getStartsOn();
//        if (startDate != null)
        if (ObjectUtils.isEmpty(startDate))
            courseDetails.setStartOn(startDate);

        Example<CourseDetails> example = Example.of(courseDetails);

        //perform search operation with filters of example obj
        /*List<CourseDetails> courseDetailsList = iCourseDetailsRepo.findAll(example);
        //convert list<CourseDetails> obj to List<SearchResult> obj
        List<SearchResult> searchResultsList = new ArrayList<>();
        courseDetailsList.forEach(course -> {
            SearchResult searchResultEntity = new SearchResult();
            BeanUtils.copyProperties(course, searchResultEntity);
            searchResultsList.add(searchResultEntity);
        });*/
        return iCourseDetailsRepo.findAll(example).stream()
                .map(course -> {
                    SearchResult searchResult = new SearchResult();
                    BeanUtils.copyProperties(course, searchResult);
                    return searchResult;
                }).toList();
    }

    @Override
    public void generatePDFReport(SearchInputs inputs, HttpServletResponse httpServletResponse) throws Exception {

        //get the SearchResults
        List<SearchResult> searchResultsList = showCoursesByFilters(inputs);
        //Create Document obj (OpenPDF)
        Document document = new Document(PageSize.A4);
        //Get the PDFWriter to write the document and response obj
        PdfWriter.getInstance(document, httpServletResponse.getOutputStream());
        //Open the PDF Document
        document.open();
        //Define font for the Paragraph
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(30);
        font.setColor(Color.RED);

        //create the Paragraph having content and above font style
        Paragraph paragraph = new Paragraph("Search Report of Courses", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        //Add paragraph to document
        document.add(paragraph);

        //display search results as the pdf table
        PdfPTable pdfTable = new PdfPTable(10);
        pdfTable.setWidthPercentage(70);
        pdfTable.setWidths(new float[] {3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f});
        pdfTable.setSpacingBefore(2.0f);

        //prepare heading row cells in the pdf table
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.gray);
        pdfPCell.setPadding(5);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        cellFont.setColor(Color.BLACK);

        pdfPCell.setPhrase(new Phrase("CourseID", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("CourseName", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("CourseCategory", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("FacultyName", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("Fee", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("TrainingMode", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("CourseStatus", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("StartDate", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("Location", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("AdminContact", cellFont));
        pdfTable.addCell(pdfPCell);

        //add data cells to PdfTable
        searchResultsList.forEach(searchResult -> {
            pdfTable.addCell(String.valueOf(searchResult.getCourseID()));
            pdfTable.addCell(searchResult.getCourseName());
            pdfTable.addCell(searchResult.getCourseCategory());
            pdfTable.addCell(searchResult.getFacultyName());
            pdfTable.addCell(String.valueOf(searchResult.getFee()));
            pdfTable.addCell(searchResult.getTrainingMode());
            pdfTable.addCell(searchResult.getCourseStatus());
            pdfTable.addCell(searchResult.getStartOn().toString());
            pdfTable.addCell(searchResult.getLocation());
            pdfTable.addCell(String.valueOf(searchResult.getAdminContact()));
        });

        //add table to document
        document.add(pdfTable);
        //close the document
        document.close();


    }

    @Override
    public void generateExcelReport(SearchInputs inputs, HttpServletResponse httpServletResponse) throws Exception {

        //get the SearchResults
        List<SearchResult> searchResultsList = showCoursesByFilters(inputs);

        /* create ExcelWorkBook */
        HSSFWorkbook  workbook = new HSSFWorkbook();
        //create sheet in workbook
        HSSFSheet sheet1 = workbook.createSheet("CourseDetails");
        //Create Head Row in sheet
        HSSFRow headRow = sheet1.createRow(0);
        headRow.createCell(0).setCellValue("CourseID");
        headRow.createCell(1).setCellValue("CourseName");
        headRow.createCell(2).setCellValue("CourseCategory");
        headRow.createCell(3).setCellValue("FacultyName");
        headRow.createCell(4).setCellValue("Fee");
        headRow.createCell(5).setCellValue("TrainingMode");
        headRow.createCell(6).setCellValue("CourseStatus");
        headRow.createCell(7).setCellValue("StartDate");
        headRow.createCell(8).setCellValue("Location");
        headRow.createCell(9).setCellValue("AdminContact");

        int i =1;

/*        searchResultsList.forEach(course -> {
            SearchResult searchResult = new SearchResult();
            HSSFRow dataRow = sheet1.createRow(1);
            dataRow.createCell(0).setCellValue(course.getCourseID());
            dataRow.createCell(1).setCellValue(course.getCourseName());
            dataRow.createCell(2).setCellValue(course.getCourseCategory());
            dataRow.createCell(3).setCellValue(course.getFacultyName());
            dataRow.createCell(4).setCellValue(course.getFee());
            dataRow.createCell(5).setCellValue(course.getTrainingMode());
            dataRow.createCell(6).setCellValue(course.getCourseStatus());
            dataRow.createCell(7).setCellValue(course.getStartOn());
            dataRow.createCell(8).setCellValue(course.getLocation());
            dataRow.createCell(9).setCellValue(course.getAdminContact());
            i++;
        });  */

        for (SearchResult searchResult: searchResultsList) {
            HSSFRow dataRow = sheet1.createRow(i);
            dataRow.createCell(0).setCellValue(searchResult.getCourseID());
            dataRow.createCell(1).setCellValue(searchResult.getCourseName());
            dataRow.createCell(2).setCellValue(searchResult.getCourseCategory());
            dataRow.createCell(3).setCellValue(searchResult.getFacultyName());
            dataRow.createCell(4).setCellValue(searchResult.getFee());
            dataRow.createCell(5).setCellValue(searchResult.getTrainingMode());
            dataRow.createCell(6).setCellValue(searchResult.getCourseStatus());
            dataRow.createCell(7).setCellValue(searchResult.getStartOn());
            dataRow.createCell(8).setCellValue(searchResult.getLocation());
            dataRow.createCell(9).setCellValue(searchResult.getAdminContact());
            i++;
        }

        //get the OutputStream pointing to Response obj
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        //Write the Excel workbook data to response obj using above stream
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();

    }

    @Override
    public void generateAllDataExcelReport(HttpServletResponse httpServletResponse) throws Exception {

        /* //Get the all records from the DB Table
        List<CourseDetails> courseDetailsList = iCourseDetailsRepo.findAll();
        //Create the List of SearchResult obj
        List<SearchResult> searchResultList = new ArrayList<>();

        courseDetailsList.forEach(courseDetails -> {
            SearchResult searchResult = new SearchResult();
            BeanUtils.copyProperties(courseDetails, searchResult);
            searchResultList.add(searchResult);
        });*/

        List<SearchResult> searchResultsList = iCourseDetailsRepo.findAll().stream()
                .map(courseDetails -> {
                    SearchResult searchResult = new SearchResult();
                    // Copy properties from courseDetails to searchResult
                    BeanUtils.copyProperties(courseDetails, searchResult);
                    return searchResult;
                })  /* .collect(Collectors.toList()); */
                .toList();

        /* create ExcelWorkBook */
        HSSFWorkbook  workbook = new HSSFWorkbook();
        //create sheet in workbook
        HSSFSheet sheet1 = workbook.createSheet("CourseDetails");
        //Create Head Row in sheet
        HSSFRow headRow = sheet1.createRow(0);
        headRow.createCell(0).setCellValue("CourseID");
        headRow.createCell(1).setCellValue("CourseName");
        headRow.createCell(2).setCellValue("CourseCategory");
        headRow.createCell(3).setCellValue("FacultyName");
        headRow.createCell(4).setCellValue("Fee");
        headRow.createCell(5).setCellValue("TrainingMode");
        headRow.createCell(6).setCellValue("CourseStatus");
        headRow.createCell(7).setCellValue("StartDate");
        headRow.createCell(8).setCellValue("Location");
        headRow.createCell(9).setCellValue("AdminContact");

        int i =1;

        for (SearchResult searchResult: searchResultsList) {
            HSSFRow dataRow = sheet1.createRow(i);
            dataRow.createCell(0).setCellValue(searchResult.getCourseID());
            dataRow.createCell(1).setCellValue(searchResult.getCourseName());
            dataRow.createCell(2).setCellValue(searchResult.getCourseCategory());
            dataRow.createCell(3).setCellValue(searchResult.getFacultyName());
            dataRow.createCell(4).setCellValue(searchResult.getFee());
            dataRow.createCell(5).setCellValue(searchResult.getTrainingMode());
            dataRow.createCell(6).setCellValue(searchResult.getCourseStatus());
            dataRow.createCell(7).setCellValue(searchResult.getStartOn());
            dataRow.createCell(8).setCellValue(searchResult.getLocation());
            dataRow.createCell(9).setCellValue(searchResult.getAdminContact());
            i++;
        }

        //get the OutputStream pointing to Response obj
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        //Write the Excel workbook data to response obj using above stream
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();

    }

    @Override
    public void generateAllDataPDFReport(HttpServletResponse httpServletResponse) throws Exception {

        //get the All CourseDetail obj data  from DB and convert it into SearchResult obj
        List<SearchResult> searchResultsList = iCourseDetailsRepo.findAll().stream()
                .map(courseDetails -> {
                    SearchResult searchResult = new SearchResult();
                    // Copy properties from courseDetails to searchResult
                    BeanUtils.copyProperties(courseDetails, searchResult);
                    return searchResult;
                })  /* .collect(Collectors.toList()); */
                .toList();

        //Create Document obj (OpenPDF)
        Document document = new Document(PageSize.A4);
        //Get the PDFWriter to write the document and response obj
        PdfWriter.getInstance(document, httpServletResponse.getOutputStream());
        //Open the PDF Document
        document.open();
        //Define font for the Paragraph
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(30);
        font.setColor(Color.RED);

        //create the Paragraph having content and above font style
        Paragraph paragraph = new Paragraph("Search Report of Courses", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        //Add paragraph to document
        document.add(paragraph);

        //display search results as the pdf table
        PdfPTable pdfTable = new PdfPTable(10);
        pdfTable.setWidthPercentage(70);
        pdfTable.setWidths(new float[] {3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f});
        pdfTable.setSpacingBefore(2.0f);

        //prepare heading row cells in the pdf table
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.gray);
        pdfPCell.setPadding(5);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        cellFont.setColor(Color.BLACK);

        pdfPCell.setPhrase(new Phrase("CourseID", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("CourseName", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("CourseCategory", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("FacultyName", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("Fee", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("TrainingMode", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("CourseStatus", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("StartDate", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("Location", cellFont));
        pdfTable.addCell(pdfPCell);
        pdfPCell.setPhrase(new Phrase("AdminContact", cellFont));
        pdfTable.addCell(pdfPCell);

        //add data cells to PdfTable
        searchResultsList.forEach(searchResult -> {
            pdfTable.addCell(String.valueOf(searchResult.getCourseID()));
            pdfTable.addCell(searchResult.getCourseName());
            pdfTable.addCell(searchResult.getCourseCategory());
            pdfTable.addCell(searchResult.getFacultyName());
            pdfTable.addCell(String.valueOf(searchResult.getFee()));
            pdfTable.addCell(searchResult.getTrainingMode());
            pdfTable.addCell(searchResult.getCourseStatus());
            pdfTable.addCell(searchResult.getStartOn().toString());
            pdfTable.addCell(searchResult.getLocation());
            pdfTable.addCell(String.valueOf(searchResult.getAdminContact()));
        });

        //add table to document
        document.add(pdfTable);
        //close the document
        document.close();
    }
}
