package cz.english.dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileReader {
	private String filePath = "dictionary.txt";
	private Map<String, String> result = new LinkedHashMap<String, String>();

	public FileReader() {
		try {
			List<Object> stringFileList = new ArrayList<Object>();
			stringFileList = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
			for(Object str : stringFileList) {
				String key = str.toString().split(", ")[0];
				String value = str.toString().split(", ")[1];
				result.put(key.replace("\"", ""), value.replace("\"", ""));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getResult() {
		return result; 
	}
}
