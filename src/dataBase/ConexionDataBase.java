package dataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDataBase {

	 private String url;
	    private String username;
	    private String pwd;
	    private Connection connection;

	    // Constructor
	    public ConexionDataBase(String url, String username, String pwd) {
	        this.url = url;
	        this.username = username;
	        this.pwd = pwd;
	    }

	    // Abrir conexión
	    public void openConnection() throws SQLException {
	        try {
	            if (this.connection != null && !this.connection.isClosed()) {
	                // La conexión ya está abierta
	                return;
	            }
	            // Registrar el driver de MySQL
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            // Establecer la conexión
	            this.connection = DriverManager.getConnection(url, username, pwd);
	            System.out.println("TODO BIEN");
	        } catch (ClassNotFoundException e) {
	            throw new SQLException("Error al cargar el driver de MySQL", e);
	        }
	    }

	    // Cerrar conexión
	    public void closeConnection() throws SQLException {
	        if (this.connection != null && !this.connection.isClosed()) {
	            this.connection.close();
	        }
	    }

	    // Getter para obtener la conexión
	    public Connection getConnection() throws SQLException {
	        if (this.connection == null || this.connection.isClosed()) {
	            openConnection();
	        }
	        return this.connection;
	    }

}
