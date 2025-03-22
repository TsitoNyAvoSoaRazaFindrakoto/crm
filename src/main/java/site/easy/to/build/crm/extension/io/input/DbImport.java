package site.easy.to.build.crm.extension.io.input;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DbImport {
	private final CsvUtil csvUtil;

	public List<? extends CsvBase> read(String clazz, InputStream inputStream) throws IOException, ClassNotFoundException {
		
		Class<? extends CsvBase> targetClass = Class.forName(clazz).getAnnotation(CsvDTO.class).csv();
		return csvUtil.read(targetClass, inputStream);
	}
}
