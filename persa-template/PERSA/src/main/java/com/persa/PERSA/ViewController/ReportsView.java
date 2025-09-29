package com.persa.PERSA.ViewController;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.persa.PERSA.repository.PermissionRepository;
import com.persa.PERSA.models.Permission;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportsView {

    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping
    public String index() {
        return "reports/index";
    }

    @PostMapping("/epba")
    public void export_permissions_by_apprentice(
            @RequestParam("apprentice_id") Long apprenticeId,
            HttpServletResponse response) throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=PermissionsByApprentice.pdf");

        List<Permission> permissionList = permissionRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Reporte por aprendiz"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("Fecha de permiso");
        table.addCell("Hora de inicio");
        table.addCell("Hora de fin");
        table.addCell("Motivo");
        table.addCell("Sede");
        table.addCell("Tipo de permiso");
        table.addCell("Estado");

        for (Permission p : permissionList) {
            table.addCell(p.getPermissionDate() != null ? p.getPermissionDate().toString() : "N/A");
            table.addCell(p.getStartTime() != null ? p.getStartTime().toString() : "N/A");
            table.addCell(p.getEndTime() != null ? p.getEndTime().toString() : "N/A");
            table.addCell(p.getReasons() != null ? p.getReasons() : "N/A");
            table.addCell(p.getLocation() != null ? p.getLocation().getName() : "N/A");
            table.addCell(p.getPermissionType() != null ? p.getPermissionType().getName() : "N/A");
            table.addCell(p.getStatus() != null ? p.getStatus() : "N/A");
        }

        document.add(table);
        document.close();
    }

    @PostMapping("/epbc")
    public void export_permissions_by_course(
            @RequestParam("course_id") Long courseId,
            HttpServletResponse response) throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=PermissionsByCourse.pdf");

        List<Permission> permissionList = permissionRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Reporte por curso"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("Fecha");
        table.addCell("Tipo de permiso");
        table.addCell("Ubicación");
        table.addCell("Motivo");
        table.addCell("Curso");

        for (Permission p : permissionList) {
            table.addCell(p.getPermissionDate() != null ? p.getPermissionDate().toString() : "N/A");
            table.addCell(p.getPermissionType() != null ? p.getPermissionType().getName() : "N/A");
            table.addCell(p.getLocation() != null ? p.getLocation().getName() : "N/A");
            table.addCell(p.getReasons() != null ? p.getReasons() : "N/A");
            if (p.getApprentice() != null && p.getApprentice().getCourse() != null) {
                table.addCell(p.getApprentice().getCourse().getCareer().getName());
            } else {
                table.addCell("N/A");
            }
        }

        document.add(table);
        document.close();
    }

    @PostMapping("/epbdr")
    public void export_permissions_by_date_range(
            @RequestParam("date1") String date1,
            @RequestParam("date2") String date2,
            HttpServletResponse response) throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=PermissionsByDateRange.pdf");

        LocalDate start = LocalDate.parse(date1);
        LocalDate end = LocalDate.parse(date2);

        List<Permission> permissionList = permissionRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Reporte por rango de fechas"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        table.addCell("Fecha de permiso");
        table.addCell("Motivo");
        table.addCell("Aprendiz");
        table.addCell("Documento");
        table.addCell("Programa");
        table.addCell("Estado");
        table.addCell("Sede");

        for (Permission p : permissionList) {
            table.addCell(p.getPermissionDate() != null ? p.getPermissionDate().toString() : "N/A");
            table.addCell(p.getReasons() != null ? p.getReasons() : "N/A");
            table.addCell(p.getApprentice() != null ? p.getApprentice().getFullname() : "N/A");
            table.addCell(p.getApprentice() != null ? String.valueOf(p.getApprentice().getDocument()) : "N/A");
            if (p.getApprentice() != null && p.getApprentice().getCourse() != null) {
                table.addCell(p.getApprentice().getCourse().getCareer().getName());
            } else {
                table.addCell("N/A");
            }
            table.addCell(p.getStatus() != null ? p.getStatus() : "N/A");
            table.addCell(p.getLocation() != null ? p.getLocation().getName() : "N/A");
        }

        document.add(table);
        document.close();
    }
}