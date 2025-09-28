package org.aplicacaotelasdi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class VeiculoDAO {

    // Cria a tabela veiculos caso não exista
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "placa TEXT NOT NULL," +
                "modelo TEXT NOT NULL," +
                "marca TEXT NOT NULL," +
                "ano INTEGER NOT NULL" +
                ");";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Salva um novo veículo
    public static boolean salvar(String placa, String modelo, String marca, int ano) {
        String sql = "INSERT INTO veiculos (placa, modelo, marca, ano) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            pstmt.setString(2, modelo);
            pstmt.setString(3, marca);
            pstmt.setInt(4, ano);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Atualiza um veículo existente
    public static boolean atualizar(int id, String placa, String modelo, String marca, int ano) {
        String sql = "UPDATE veiculos SET placa=?, modelo=?, marca=?, ano=? WHERE id=?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            pstmt.setString(2, modelo);
            pstmt.setString(3, marca);
            pstmt.setInt(4, ano);
            pstmt.setInt(5, id);

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
