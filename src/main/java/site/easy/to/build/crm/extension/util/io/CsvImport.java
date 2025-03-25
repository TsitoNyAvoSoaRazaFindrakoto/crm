package site.easy.to.build.crm.extension.util.io;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
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

  public <T> List<T> readToCsv(Class<T> targetClass, InputStream inputStream, List<String> errorMessages) {
    List<T> csvObjects = new ArrayList<>();
    try {
      CsvSchema schema = csvMapper.schemaFor(targetClass).withHeader();
      MappingIterator<T> mappingIterator = csvMapper.readerFor(targetClass).with(schema).readValues(inputStream);
      csvObjects = mappingIterator.readAll();
    } catch (IOException e) {
      errorMessages.add("Error reading CSV: " + e.getMessage());
      return csvObjects; // Return already read objects, or empty list.
    }
    validateCsvObjects(csvObjects, errorMessages);
    return csvObjects;
  }

  private <T> void validateCsvObjects(List<T> csvObjects, List<String> errorMessages) {
    for (int i = 0; i < csvObjects.size(); i++) {
      Set<ConstraintViolation<T>> violations = validator.validate(csvObjects.get(i));
      if (!violations.isEmpty()) {
        for (ConstraintViolation<T> violation : violations) {
          String errorMessage = String.format(
            "Line %d: Property '%s' failed validation: %s",
            2 + i,
            violation.getPropertyPath(),
            violation.getMessage()
          );
          errorMessages.add(errorMessage);
        }
      }
    }
  }
}