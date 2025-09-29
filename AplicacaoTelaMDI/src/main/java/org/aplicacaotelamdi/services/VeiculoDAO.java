package org.aplicacaotelamdi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    // Cria a tabela se não existir
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "placa TEXT NOT NULL," +
                "modelo TEXT NOT NULL," +
                "marca TEXT," +
                "ano INTEGER" +
                ");";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Salva novo veículo
    public static boolean salvar(String placa, String modelo, String marca, int ano) {
        String sql = "INSERT INTO veiculos (placa, modelo, marca, ano) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            pstmt.setString(2, modelo);
            pstmt.setString(3, marca);
            if (ano == 0) {
                pstmt.setNull(4, Types.INTEGER);
            } else {
                pstmt.setInt(4, ano);
            }

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Atualiza veículo existente
    public static boolean atualizar(int id, String placa, String modelo, String marca, int ano) {
        String sql = "UPDATE veiculos SET placa=?, modelo=?, marca=?, ano=? WHERE id=?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, placa);
            pstmt.setString(2, modelo);
            pstmt.setString(3, marca);
            if (ano == 0) {
                pstmt.setNull(4, Types.INTEGER);
            } else {
                pstmt.setInt(4, ano);
            }
            pstmt.setInt(5, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Exclui veículo pelo ID
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

    // Lista todos os veículos (para JTable)
    public static List<String[]> listarTodos() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT id, placa, modelo, marca, ano FROM veiculos";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String placa = rs.getString("placa");
                String modelo = rs.getString("modelo");
                String marca = rs.getString("marca");
                String ano = rs.getString("ano");
                lista.add(new String[]{id, placa, modelo, marca, ano});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
