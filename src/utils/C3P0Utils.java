package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Utils {

    static ComboPooledDataSource cpds  ;

    static {

        //try {
            cpds = new ComboPooledDataSource("mysql");


    }

    public static ComboPooledDataSource getCpds() {
        return cpds;
    }

    public static Connection getConnection() throws SQLException {


        return  cpds.getConnection();
    }

}
