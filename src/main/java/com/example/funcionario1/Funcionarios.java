package com.example.funcionario1;
import java.time.LocalDate;
import java.time.Period;

public class Funcionarios {

    private String matricula;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cargo;
    private Float salario;
    private Endereco endereco;

    public Funcionarios(String matricula, String nome, String cpf, LocalDate dataNascimento,
                        String cargo, Float salario, Endereco endereco) throws IllegalArgumentException {
        if (!validarMatricula(matricula)) {
            throw new IllegalArgumentException("Matrícula inválida. Deve ter 6 dígitos numéricos.");
        }
        if (!validarNome(nome)) {
            throw new IllegalArgumentException("Nome inválido. O nome não pode ser vazio e deve ter pelo menos 2 caracteres.");
        }
        if (!validarCpf(cpf)) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (!validarIdade(dataNascimento)) {
            throw new IllegalArgumentException("A idade mínima para cadastro é 18 anos.");
        }
        if (!validarSalario(salario)) {
            throw new IllegalArgumentException("Salário inválido. Deve ser um valor positivo.");
        }

        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.cargo = cargo;
        this.salario = salario;
        this.endereco  = endereco;
    }

    private boolean validarMatricula(String matricula) {
        return matricula != null && matricula.matches("\\d{6}");
    }

    private boolean validarNome(String nome){
        return !nome.isEmpty() && nome.length() > 1;
    }

    private boolean validarCpf(String cpf){
        return cpf.length() <= 14;

    }
    private boolean validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) return false;
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        return idade >= 18;
    }
    private boolean validarSalario(Float salario){
        return salario > 0;
    }

    @Override
    public String toString() {
        return "Funcionário [matricula=" + matricula +
                ", nome=" + nome +
                ", cpf=" + cpf +
                ", dataNascimento=" + dataNascimento +
                ", cargo=" + cargo +
                ", salario=" + salario +
                ", endereco=" + endereco + "]";
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Float getSalario() {
        return salario;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco (Endereco endereco){
        this.endereco = endereco;
    }
}
