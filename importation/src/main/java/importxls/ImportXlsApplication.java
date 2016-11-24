package importxls;

import java.io.IOException;

import request.ExcelFileReader;

public class ImportXlsApplication {

	public static void main(String[] args) throws IOException {

		ExcelFileReader tt = new ExcelFileReader();

		tt.readEntierFile();

		// ImportXlsDao.users();

	}

}