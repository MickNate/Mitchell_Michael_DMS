package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * RiseAgainst
 * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
 * Class that is able to easily access the database to make changes without the user having to worry
 * about typing in SQL queries themselves.
 */
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

	/**
	 * RiseAgainst Constructor
	 * Creates a RiseAgainst object
	 * @param url a string with the filepath to find database
	 */
	public RiseAgainst(String url) {
		super(url);
	}

	/**
	 * RiseAgainst PrepareSQL
	 * Takes in the parameters of a query and returns a string containing what will be sent
	 * to the database.
	 * @param fields String represent a column of the database table to return
	 * @param whatField String of the column to search through
	 * @param whatValue String of the value to find in the whatField column
	 * @param sortField String of the column to use for sorting
	 * @param sort String to represent if the results ascend or descend
	 * @return
	 */
	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

	/**
	 * RiseAgainst Insert
	 * Creates a new row in the column
	 * @param Title String with the title of the song
	 * @param Album String with the album name
	 * @param Track Integer with the track number
	 * @param Year Integer with the year
	 * @param Length String with the minute and seconds of song
	 * @param Writer String with the name of the writer of the song
	 * @param Single Boolean with if the song is a single or not
	 * @param ID Integer with the id number of the song, the primary key
	 */
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

	/**
	 * RiseAgainst Delete
	 * Deletes a row in the database table
	 * @param whatField String containing the column to look through
	 * @param whatValue String containing the value to look for in column
	 */
	public void delete(String whatField, String whatValue) {
		super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
	}

	/**
	 * RiseAgainst Update
	 * Updates a value in a pre-existing row
	 * @param whatField String containing the column to change
	 * @param whatValue String containing the new value to add to the whatField column of a row
	 * @param whereField String containing a column to search for
	 * @param whereValue String containing the value to search for in the whereField column of a row
	 */
	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
	}

	/**
	 * RiseAgainst Select
	 * Creates an array of objects that match a search query sent to the database
	 * @param fields String of the columns to return values from in the row
	 * @param whatField String with the name of the column to search through
	 * @param whatValue String with the value to search for in the whatField column
	 * @param sortField String with name of column to base results around
	 * @param sort String declaring whether the results are ascending or descending
	 * @return an array of objects that match the query results
	 */
	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	/**
	 * RiseAgainst GetExecuteResult
	 * Creates an array of objects that match a search query sent to the database
	 * @param query String containing the query details
	 * @return an array of objects that match the query results
	 */
	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return super.executeQuery(query);
	}

	/**
	 * RiseAgainst Query
	 * Creates a query request for the database
	 * @param query String of the query that is being sent into the database
	 */
	public void execute(String query) {
		super.execute(query);
	}

	/**
	 * RiseAgainst SelectToTable
	 * Creates a table that contains results of a query to the database
	 * @param fields String with the column(s) to pull from in the results
	 * @param whatField String with the column to look through in query
	 * @param whatValue String with the value to look for in the whatField column
	 * @param sortField String with the column to base the results around
	 * @param sort String with the details on if the results will be ascending or descending
	 * @return
	 */
	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

}