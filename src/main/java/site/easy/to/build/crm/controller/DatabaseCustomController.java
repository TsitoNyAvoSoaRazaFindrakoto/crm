package site.easy.to.build.crm.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.easy.to.build.crm.util.DatabaseCustomUtil;

@Controller
@RequestMapping("/quick-action/database")
@AllArgsConstructor
public class DatabaseCustomController {
  private DatabaseCustomUtil databaseCustomUtil;

  @GetMapping()
  public String databaseCustom() {
    return "quick-action/database";
  }

  @PostMapping("/reset")
  public String resetDatabase() {
    databaseCustomUtil.resetDatabase();
    return "redirect:/";
  }

  @PostMapping("/generate")
  public String generateData() {
//    TODO: generate data
    return "redirect:/";
  }

  @PostMapping("/import")
  public String importData() {
//    TODO : import data
    return "redirect:/";
  }
}
