package site.easy.to.build.crm.extension.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import site.easy.to.build.crm.extension.service.QuickActionsService;

@Controller
@RequestMapping("/quick-action/database")
@AllArgsConstructor
public class QuickActionsController {
	private final QuickActionsService quickActionService;

	@GetMapping()
	public String databaseCustom() {
		return "quick-action/database";
	}

	@GetMapping("/reset")
	public String resetDatabase() {
		quickActionService.resetDatabase();
		return "redirect:/login";
	}

	@GetMapping("/import")
	public String toImport(Model m) {
		return "quick-action/import";
	}

	@PostMapping("/generate")
	public String generateData() {
		quickActionService.generateData();
		return "redirect:/login";
	}

	@GetMapping("/generate")
	public String toGenerate(Model m) {
		return "quick-action/generate";
	}
	

	@PostMapping("/import")
	public String importData(MultipartFile file, @RequestParam String table) {
		quickActionService.importData(file, table);
		return "redirect:/login";
	}

}
