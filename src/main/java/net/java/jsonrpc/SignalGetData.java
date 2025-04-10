package net.java.jsonrpc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.sql.DataSource;

public class SignalGetData {
    private static final Logger log = LoggerFactory.getLogger(SignalGetData.class);
    private static DataSource dataSource = null;

    public static List<String> getData(String searchWord){

        String query = "SELECT * FROM rst WHERE LOWER(serial_number) LIKE LOWER(?) or " +
                                                        "LOWER(main_location) LIKE LOWER(?) or " +
                                                            "LOWER(location) LIKE LOWER(?)";
        log.debug("SQL query: {}", query);
        initializedataSource();
        List<String> result = new ArrayList<>();

            try {
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchWord + "%");
                preparedStatement.setString(2, "%" + searchWord + "%");
                preparedStatement.setString(3, "%" + searchWord + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String serial_number = resultSet.getString("serial_number");
                    String main_location = resultSet.getString("main_location");
                    String location = resultSet.getString("location");
                    String id_rst = resultSet.getString("id_rst");
                    String codeplag_v = resultSet.getString("codeplag_v");
                    result.add(id + " | " +
                                name+ " | " +
                                serial_number+ " | " +
                                main_location+ " | " +
                                location+ " | " +
                                id_rst+ " | " +
                                codeplag_v);
                }

                connection.close();
                return result;
            } catch (SQLException e) {
                log.error("Database error occurred. Details: {}", e.getMessage(), e);
                throw new RuntimeException("Database operation failed. Original exception: " + e.getMessage(), e);
            }
    }

    public static Integer countSearchFields(String searchWord) {
        String query = "SELECT count(*) FROM rst WHERE LOWER(serial_number) LIKE LOWER(?) or " +
                "LOWER(main_location) LIKE LOWER(?) or " +
                "LOWER(location) LIKE LOWER(?)";

        log.debug("SQL count query: {}", query);

        initializedataSource();
        int result;
        try {
            result = 0;
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchWord + "%");
            preparedStatement.setString(2, "%" + searchWord + "%");
            preparedStatement.setString(3, "%" + searchWord + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException e) {
            log.error("Database error occurred. Details: {}", e.getMessage(), e);
            throw new RuntimeException("Database operation failed. Original exception: " + e.getMessage(), e);
        }
        return result;
    }

    private static void initializedataSource (){
        String JdbcUrl = "jdbc:postgresql://" + GetPropety.get("DB_HOST") + ":" + GetPropety.get("DB_PORT") + "/";
        String Username = GetPropety.get("DB_USER");
        log.debug("Initialize Data Source ...");
        log.debug("DB JDBC URL: {}", JdbcUrl);
        log.debug("DB User name: {}", Username);
        String Password = GetPropety.get("DB_PASSWORD");
        if(dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(JdbcUrl);
            config.setUsername(Username);
            config.setPassword(Password);
            dataSource = new HikariDataSource(config);
        }
    }
}