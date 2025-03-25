package site.easy.to.build.crm.extension.service;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.*;
import site.easy.to.build.crm.extension.util.io.dto.CustomerBudgetCsv;
import site.easy.to.build.crm.extension.util.io.dto.CustomerCsv;
import site.easy.to.build.crm.extension.util.io.dto.TicketLeadCsv;
import site.easy.to.build.crm.service.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
public class DataGenerationService {
  private final User adminUser;
  private final Faker dataGenerator;
  private final PasswordEncoder passwordEncoder;

  List<String> errors = new ArrayList<>();
  Map<String, CustomerLoginInfo> customerLoginInfos;
  List<Ticket> tickets;
  List<Lead> leads;
  List<Expense> expenses;
  List<Budget> budgets;

  public DataGenerationService(UserService userService, PasswordEncoder passwordEncoder) {
    dataGenerator = new Faker();
    adminUser = userService.findFirst();
    this.passwordEncoder = passwordEncoder;
  }

  public void generateCustomerLoginInfosFrom(List<CustomerCsv> customerCsvList) {
    customerLoginInfos = new HashMap<>();
    for (CustomerCsv customerCsv : customerCsvList) {
      CustomerLoginInfo customerLoginInfo = new CustomerLoginInfo();
      customerLoginInfo.setEmail(customerCsv.getCustomerEmail());
      customerLoginInfo.setPassword(passwordEncoder.encode(customerCsv.getCustomerEmail()));
      customerLoginInfo.setPasswordSet(true);

      Customer customer = new Customer();
      customer.setUser(adminUser);
      customer.setEmail(customerCsv.getCustomerEmail());
      customer.setName(customerCsv.getCustomerName());
      customer.setPosition(dataGenerator.job().title());
      customer.setCreatedAt(LocalDateTime.now());

      Address address = dataGenerator.address();
      customer.setAddress(address.fullAddress());
      customer.setCountry(address.country());

      customer.setCity(address.city());
      customer.setState(address.state());

      customerLoginInfo.setCustomer(customer);

      customerLoginInfos.put(customerCsv.getCustomerEmail(), customerLoginInfo);
    }
  }

  public List<String> setBudgets(List<CustomerBudgetCsv> customerBudgetCsvs) {
    List<String> errors = new ArrayList<>();
    for (CustomerBudgetCsv customerBudgetCsv : customerBudgetCsvs) {
      Budget budget = new Budget();
      budget.setTitle("Added budget" + dataGenerator.business().creditCardType());
      budget.setAmount(customerBudgetCsv.getBudget());
      budget.setStartDate(LocalDate.now());
      budget.setEndDate(LocalDate.now().plusWeeks(dataGenerator.random().nextInt(4, 52)));
      if (customerLoginInfos.containsKey(customerBudgetCsv.getCustomerEmail())){
        budget.setCustomer(customerLoginInfos.get(customerBudgetCsv.getCustomerEmail()).getCustomer());
      } else {
        errors.add("Customer with email "+ customerBudgetCsv.getCustomerEmail() +" found for budget");
      }
      budgets.add(budget);
    }
    return errors;
  }



  public void generateTicketsAndLeads(List<TicketLeadCsv> ticketLeadCsvList) {
    for (TicketLeadCsv ticketLeadCsv : ticketLeadCsvList) {
      if (ticketLeadCsv.getType().equalsIgnoreCase("lead")) {
        Lead lead = new Lead();
        lead.setEmployee(adminUser);
        lead.setManager(adminUser);
        lead.setCreatedAt(LocalDateTime.now().plusDays(dataGenerator.random().nextInt(1, 5)));

      }
    }
  }

}
