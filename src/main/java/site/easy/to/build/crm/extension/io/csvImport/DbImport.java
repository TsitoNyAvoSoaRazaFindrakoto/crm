package site.easy.to.build.crm.extension.io.csvImport;

import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DbImport {
	private final Validator validator;
	private final CsvUtil csvUtil;

	/**
	 * Reads a CSV file and converts it into a list of objects of the specified
	 * class.
	 *
	 * @param clazz       The fully qualified name of the target class.
	 * @param inputStream The input stream of the CSV file.
	 * @return A list of objects of the specified class.
	 * @throws IOException            If an I/O error occurs.
	 * @throws ClassNotFoundException If the class cannot be found.
	 * @throws IllegalStateException  If the class does not have the required
	 *                                annotation.
	 */
	public List<?> read(String clazz, InputStream inputStream) throws IOException, ClassNotFoundException {
		// Load the class dynamically
		Class<?> targetClass = Class.forName(clazz);

		// Check if the class has the @CsvDTO annotation
		CsvDTO csvDTO = targetClass.getAnnotation(CsvDTO.class);
		if (csvDTO == null) {
			throw new IllegalStateException("Class " + clazz + " does not have the @CsvDTO annotation.");
		}

		// Get the target CSV class from the annotation
		Class<?> csvClass = csvDTO.csv();

		// Read the CSV file and return the list of objects
		return csvUtil.read(csvClass, inputStream);
	}

	/**
	 * Converts a list of CsvBase objects into a list of their extracted entities.
	 *
	 * @param list The list of CsvBase objects.
	 * @param <T>  The type of the extracted entities.
	 * @return A list of extracted entities.
	 */
	public <T> List<T> convertToType(List<? extends CsvBase<T>> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.stream()
				.map(CsvBase::extractMotherEntity)
				.collect(Collectors.toList());
	}
}
