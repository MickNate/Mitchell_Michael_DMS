package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * DBHelper
 * Michael N. Mitchell, CEN-3024C-31950, July 10, 2024
 * Works as a shortcut in regards to accessing the database.
 */
public class DBHelper {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private String url;

	/**
	 * DBHelper Constructor
	 * Creates a DBHelper object
	 * @param url a string with the filepath to find database
	 */
	public DBHelper(String url) {
		connection = null;
		statement = null;
		resultSet = null;
		this.url = url;
	}

	/**
	 * DBHelper Connect
	 * Attempts to connect to the database given
	 */
	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + url);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DBHelper Close
	 * Close the connection once the database is done being used.
	 */
	private void close() {
		try {
			connection.close();
			statement.close();
			if (resultSet != null)
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DBHelper arrayListTo2DArray
	 * Converts a list of arrays into a 2D array
	 * @param list that is turned into a 2D array
	 * @return an array
	 */
	private Object[][] arrayListTo2DArray(ArrayList<ArrayList<Object>> list) {
		Object[][] array = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Object> row = list.get(i);
			array[i] = row.toArray(new Object[row.size()]);
		}
		return array;
	}

	/**
	 * DBHelper Execute
	 * Does a query to the database
	 * @param sql a string that is contains the query
	 */
	protected void execute(String sql) {
		try {
			connect();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}

	/**
	 * DBHelper ExecuteQueryToTable
	 * Takes in a request and then returns the results as a table
	 * @param sql a string passed in that contains the query for the database
	 * @return the table that contains the results of the query
	 */
	protected DefaultTableModel executeQueryToTable(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> columns = new ArrayList<Object>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++)
			columns.add(resultSet.getMetaData().getColumnName(i));
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++)
				subresult.add(resultSet.getObject(i));
				result.add(subresult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return new DefaultTableModel(arrayListTo2DArray(result), columns.toArray());
	}

	/**
	 * DBHelper ExecuteQuery
	 * Executes a query and returns an arraylist of the objects the query returns
	 * @param sql a string with the query
	 * @return the arrayList made from the execution of the query.
	 */
	protected ArrayList<ArrayList<Object>> executeQuery(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					subresult.add(resultSet.getObject(i));
				}
				result.add(subresult);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		close();
		return result;
	}

}