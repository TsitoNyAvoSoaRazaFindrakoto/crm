package site.easy.to.build.crm.extension.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.extension.exception.LocalValidationException;
import site.easy.to.build.crm.extension.exception.PostPersistException;
import site.easy.to.build.crm.extension.service.quickActions.QuickActionsService;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/quick-actions/database")
public class QuickActionsController {
  private final QuickActionsService quickActionsService;


  @GetMapping("/reset")
  public String resetDatabase() {
    quickActionsService.resetDatabase();
    System.out.println("Resetting database...");
    return "redirect:/";
  }

  @GetMapping("/import")
  public String showImportForm() {
    return "data-modifier/csv/import-csv";
  }

  // process the uploaded files
  @PostMapping("/import")
  public String handleFileUpload(@RequestParam("customercsv") MultipartFile customerFile, @RequestParam("ticketleadcsv") MultipartFile ticketLeadFile, @RequestParam("budgetcsv") MultipartFile budgetFile, Model model) throws IOException, SQLException {

    try {
      quickActionsService.ImportData(customerFile, budgetFile, ticketLeadFile);
    } catch (PostPersistException | LocalValidationException e) {
      model.addAttribute("messages", quickActionsService.getRunTimeErrors());
      return "data-modifier/csv/import-csv";
    }

    return "redirect:/quick-actions/database/import?success=true";

  }

  @GetMapping("/generate")
  public String showGenerateForm() {
    return "data-modifier/generate/generate-data";
  }

  @PostMapping("/generate")
  public String generateData(@RequestParam int customerCount, @RequestParam int budgetPerCustomer, @RequestParam int ticketPerCustomer, @RequestParam int leadPerCustomer) throws SQLDataException {

    Map<String, Integer> results = quickActionsService.generateAndSaveRandomData(customerCount, budgetPerCustomer, ticketPerCustomer, leadPerCustomer);

    return "redirect:/quick-actions/database/generate?success=true";
  }


}
