package site.easy.to.build.crm.extension.service.quickActions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.extension.exception.LocalValidationException;
import site.easy.to.build.crm.extension.exception.PostPersistException;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class QuickActionsService {
  private final DataGenerationService dataGenerationService;
  private final DataImportService dataImportService;
  private final DatabaseResetService databaseResetService;
  @Getter
  private List<String> runTimeErrors;

  public void ImportData(MultipartFile customers, MultipartFile budgets, MultipartFile ticketLeads) throws IOException, SQLException, LocalValidationException, PostPersistException {
    try {
      dataImportService.importAllCsvData(customers, budgets, ticketLeads);
      dataGenerationService.importCsvAndSave(dataImportService.getCustomerCsvList(),
        dataImportService.getTicketLeadCsvList(), dataImportService.getCustomerBudgetCsvList());
      dataImportService.clearImportedData();
      dataGenerationService.clearGeneratedData();
    } catch (LocalValidationException lve) {
      runTimeErrors = dataImportService.getDataImportErrors();
      dataImportService.clearImportedDataErrors();
      throw lve;
    } catch (PostPersistException ppe) {
      runTimeErrors = dataGenerationService.getDataCompletionErrors();
      dataGenerationService.clearDataCompletionErrors();
      throw ppe;
    } catch (Exception e) {
      dataImportService.clearImportedData();
      dataGenerationService.clearGeneratedData();
      throw e;
    }
  }

  public void resetDatabase() {
    databaseResetService.resetDatabase();
  }

  public Map<String, Integer> generateAndSaveRandomData(int customerCount, int budgetPerCustomer,
                                                        int ticketPerCustomer, int leadPerCustomer) throws SQLDataException {
    return dataGenerationService.generateAndSaveRandomData(customerCount, budgetPerCustomer,
      ticketPerCustomer, leadPerCustomer);
  }

}
