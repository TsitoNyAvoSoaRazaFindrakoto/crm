package site.easy.to.build.crm.extension.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.extension.util.io.CsvImport;
import site.easy.to.build.crm.extension.util.io.dto.CustomerBudgetCsv;
import site.easy.to.build.crm.extension.util.io.dto.CustomerCsv;
import site.easy.to.build.crm.extension.util.io.dto.TicketLeadCsv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class DataImportService {
  private final CsvImport csvImport;

  private List<CustomerCsv> customerCsvList;
  private List<CustomerBudgetCsv> customerBudgetCsvList;
  private List<TicketLeadCsv> ticketLeadCsvList;

  public DataImportService(CsvImport csvImport) {
    this.csvImport = csvImport;
  }

  public List<String> importCustomerCsv(MultipartFile file) throws IOException {
    List<String> errorMessages = new ArrayList<>();
    customerCsvList = csvImport.readToCsv(CustomerCsv.class, file.getInputStream(), errorMessages);
    return errorMessages;
  }

  public List<String> importCustomerBudgetCsv(MultipartFile file) throws IOException {
    List<String> errorMessages = new ArrayList<>();
    customerBudgetCsvList = csvImport.readToCsv(CustomerBudgetCsv.class, file.getInputStream(), errorMessages);
    return errorMessages;
  }

  public List<String> importTicketLeadCsv(MultipartFile file) throws IOException {
    List<String> errorMessages = new ArrayList<>();
    ticketLeadCsvList = csvImport.readToCsv(TicketLeadCsv.class, file.getInputStream(), errorMessages);
    return errorMessages;
  }

  public void clearData() {
    customerCsvList.clear();
    customerBudgetCsvList.clear();
    ticketLeadCsvList.clear();
  }

}
