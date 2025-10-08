package org.aplicacaotelamdi.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class Database {
    private static final String URL = "jdbc:sqlite:data/veiculos.db";

    static {
        // Garante que a pasta data existe
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            boolean criado = dataDir.mkdir();
            if (criado) {
                System.out.println("Diretório 'data' criado com sucesso");
            }
        }
    }

    public static Connection connect() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Conectado ao banco: " + new File("data/veiculos.db").getAbsolutePath());
            return conn;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            throw e;
        }
    }
}