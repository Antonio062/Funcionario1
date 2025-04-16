package com.example.funcionario1;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArquivoCSV {

    private static final String FILE_PATH = "C:\\Users\\perei\\IdeaProjects\\Funcionario1\\src\\main\\java\\com\\example\\funcionario1\\Funcionarios.csv";

    public static void salvarFuncionarios(List<Funcionarios> funcionarios) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Funcionarios funcionario : funcionarios) {
                writer.write(formatFuncionario(funcionario));
                writer.newLine();
            }
        }
    }

    public static List<Funcionarios> carregarFuncionarios() throws IOException {
        List<Funcionarios> funcionarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                funcionarios.add(parseFuncionario(line));
            }
        }
        return funcionarios;
    }
    public static void excluirFuncionarios() throws IOException {
        List<Funcionarios> funcionariosVazios = new ArrayList<>();

        salvarFuncionarios(funcionariosVazios);
    }



    private static String formatFuncionario(Funcionarios funcionario) {
        Endereco e = funcionario.getEndereco();
        return String.join(";",
                funcionario.getMatricula(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getDataNascimento().toString(),
                funcionario.getCargo(),
                funcionario.getSalario().toString(),
                e.getLogradouro(),
                e.getNumero(),
                e.getComplemento(),
                e.getBairro(),
                e.getCidade(),
                e.getEstado(),
                e.getCep()
        );
    }
    private static Funcionarios parseFuncionario(String line) {
        String[] data = line.split(";");
        if (data.length < 7) {
            System.out.println("Linha inválida (menos de 7 colunas): " + line);
            return null;
        }
        return new Funcionarios(
                data[0],
                data[1],
                data[2],
                LocalDate.parse(data[3]),
                data[4],
                Float.parseFloat(data[5]),
                new Endereco(data[6], "", "", "", "", "", ""));

    }
}
