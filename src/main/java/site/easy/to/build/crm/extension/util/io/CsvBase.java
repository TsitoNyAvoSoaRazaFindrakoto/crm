package site.easy.to.build.crm.extension.util.io;

import jakarta.validation.Validator;
import site.easy.to.build.crm.extension.Exception.CsvValidationException;
import site.easy.to.build.crm.extension.Exception.LocalValidationException;

import java.util.Map;

public abstract class CsvBase<T> {
  public abstract Class<?> getMotherEntity();

  public void validate(Validator validator) throws CsvValidationException {
    if(!validator.validate(this).isEmpty()) throw new CsvValidationException();
  }

  public abstract T extractMotherEntity(Validator validator, Map<String, ?> additionalParams) throws LocalValidationException;

  public abstract T extractEntity(Class<?> targetClass, Validator validator, Map<String, ?> additionalParams);

}

