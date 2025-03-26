package site.easy.to.build.crm.extension.io;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CsvImport {

  private final CsvMapper csvMapper;
  private final Validator validator;

  public CsvImport(Validator validator) {
    this.validator = validator;
    this.csvMapper = CsvMapper.builder().build();
  }

  public <T> List<T> readToCsv(Class<T> targetClass, MultipartFile file, List<String> errorMessages,
                               String fileContent) {
    List<T> csvObjects = new ArrayList<>();
    try {
      CsvSchema schema = csvMapper.schemaFor(targetClass).withHeader();
      MappingIterator<T> mappingIterator =
        csvMapper.readerFor(targetClass).with(schema).readValues(file.getInputStream());
      csvObjects = mappingIterator.readAll();
    } catch (IOException e) {
      errorMessages.add("--- --- ---");
      errorMessages.add("Error reading CSV " + e.getMessage());
      errorMessages.add("--- --- ---");
      return csvObjects;
    }
    validateCsvObjects(csvObjects, errorMessages,fileContent);
    return csvObjects;
  }

  private <T> void validateCsvObjects(List<T> csvObjects, List<String> errorMessages, String fileContent) {
    for (int i = 0; i < csvObjects.size(); i++) {
      Set<ConstraintViolation<T>> violations = validator.validate(csvObjects.get(i));
      if (!violations.isEmpty()) {
        for (ConstraintViolation<T> violation : violations) {
          String errorMessage = String.format(
            "Line %d of %s: Property '%s' failed validation: %s",
            2 + i,
            fileContent,
            violation.getPropertyPath(),
            violation.getMessage()
          );
          errorMessages.add(errorMessage);
        }
      }
    }
  }
}