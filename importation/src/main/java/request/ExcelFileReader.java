package request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import entities.DemandeFormation;
import entities.Formation;
import entities.User;
import jpa.EmFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {

	@SuppressWarnings("null")
	public void readEntierFile() throws IOException {

		File excel = new File("C:/code/workspace/formations/sopra-modified.xlsx");
		FileInputStream fis = new FileInputStream(excel);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("Suivi");

		UserQuery ud = new UserQuery();
		FormationQuery fd = new FormationQuery();
		DemandeFormationQuery dfd = new DemandeFormationQuery();

		boolean first = true;
		for (Row r : ws) {
			if (first) {
				first = false;
				continue;
			}
			try {

				User user = new User();

				Cell cName = r.getCell(7);
				Cell cLastname = r.getCell(8);
				Cell cAgence = r.getCell(1);

				if (cName == null && cLastname == null && cAgence == null)
					continue;

				user.setName(cName.toString());
				user.setLastname(cLastname.toString());
				user.setAgence(cAgence.toString());

				if (!((user.getName() != null || user.getLastname() != null || user.getAgence() != null)
						&& !(user.getName().isEmpty() || user.getLastname().isEmpty() || user.getAgence().isEmpty())))
					continue;

				user = ud.getOrInsertUserInDb(user);

				Formation formation = new Formation();

				Cell cDateReel = r.getCell(3);
				formation.setDateReel(cDateReel.getDateCellValue());

				Cell cNbjours = r.getCell(2);
				// DecimalFormat df = new DecimalFormat("0.0", new
				// DecimalFormatSymbols(Locale.FRENCH));
				// df.setParseBigDecimal(true);
				// String replaceComa = cNbjours.toString().replaceAll(",",
				// ".");
				if (cNbjours != null && !cNbjours.toString().trim().isEmpty()) {
					BigDecimal bdNbjours = new BigDecimal(cNbjours.toString());
					formation.setNbjours(bdNbjours);
				} else {
					BigDecimal bdNbjours = new BigDecimal("0.0");
					formation.setNbjours(bdNbjours);
				}

				Cell cFormation = r.getCell(5);
				formation.setFormation(cFormation.toString());

				Cell cLieuFormation = r.getCell(6);
				formation.setLieuFormation(cLieuFormation.toString());

				Cell cOrganisme = r.getCell(9);
				formation.setOrganisme(cOrganisme.toString());

				Cell cDateAttendue = r.getCell(4);
				formation.setDateAttendue(cDateAttendue.getDateCellValue());

				if (!(!(formation.getFormation().isEmpty() || formation.getLieuFormation().isEmpty())
						&& !(formation.getNbjours() == null || formation.getNbjours().equals(" ")
								|| formation.getFormation() == null || formation.getLieuFormation() == null
								|| formation.getDateReel() == null || formation.getDateAttendue() == null)))
					continue;

				formation = fd.getOrInsertFormationInDb(formation);

				DemandeFormation demandeFormation = new DemandeFormation();
				demandeFormation.setUser(user);
				demandeFormation.setFormation(formation);

				demandeFormation = dfd.getOrInsertDemandeFormationInDb(demandeFormation, user, formation);

			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}

		}

		EmFactory.close();
	}
}
