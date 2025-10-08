package org.aplicacaotelasdi.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class Database {
    private static final String DB_PATH = "data/veiculos.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    static {
        criarDiretorioSeNecessario();
    }

    private static void criarDiretorioSeNecessario() {
        try {
            File dbFile = new File(DB_PATH);
            File parentDir = dbFile.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                boolean criado = parentDir.mkdirs();
                if (criado) {
                    System.out.println("Diretório criado: " + parentDir.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao criar diretório: " + e.getMessage());
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}