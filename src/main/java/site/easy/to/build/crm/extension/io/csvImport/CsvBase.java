package site.easy.to.build.crm.extension.io.csvImport;

public abstract class CsvBase<T> {
  public abstract Class<T> getMotherEntity();
  public abstract T extractMotherEntity();
  public abstract Object extractClass(Class<?> someEntity);
  public abstract Object extractClass(String someEntity);
}
