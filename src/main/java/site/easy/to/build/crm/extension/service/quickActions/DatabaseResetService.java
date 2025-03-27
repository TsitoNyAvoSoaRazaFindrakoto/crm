package site.easy.to.build.crm.extension.service.quickActions;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class DatabaseResetService {

	private JdbcTemplate jdbcTemplate;

	@Transactional
	public void resetDatabase() {
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

		// Liste des tables à supprimer
		List<String> tables = List.of(
				"contract_settings",
				"email_template",
				"employee",
				"file",
				"google_drive_file",
				"lead_action",
				"lead_settings",
				"ticket_settings",
				"trigger_lead",
				"trigger_ticket",
				"trigger_contract",
				"customer",
				"customer_login_info",
				"expense",
				"budget");
		tables.forEach(table -> {
			jdbcTemplate.execute("TRUNCATE TABLE " + table);
		});
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
	}
}
