package site.easy.to.build.crm.extension.io.input;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class CsvUtil {
	private final CsvMapper csvMapper;

	public CsvUtil() {
		this.csvMapper = new CsvMapper();
	}

	public List<? extends CsvBase> read(Class<? extends CsvBase> clazz, InputStream inputStream) throws IOException {
		CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
		MappingIterator<? extends CsvBase> mappingIterator = csvMapper.readerFor(clazz).with(schema)
				.readValues(inputStream);
		return mappingIterator.readAll();
	}

	public void write(Class<? extends CsvBase> clazz, List<? extends CsvBase> objects, File file) throws IOException {
		CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
		csvMapper.writer(schema).writeValue(file, objects);
	}

}
