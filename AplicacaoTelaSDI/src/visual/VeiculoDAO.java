package visual;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class VeiculoDAO {
    private static final Path FILE = Paths.get("veiculos.csv");

    public static boolean salvar(String placa, String modelo, String marca, String ano) {
        String safePlaca = csvEscape(placa);
        String safeModelo = csvEscape(modelo);
        String safeMarca = csvEscape(marca);
        String safeAno = csvEscape(ano);
        String line = String.format("%s,%s,%s,%s%n", safePlaca, safeModelo, safeMarca, safeAno);

        try {
            if (Files.notExists(FILE)) {
                Files.createFile(FILE);
                Files.write(FILE, "placa,modelo,marca,ano\n".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }
            Files.write(FILE, line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String csvEscape(String s) {
        if (s == null) return "";
        // Remove quebras e escape simples (básico)
        return s.replace("\n", " ").replace("\r", " ").replace("\"", "\"\"");
    }
}
