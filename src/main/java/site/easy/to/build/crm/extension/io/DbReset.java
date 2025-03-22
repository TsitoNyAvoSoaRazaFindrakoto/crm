package site.easy.to.build.crm.extension.io;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class DbReset {
  private JdbcTemplate jdbcTemplate;

  @Transactional
  public void resetDatabase() {
    jdbcTemplate.execute("set foreign_key_checks=0");
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
      "customer_login_info"
    );
    tables.forEach(table -> {
      jdbcTemplate.execute("truncate table " + table);
    });
    jdbcTemplate.execute("set foreign_key_checks=1");
  }

}
