package site.easy.to.build.crm.extension.service.quickActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.extension.io.dto.CustomerJson;
import site.easy.to.build.crm.extension.service.BudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class DuplicationService {
  private final CustomerService customerService;
  private final TicketService ticketService;
  private final LeadService leadService;
  private final BudgetService budgetService;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final CustomerLoginInfoService customerLoginInfoService;

  public CustomerJson createDuplicate(int customerId) {
    CustomerLoginInfo c = customerLoginInfoService.findById(customerId);
    List<Ticket> tickets = ticketService.findCustomerTickets(c.getCustomer().getCustomerId());
    List<Lead> leads = leadService.getCustomerLeads(customerId);
    List<Budget> budgets = budgetService.findBudgetsByCustomerId(customerId);

    for (Ticket ticket : tickets) {
      ticket.setTicketId(0);
      ticket.setCustomer(null);
      if (ticket.getExpense() != null) {
        ticket.getExpense().setExpenseId(null);
      }
    }

    for (Lead lead : leads) {
      lead.setLeadId(0);
      lead.setCustomer(null);
      if (lead.getExpense() != null) {
        lead.getExpense().setExpenseId(null);
      }
    }

    for (Budget bu : budgets) {
      bu.setBudgetId(null);
      bu.setCustomer(null);
    }

    CustomerJson cj = new CustomerJson();
    cj.setEmail("copy_" + c.getEmail());
    cj.setPassword(c.getPassword());
    cj.setCustomer(c.getCustomer());
    cj.getCustomer().setName("copy_" + c.getCustomer().getName());
    cj.getCustomer().setEmail(cj.getEmail());
    cj.setPasswordSet(c.isPasswordSet());
    cj.setId(null);
    cj.getCustomer().setCustomerId(null);
    cj.setTickets(tickets);
    cj.setLeads(leads);
    cj.setBudgets(budgets);

    return cj;
  }

  public File createJsonFile(int customerLoginId) throws JsonProcessingException {
    CustomerJson cj = createDuplicate(customerLoginId);
    String jsonContent = objectMapper.writeValueAsString(cj);

    File jsonFile = new File(cj.getCustomer().getName() + ".json");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
      writer.write(jsonContent);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return jsonFile;

  }
}
