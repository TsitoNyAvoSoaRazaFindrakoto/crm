package site.easy.to.build.crm.extension.io.csvImport;

import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DbImport {
  private final Validator validator;
  private final CsvUtil csvUtil;

  public List<?> read(String clazz, InputStream inputStream) throws IOException, ClassNotFoundException {
    Class<?> targetClass = Class.forName(clazz).getAnnotation(CsvDTO.class).csv();
    return csvUtil.read(targetClass, inputStream);
  }

  public T List<T> convertToType(List<? extends CsvBase<T>> list) {
    if (list == null || list.isEmpty()) return null;
    return list.stream()
             .map(CsvBase::extractMotherEntity)
             .collect(Collectors.toList());
  }
}
