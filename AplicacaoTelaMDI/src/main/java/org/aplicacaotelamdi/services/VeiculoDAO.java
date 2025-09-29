package org.aplicacaotelamdi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class VeiculoDAO {

    // Cria a tabela veiculos caso não exista
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "fabricante TEXT NOT NULL," +
                    "modelo TEXT NOT NULL," +
                    "cidade TEXT NOT NULL" +
                ");";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Salva um novo veículo
    public static boolean salvar(String fabricante, String modelo, String cidade) {
        String sql = "INSERT INTO veiculos (fabricante, modelo, cidade) VALUES (?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fabricante);
            pstmt.setString(2, modelo);
            pstmt.setString(3, cidade);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Atualiza um veículo existente
    public static boolean atualizar(int id, String fabricante, String modelo, String cidade) {
        String sql = "UPDATE veiculos SET fabricante=?, modelo=?, cidade=? WHERE id=?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fabricante);
            pstmt.setString(2, modelo);
            pstmt.setString(3, cidade);
            pstmt.setInt(4, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Exclui um veículo pelo ID
    public static boolean excluir(int id) {
        String sql = "DELETE FROM veiculos WHERE id=?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
