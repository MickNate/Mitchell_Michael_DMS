package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class RiseAgainst extends DBHelper {
	private final String TABLE_NAME = "RiseAgainst";
	public static final String Title = "Title";
	public static final String Album = "Album";
	public static final String Track = "Track";
	public static final String Year = "Year";
	public static final String Length = "Length";
	public static final String Writer = "Writer";
	public static final String Single = "Single";
	public static final String ID = "ID";

	public RiseAgainst(String url) {
		super(url);
	}

	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

	public void insert(String Title, String Album, Integer Track, Integer Year, String Length, String Writer, Boolean Single, Integer ID) {
		Title = Title != null ? "\"" + Title + "\"" : null;
		Album = Album != null ? "\"" + Album + "\"" : null;
		Length = Length != null ? "\"" + Length + "\"" : null;
		Writer = Writer != null ? "\"" + Writer + "\"" : null;
		
		Object[] values_ar = {Title, Album, Track, Year, Length, Writer, Single, ID};
		String[] fields_ar = {RiseAgainst.Title, RiseAgainst.Album, RiseAgainst.Track, RiseAgainst.Year, RiseAgainst.Length, RiseAgainst.Writer, RiseAgainst.Single, RiseAgainst.ID};
		String values = "", fields = "";
		for (int i = 0; i < values_ar.length; i++) {
			if (values_ar[i] != null) {
				values += values_ar[i] + ", ";
				fields += fields_ar[i] + ", ";
			}
		}
		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 2);
			fields = fields.substring(0, fields.length() - 2);
			super.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ");");
		}
	}

	public void delete(String whatField, String whatValue) {
		super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
	}

	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
	}

	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return super.executeQuery(query);
	}

	public void execute(String query) {
		super.execute(query);
	}

	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

}