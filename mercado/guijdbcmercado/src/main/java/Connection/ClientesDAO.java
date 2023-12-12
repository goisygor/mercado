package Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.ListaClientes;

public class ClientesDAO {
    // atributos
    private Connection connection;
    private List<ListaClientes> clientes;
    // construtor
    public ClientesDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    // criar Tabela
    public void criarTabela(){
        String sql = "CREATE TABLE IF NOT EXISTS CLIENTES_MERCADO(NOME VARCHAR(255), CPF VARCHAR (255) PRIMARY KEY, EMAIL VARCHAR(255), TELEFONE VARCHAR(13), ENDERECO VARCHAR (255))";
        try (Statement stmt = this.connection.createStatement()){
            stmt.execute(sql);
            System.out.println("tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: "+e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }
    // Listar todos os valores cadastrados
    public List<ListaClientes> listarTodos(){
        PreparedStatement stmt = null;
        //  Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta
        clientes = new ArrayList<>();
        // Criar uma lista para armazenar os funcioanrios recuperdos do banco de dados
        try {
            String sql = "SELECT *FROM CLIENTES_MERCADO";
            stmt = connection.prepareStatement(sql);
            // Prepara a consulta SQL para selecionar todos os registros da tabele
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto cliente com os valores do registro
                ListaClientes cliente = new ListaClientes(
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getString("endereco"));
                clientes.add(cliente); // Adiciona o objeto cliente à lista de Clientes
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection (connection, stmt, rs); // Fecha a conexão, PreparedStatement e o ResultSet
        }
        return clientes; // Retorna a lista de clientes recuperados do banco de dados
    }
    // Cadastrar Cliente no banco de dados
    public void cadastrar(String nome, String cpf, String email, String telefone, String endereco) {
        PreparedStatement stmt = null;
        // Define a instrução SQL paramtrizada para cadastrar na tabela
        String sql = "INSERT INTO CLIENTES_MERCADO (NOME, CPF, EMAIL, TELEFONE, ENDERECO) VALUES (?,?,?,?,?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1,nome);
            stmt.setString(2,cpf);
            stmt.setString(3,email);
            stmt.setString(4,telefone);
            stmt.setString(5,endereco);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Dados inseridos com sucesso");
        } catch (SQLException e ) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao inserir dados no banco de dados.", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    // Atualizar dados no banco de dados
    public void atualizar(String nome, String cpf, String email, String telefone, String endereco) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pelo email
        String sql = "UPDATE CLIENTES_MERCADO SET NOME = ?, EMAIL = ?, TELEFONE = ?, ENDERECO = ? WHERE CPF = ?";
        try {
            stmt = connection.prepareStatement(sql);
            // cpf é a chave primaria, não pode ser alterada.
            stmt.setString(1,nome);
            stmt.setString(2,cpf);
            stmt.setString(3,email);
            stmt.setString(4,telefone);
            stmt.setString(5,endereco);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Dados atualizados com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao atualizar dados no banco de dados");
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    // Apagar dados do banco
    public void apagar (String cpf) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pelo cpf
        String sql = "DELETE FROM CLIENTES_MERCADO WHERE CPF = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(2,cpf);
            stmt.executeUpdate(); // Executa a instrução SQL
            JOptionPane.showMessageDialog(null,"Dado apagado com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao paagar dados no banco de dados");
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}