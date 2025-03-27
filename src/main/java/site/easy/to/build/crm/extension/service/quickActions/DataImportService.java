package site.easy.to.build.crm.extension.service.quickActions;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.extension.exception.LocalValidationException;
import site.easy.to.build.crm.extension.io.CsvImport;
import site.easy.to.build.crm.extension.io.dto.CustomerBudgetCsv;
import site.easy.to.build.crm.extension.io.dto.CustomerCsv;
import site.easy.to.build.crm.extension.io.dto.TicketLeadCsv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class DataImportService {
  private final CsvImport csvImport;

  private final List<String> dataImportErrors = new ArrayList<>();
  private List<CustomerCsv> customerCsvList;
  private List<CustomerBudgetCsv> customerBudgetCsvList;
  private List<TicketLeadCsv> ticketLeadCsvList;

  public DataImportService(CsvImport csvImport) {
    this.csvImport = csvImport;
  }

  public void importCustomerCsv(MultipartFile file) throws IOException {
    customerCsvList = csvImport.readToCsv(CustomerCsv.class, file, dataImportErrors, "customers");
  }

  public void importCustomerBudgetCsv(MultipartFile file) throws IOException {
    customerBudgetCsvList = csvImport.readToCsv(CustomerBudgetCsv.class, file, dataImportErrors, "budgets");
  }

  public void importTicketLeadCsv(MultipartFile file) throws IOException {
    ticketLeadCsvList = csvImport.readToCsv(TicketLeadCsv.class, file, dataImportErrors, "tickets and leads");
  }

  public void importAllCsvData(MultipartFile customerFile, MultipartFile customerBudgetFile, MultipartFile ticketLeadFile) throws IOException, LocalValidationException {
    importCustomerCsv(customerFile);
    importCustomerBudgetCsv(customerBudgetFile);
    importTicketLeadCsv(ticketLeadFile);
		if (!dataImportErrors.isEmpty()) {
			// ?Info : clearImportedData;
      throw new LocalValidationException();
    }
  }

  public void clearImportedData() {
    customerCsvList.clear();
    customerBudgetCsvList.clear();
    ticketLeadCsvList.clear();
  }

  public void clearImportedDataErrors() {
    dataImportErrors.clear();
  }

}
