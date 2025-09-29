package org.aplicacaotelasdi.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:AplicacaoTelaSDI_/data/veiculos.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
