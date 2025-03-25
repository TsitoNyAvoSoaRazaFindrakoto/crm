package site.easy.to.build.crm.extension.util.io;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import site.easy.to.build.crm.extension.Exception.CsvValidationException;
import site.easy.to.build.crm.extension.Exception.LocalValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class CsvImport {

  private final CsvMapper csvMapper;

  public CsvImport() {
    this.csvMapper = new CsvMapper();
  }

  public <T, C extends CsvBase<T>> List<C> readToCsv(Class<T> motherEntity, InputStream inputStream, Validator validator) throws IOException, CsvValidationException {
    @SuppressWarnings("unchecked") Class<C> targetClass = (Class<C>) motherEntity.getAnnotation(CsvDTO.class).value();
    CsvSchema schema = csvMapper.schemaFor(targetClass).withHeader();
    MappingIterator<C> mappingIterator = csvMapper.readerFor(targetClass).with(schema).readValues(inputStream);
    
    List<C> csvObjects = mappingIterator.readAll();
    
    // Validate all objects after reading
		for (int i = 0; i < csvObjects.size(); i++) {
      csvObjects.get(i).validate(validator);
    }
    
    return csvObjects;
  }

  public List<?> readToEntity(Class<?> targetClass, InputStream inputStream, Validator validator, Map<String, ?> map) throws IOException, CsvValidationException, LocalValidationException {
    List<? extends CsvBase<?>> csvObjs = readToCsv(targetClass, inputStream, validator);
    return csvObjs.stream().map(csvObj -> {
      try {
        return ((CsvBase<?>) csvObj.extractMotherEntity(validator, map)).extractMotherEntity(validator, map);
      } catch (LocalValidationException e) {
        throw new RuntimeException(e);
      }
    }).toList();
  }

  public <T> List<T> read(Class<T> clazz, InputStream inputStream) throws IOException {
    CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
    MappingIterator<T> mappingIterator = csvMapper.readerFor(clazz).with(schema).readValues(inputStream);
    return mappingIterator.readAll();
  }
}
