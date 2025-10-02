package com.persa.PERSA.ViewController;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.persa.PERSA.models.Permission;
import com.persa.PERSA.repository.CourseRepository;
import com.persa.PERSA.repository.PermissionRepository;
import com.persa.PERSA.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportsView {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CourseRepository courseRepository;

    private static final BaseColor PRIMARY_COLOR = new BaseColor(39, 174, 96);
    private static final BaseColor SECONDARY_COLOR = new BaseColor(30, 132, 73);
    private static final BaseColor HEADER_COLOR = new BaseColor(39, 174, 96);
    private static final BaseColor LIGHT_GREEN = new BaseColor(200, 230, 201);
    private static final BaseColor WHITE = BaseColor.WHITE;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", usersRepository.findByRoleId(3L));
        model.addAttribute("courses", courseRepository.findAll());
        return "reports/index";
    }


    private void addFooter(PdfWriter writer) {
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                Phrase footer = new Phrase("Página " + writer.getPageNumber() + " – PERSA 1.0",
                        FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.GRAY));
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        footer,
                        (document.right() - document.left()) / 2 + document.leftMargin(),
                        document.bottom() - 10, 0);
            }
        });
    }

    private void addHeader(Document document, String title, String subtitle) throws DocumentException {
        try {
            Image logoSena = Image.getInstance(new ClassPathResource("static/assets/img/sena-logo.png").getURL());
            logoSena.scaleAbsolute(70, 70);
            logoSena.setAbsolutePosition(40, 750);
            document.add(logoSena);

            Image logoPersa = Image.getInstance(new ClassPathResource("static/assets/img/persa-logo.png").getURL());
            logoPersa.scaleAbsolute(200, 60);
            logoPersa.setAlignment(Image.ALIGN_CENTER);
            Paragraph logoContainer = new Paragraph();
            logoContainer.setAlignment(Element.ALIGN_CENTER);
            logoContainer.add(logoPersa);
            document.add(logoContainer);

        } catch (Exception e) {

        }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, PRIMARY_COLOR);
        Paragraph titleParagraph = new Paragraph(title, titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(12);
        document.add(titleParagraph);

        LineSeparator line = new LineSeparator();
        line.setLineColor(PRIMARY_COLOR);
        line.setLineWidth(2);
        document.add(new Chunk(line));
        document.add(new Paragraph(" "));

        if (subtitle != null && !subtitle.isEmpty()) {
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, SECONDARY_COLOR);
            Paragraph subtitleParagraph = new Paragraph(subtitle, subtitleFont);
            subtitleParagraph.setAlignment(Element.ALIGN_LEFT);
            subtitleParagraph.setSpacingAfter(8);
            document.add(subtitleParagraph);
        }

        Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
        Paragraph dateParagraph = new Paragraph(
                "Generado el: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                dateFont
        );
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        dateParagraph.setSpacingAfter(15);
        document.add(dateParagraph);
    }

    private void addNoDataMessage(Document document, String message) throws DocumentException {
        Paragraph noData = new Paragraph(message);
        noData.setAlignment(Element.ALIGN_CENTER);
        noData.setSpacingBefore(20);
        document.add(noData);
    }

    private PdfPCell createHeaderCell(String text) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
        cell.setBackgroundColor(HEADER_COLOR);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        return cell;
    }

    private PdfPCell createDataCell(String text, boolean alternate) {
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
        PdfPCell cell = new PdfPCell(new Phrase(text, cellFont));
        cell.setBackgroundColor(alternate ? LIGHT_GREEN : WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }

    @PostMapping("/epba")
    public void exportByApprentice(
            @RequestParam("apprentice_id") Long apprenticeId,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Permisos_Aprendiz.pdf");

        var apprentice = usersRepository.findById(apprenticeId)
                .orElseThrow(() -> new RuntimeException("Aprendiz no encontrado"));

        if (apprentice.getRole() == null || apprentice.getRole().getId() != 3) {
            response.sendError(403, "El usuario no tiene permisos para este reporte");
            return;
        }

        List<Permission> permissionList = permissionRepository.findByApprenticeId(apprenticeId);
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            addFooter(writer);
            document.open();

            String apprenticeInfo = "Aprendiz: " + apprentice.getFullname() +
                                    " | Documento: " + apprentice.getDocument();

            addHeader(document, "REPORTE DE PERMISOS POR APRENDIZ", apprenticeInfo);

            if (permissionList.isEmpty()) {
                addNoDataMessage(document, "No hay permisos registrados para este aprendiz.");
            } else {
                PdfPTable table = new PdfPTable(7);
                table.setWidthPercentage(105);
                table.setWidths(new float[]{12f, 8f, 8f, 18f, 12f, 12f, 12f});

                table.addCell(createHeaderCell("Fecha"));
                table.addCell(createHeaderCell("Inicio"));
                table.addCell(createHeaderCell("Fin"));
                table.addCell(createHeaderCell("Motivo"));
                table.addCell(createHeaderCell("Sede"));
                table.addCell(createHeaderCell("Tipo"));
                table.addCell(createHeaderCell("Estado"));

                int row = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Permission p : permissionList) {
                    boolean alt = row % 2 == 0;
                    table.addCell(createDataCell(p.getPermissionDate() != null ? p.getPermissionDate().format(formatter) : "N/A", alt));
                    table.addCell(createDataCell(p.getStartTime() != null ? p.getStartTime().toString() : "N/A", alt));
                    table.addCell(createDataCell(p.getEndTime() != null ? p.getEndTime().toString() : "N/A", alt));
                    table.addCell(createDataCell(p.getReasons() != null ? p.getReasons() : "N/A", alt));
                    table.addCell(createDataCell(p.getLocation() != null ? p.getLocation().getName() : "N/A", alt));
                    table.addCell(createDataCell(p.getPermissionType() != null ? p.getPermissionType().getName() : "N/A", alt));
                    table.addCell(createDataCell(p.getStatus() != null ? p.getStatus() : "N/A", alt));
                    row++;
                }
                document.add(table);
            }
        } catch (Exception e) {
            response.sendError(500, "Error al generar el PDF");
        } finally {
            document.close();
        }
    }

    @PostMapping("/epbc")
    public void exportByCourse(
            @RequestParam("course_id") Long courseId,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Permisos_Ficha.pdf");

        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<Permission> permissionList = permissionRepository.findByCourseId(courseId);
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            addFooter(writer);
            document.open();

            String info = "Ficha: " + course.getNumberGroup() +
                    " | Programa: " + (course.getCareer() != null ? course.getCareer().getName() : "N/A");

            addHeader(document, "REPORTE DE PERMISOS POR CURSO", info);

            if (permissionList.isEmpty()) {
                addNoDataMessage(document, "No hay permisos para esta ficha.");
            } else {
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(105);
                table.setWidths(new float[]{15f, 20f, 15f, 30f, 20f});

                table.addCell(createHeaderCell("Fecha"));
                table.addCell(createHeaderCell("Tipo de Permiso"));
                table.addCell(createHeaderCell("Ubicación"));
                table.addCell(createHeaderCell("Motivo"));
                table.addCell(createHeaderCell("Aprendiz"));

                int row = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Permission p : permissionList) {
                    boolean alt = row % 2 == 0;
                    table.addCell(createDataCell(p.getPermissionDate() != null ? p.getPermissionDate().format(formatter) : "N/A", alt));
                    table.addCell(createDataCell(p.getPermissionType() != null ? p.getPermissionType().getName() : "N/A", alt));
                    table.addCell(createDataCell(p.getLocation() != null ? p.getLocation().getName() : "N/A", alt));
                    table.addCell(createDataCell(p.getReasons() != null ? p.getReasons() : "N/A", alt));
                    table.addCell(createDataCell(p.getApprentice() != null ? p.getApprentice().getFullname() : "N/A", alt));
                    row++;
                }
                document.add(table);
            }
        } catch (Exception e) {
            response.sendError(500, "Error al generar el PDF");
        } finally {
            document.close();
        }
    }


    @PostMapping("/epbdr")
    public void exportByDateRange(
            @RequestParam("date1") String date1,
            @RequestParam("date2") String date2,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Permisos_RangoFechas.pdf");

        try {
            LocalDate start = LocalDate.parse(date1);
            LocalDate end = LocalDate.parse(date2);

            List<Permission> permissionList = permissionRepository.findByPermissionDateBetween(start, end);
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            addFooter(writer);
            document.open();

            String info = "Desde: " + start.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    " | Hasta: " + end.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            addHeader(document, "REPORTE DE PERMISOS POR RANGO DE FECHAS", info);

            if (permissionList.isEmpty()) {
                addNoDataMessage(document, "No hay permisos en el rango especificado.");
            } else {
                PdfPTable table = new PdfPTable(7);
                table.setWidthPercentage(105);
                table.setWidths(new float[]{12f, 18f, 14f, 13f, 14f, 10f, 12f});

                table.addCell(createHeaderCell("Fecha"));
                table.addCell(createHeaderCell("Motivo"));
                table.addCell(createHeaderCell("Aprendiz"));
                table.addCell(createHeaderCell("Documento"));
                table.addCell(createHeaderCell("Programa"));
                table.addCell(createHeaderCell("Estado"));
                table.addCell(createHeaderCell("Sede"));

                int row = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                for (Permission p : permissionList) {
                    boolean alt = row % 2 == 0;
                    table.addCell(createDataCell(p.getPermissionDate() != null ? p.getPermissionDate().format(formatter) : "N/A", alt));
                    table.addCell(createDataCell(p.getReasons() != null ? p.getReasons() : "N/A", alt));
                    table.addCell(createDataCell(p.getApprentice() != null ? p.getApprentice().getFullname() : "N/A", alt));
                    table.addCell(createDataCell(p.getApprentice() != null ? String.valueOf(p.getApprentice().getDocument()) : "N/A", alt));

                    String program = (p.getApprentice() != null &&
                            p.getApprentice().getCourse() != null &&
                            p.getApprentice().getCourse().getCareer() != null)
                            ? p.getApprentice().getCourse().getCareer().getName() : "N/A";

                    table.addCell(createDataCell(program, alt));
                    table.addCell(createDataCell(p.getStatus() != null ? p.getStatus() : "N/A", alt));
                    table.addCell(createDataCell(p.getLocation() != null ? p.getLocation().getName() : "N/A", alt));
                    row++;
                }
                document.add(table);
            }

            document.close();
        } catch (DateTimeParseException e) {
            response.sendError(400, "Formato de fecha inválido");
        } catch (Exception e) {
            response.sendError(500, "Error al generar el PDF");
        }
    }
}