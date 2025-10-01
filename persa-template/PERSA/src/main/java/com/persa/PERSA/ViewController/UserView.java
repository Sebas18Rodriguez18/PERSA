package com.persa.PERSA.ViewController;

import com.persa.PERSA.models.User;
import com.persa.PERSA.repository.RoleRepository;
import com.persa.PERSA.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserView {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final BaseColor PRIMARY_COLOR = new BaseColor(39, 174, 96);
    private static final BaseColor SECONDARY_COLOR = new BaseColor(30, 132, 73);
    private static final BaseColor HEADER_COLOR = new BaseColor(39, 174, 96);
    private static final BaseColor LIGHT_GREEN = new BaseColor(200, 230, 201);
    private static final BaseColor WHITE = BaseColor.WHITE;

    @GetMapping
    public String showAll(Model model){
        model.addAttribute("users", usersRepository.findAll());
        return "user/index";
    }

    @GetMapping("/apprentices")
    public String showApprentices(Model model) {
        model.addAttribute("users", usersRepository.findByRole_NameIgnoreCase("APRENDIZ"));
        return "user/index";
    }

    @GetMapping("/instructors")
    public String showInstructors(Model model) {
        model.addAttribute("users", usersRepository.findByRole_NameIgnoreCase("INSTRUCTOR"));
        return "user/index";
    }

    @GetMapping("/guards")
    public String showGuards(Model model) {
        model.addAttribute("users", usersRepository.findByRole_NameIgnoreCase("GUARDIA"));
        return "user/index";
    }

    @GetMapping("/export/pdf")
    public void exportPdf(@RequestParam(value = "role", required = false) String roleName,
                        HttpServletResponse response) throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Usuarios.pdf");

        List<User> users;
        String subtitle = "Todos los usuarios";

        if(roleName != null && !roleName.isEmpty()){
            users = usersRepository.findByRole_NameIgnoreCase(roleName);
            subtitle = "Filtrado por rol: " + roleName;
        } else {
            users = usersRepository.findAll();
        }

        Document document = new Document(PageSize.A4.rotate(), 40, 40, 40, 40);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        addFooter(writer);
        document.open();
        addHeader(document, "REPORTE DE USUARIOS", subtitle);

        if(users.isEmpty()){
            addNoDataMessage(document, "No hay usuarios para mostrar.");
        } else {
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(105);
            table.setWidths(new float[]{15f, 30f, 25f, 15f, 15f});

            table.addCell(createHeaderCell("Documento"));
            table.addCell(createHeaderCell("Nombre completo"));
            table.addCell(createHeaderCell("Correo"));
            table.addCell(createHeaderCell("Rol"));
            table.addCell(createHeaderCell("Estado"));

            int row = 0;
            for(User u : users){
                boolean alt = row % 2 == 0;
                table.addCell(createDataCell(u.getDocument() != null ? u.getDocument().toString() : "N/A", alt));
                table.addCell(createDataCell(u.getFullname() != null ? u.getFullname() : "N/A", alt));
                table.addCell(createDataCell(u.getEmail() != null ? u.getEmail() : "N/A", alt));
                table.addCell(createDataCell(u.getRole() != null ? u.getRole().getName() : "Sin rol", alt));
                table.addCell(createDataCell(u.getStatus() != null ? u.getStatus() : "N/A", alt));
                row++;
            }

            document.add(table);
        }

        document.close();
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
            logoSena.setAbsolutePosition(40, 520);
            document.add(logoSena);

            Image logoPersa = Image.getInstance(new ClassPathResource("static/assets/img/persa-logo.png").getURL());
            logoPersa.scaleAbsolute(200, 60);
            logoPersa.setAlignment(Image.ALIGN_CENTER);
            Paragraph logoContainer = new Paragraph();
            logoContainer.setAlignment(Element.ALIGN_CENTER);
            logoContainer.add(logoPersa);
            document.add(logoContainer);
        } catch (Exception e) { }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, PRIMARY_COLOR);
        Paragraph titleParagraph = new Paragraph(title, titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(12);
        document.add(titleParagraph);

        LineSeparator line = new LineSeparator();
        line.setLineColor(PRIMARY_COLOR);
        line.setLineWidth(2);
        document.add(new Chunk(line));
        document.add(new Paragraph(" "));

        if(subtitle != null && !subtitle.isEmpty()){
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

    private PdfPCell createHeaderCell(String text){
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
        cell.setBackgroundColor(HEADER_COLOR);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        return cell;
    }

    private PdfPCell createDataCell(String text, boolean alternate){
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
        PdfPCell cell = new PdfPCell(new Phrase(text, cellFont));
        cell.setBackgroundColor(alternate ? LIGHT_GREEN : WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }

    @GetMapping("/export/excel")
    public void exportExcel(@RequestParam(value = "role", required = false) String roleName,
                            HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Usuarios.xlsx");

        List<User> users;
        String sheetTitle = "Usuarios";
        if(roleName != null && !roleName.isEmpty()){
            users = usersRepository.findByRole_NameIgnoreCase(roleName);
            sheetTitle += " - " + roleName.toUpperCase();
        } else {
            users = usersRepository.findAll();
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetTitle);

        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Documento", "Nombre completo", "Correo", "Rol", "Estado"};
        for(int i=0;i<headers.length;i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        int rowNum = 1;
        for(User u : users){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(u.getDocument() != null ? u.getDocument().toString() : "N/A");
            row.createCell(1).setCellValue(u.getFullname() != null ? u.getFullname() : "N/A");
            row.createCell(2).setCellValue(u.getEmail() != null ? u.getEmail() : "N/A");
            row.createCell(3).setCellValue(u.getRole() != null ? u.getRole().getName() : "Sin rol");
            row.createCell(4).setCellValue(u.getStatus() != null ? u.getStatus() : "N/A");

            for(int i=0;i<5;i++){
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        for(int i=0;i<5;i++) sheet.autoSizeColumn(i);
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/form")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "user/form";
    }

    @GetMapping("/form/{id}")
    public String edit(@PathVariable Long id, Model model) {
        User user = usersRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "user/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") User user, RedirectAttributes ra) {
        usersRepository.save(user);
        ra.addFlashAttribute("success", "Usuario guardado correctamente");
        return "redirect:/user";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        usersRepository.deleteById(id);
        ra.addFlashAttribute("success", "Usuario eliminado correctamente");
        return "redirect:/user";
    }
}