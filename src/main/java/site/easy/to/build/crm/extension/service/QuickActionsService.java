package site.easy.to.build.crm.extension.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.extension.io.DbReset;
import site.easy.to.build.crm.extension.io.input.DbImport;

@Service
@AllArgsConstructor
public class QuickActionsService {
	private final DbReset dbReset;
	private final DbImport dbImport;

  public void resetDatabase() {
    dbReset.resetDatabase();
  }

  public int generateData() {
//    TODO database generato
    return 0;
  }

	public int importData(MultipartFile file, String clazz) {
		// TODO : import data
    return 0;
  }
}
