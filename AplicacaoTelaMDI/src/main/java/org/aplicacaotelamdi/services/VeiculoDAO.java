package org.aplicacaotelamdi.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    // Cria a tabela se não existir
    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS veiculos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fabricante TEXT NOT NULL," +
                "modelo TEXT NOT NULL," +
                "cidade TEXT," +
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
    public static boolean salvar(String fabricante, String modelo, String cidade, int ano) {
        String sql = "INSERT INTO veiculos (fabricante, modelo, cidade, ano) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fabricante);
            pstmt.setString(2, modelo);
            pstmt.setString(3, cidade);
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
    public static boolean atualizar(int id, String fabricante, String modelo, String cidade, int ano) {
        String sql = "UPDATE veiculos SET fabricante=?, modelo=?, cidade=?, ano=? WHERE id=?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fabricante);
            pstmt.setString(2, modelo);
            pstmt.setString(3, cidade);
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
        String sql = "SELECT id, fabricante, modelo, cidade, ano FROM veiculos";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fabricante = rs.getString("fabricante");
                String modelo = rs.getString("modelo");
                String cidade = rs.getString("cidade");
                String ano = rs.getString("ano");
                lista.add(new String[]{id, fabricante, modelo, cidade, ano});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
