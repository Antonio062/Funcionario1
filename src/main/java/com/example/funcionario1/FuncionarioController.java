package com.example.funcionario1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuncionarioController {
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField matriculaTextField;
    @FXML
    private TextField cpfTextField;
    @FXML
    private TextField cargoTextField;
    @FXML
    private TextField salarioTextField;
    @FXML
    private TextField dataNascimentoTextField;
    @FXML
    private TextField logradouroTextField;
    @FXML
    private TextField numeroTextField;
    @FXML
    private TextField complementoTextField;
    @FXML
    private TextField bairroTextField;
    @FXML
    private TextField cidadeTextField;
    @FXML
    private TextField estadoTextField;
    @FXML
    private TextField cepTextField;
    @FXML
    private TextField FiltroCargoTextField;
    @FXML
    private TextField FiltroSalarioMinimoTextField;
    @FXML
    private TextField FiltroSalarioMaximoTextField;
    @FXML
    private TextField FiltroCidadeTextField;

    private List<Funcionarios> funcionarios;
    private List<Endereco> endereco;
    private ObservableList<Funcionarios> observableFuncionarios = FXCollections.observableArrayList();

    @FXML
    private TableView<Funcionarios> funcionariosTableView;
    @FXML
    private TableColumn<Funcionarios, String> nomeColumn;
    @FXML
    private TableColumn<Funcionarios, String> matriculaColumn;
    @FXML
    private TableColumn<Funcionarios, String> cpfColumn;
    @FXML
    private TableColumn<Funcionarios, String> cargoColumn;
    @FXML
    private TableColumn<Funcionarios, BigDecimal> salarioColumn;
    @FXML
    private TableColumn<Funcionarios, LocalDate> dataNascimentoColumn;
    @FXML
    private TableColumn<Funcionarios, String> enderecoColumn;



    @FXML
    private void initialize() {
        try {
            funcionarios = ArquivoCSV.carregarFuncionarios();

            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
            matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("matricula"));
            cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargo"));
            salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));
            dataNascimentoColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
            enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salvarFuncionario() {
        try {
            String nome = nomeTextField.getText().trim();
            String matricula = matriculaTextField.getText().trim();
            String cpf = cpfTextField.getText().trim();
            String cargo = cargoTextField.getText().trim().toUpperCase();
            String salarioStr = salarioTextField.getText().trim();
            String dataNascimentoStr = dataNascimentoTextField.getText().trim();
            String logradouro = logradouroTextField.getText().trim().toUpperCase();
            String numero = numeroTextField.getText().trim().toUpperCase();
            String complemento = complementoTextField.getText().trim().toUpperCase();
            String bairro = bairroTextField.getText().trim().toUpperCase();
            String cidade = cidadeTextField.getText().trim().toUpperCase();
            String estado = estadoTextField.getText().trim().toUpperCase();
            String cep = cepTextField.getText().trim().toUpperCase();


            if (nome.isEmpty() || matricula.isEmpty() || cpf.isEmpty() || cargo.isEmpty()
                    || salarioStr.isEmpty() || dataNascimentoStr.isEmpty()
                    || logradouro.isEmpty() || numero.isEmpty() || bairro.isEmpty()
                    || cidade.isEmpty() || estado.isEmpty() || cep.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campos obrigatórios");
                alert.setHeaderText("Por favor, preencha todos os campos.");
                alert.showAndWait();
                return;
            }

            float salario = Float.parseFloat(salarioStr);
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);

            Endereco endereco1 = new Endereco(logradouro, numero, complemento, bairro, cidade, estado, cep);
            Funcionarios funcionarios1 = new Funcionarios(matricula, nome, cpf, dataNascimento, cargo, salario, endereco1);
            funcionarios.add(funcionarios1);
            observableFuncionarios.add(funcionarios1);
            funcionariosTableView.setItems(observableFuncionarios);

            ArquivoCSV.salvarFuncionarios(funcionarios);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Funcionário salvo com sucesso!");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Formato");
            alert.setHeaderText("Salário inválido");
            alert.setContentText("Digite um valor numérico válido para o salário.");
            alert.showAndWait();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Data");
            alert.setHeaderText("Data de nascimento inválida");
            alert.setContentText("Use o formato correto: AAAA-MM-DD.");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao salvar");
            alert.setHeaderText("Erro ao salvar funcionário");
            alert.setContentText("Verifique se o arquivo está acessível.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    public void carregarValores() throws IOException {

        funcionarios = ArquivoCSV.carregarFuncionarios();


        observableFuncionarios.clear();
        observableFuncionarios.addAll(funcionarios);

        funcionariosTableView.setItems(observableFuncionarios);
    }
    @FXML
    private void excluirFuncionarios() {
        try {
            ArquivoCSV.excluirFuncionarios();

            observableFuncionarios.clear();
            funcionariosTableView.setItems(observableFuncionarios);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Todos os funcionários foram excluídos com sucesso!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao excluir os funcionários!");
            alert.showAndWait();
        }
    }



    public void filtrarCargo() {
        String cargo = FiltroCargoTextField.getText().trim();

        List<Funcionarios> filtrados = observableFuncionarios.stream()
                .filter(c -> c.getCargo().trim().equalsIgnoreCase(cargo))
                .collect(Collectors.toList());

        ObservableList<Funcionarios> funcionariosFiltrados = FXCollections.observableArrayList(filtrados);
        funcionariosTableView.setItems(funcionariosFiltrados);
    }


    public void filtrarSalario() {

        if (observableFuncionarios == null || observableFuncionarios.isEmpty()) {
            System.out.println("Não há funcionários na lista para calcular a média.");
            return;
        }
        float salarioMinimo = Float.parseFloat(FiltroSalarioMinimoTextField.getText().trim());
        float salarioMaximo = Float.parseFloat(FiltroSalarioMaximoTextField.getText().trim());

        List<Funcionarios> filtrados = observableFuncionarios.stream()
                .filter(c -> c.getSalario() >= salarioMinimo && c.getSalario() <= salarioMaximo)
                .collect(Collectors.toList());

        ObservableList<Funcionarios> funcionariosFiltrados = FXCollections.observableArrayList(filtrados);
        funcionariosTableView.setItems(funcionariosFiltrados);
    }

    public void mediaSalarial() {

        String cargo = FiltroCargoTextField.getText().trim();

        double mediaSalarial =  observableFuncionarios.stream()
                .filter(c -> c.getCargo().equalsIgnoreCase(cargo)).mapToDouble(Funcionarios::getSalario)
                .average().orElse(0.0);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Média Salarial");
        alert.setHeaderText("A média salarial dos funcionários é:");
        alert.setContentText(String.format("R$ %.2f", mediaSalarial));
        alert.showAndWait();
    }


    public void filtrarCidade(){
        String cidade = FiltroCidadeTextField.getText().trim();

        List<Funcionarios> filtrados = observableFuncionarios.stream()
                .filter(c -> c.getEndereco() != null && c.getEndereco().getCidade().equalsIgnoreCase(cidade))
                .collect(Collectors.toList());

        ObservableList<Funcionarios> funcionariosFiltrados = FXCollections.observableArrayList(filtrados);
        funcionariosTableView.setItems(funcionariosFiltrados);
    }
}