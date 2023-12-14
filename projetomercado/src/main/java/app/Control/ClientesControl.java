package app.Control;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.Connection.ClientesDAO;
import app.Model.Cliente;
import app.View.ClientesPanel;

public class ClientesControl {

    private List<Cliente> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    public ClientesControl(List<Cliente> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        clientes = new ClientesPanel().listarTodos();
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getIdade() });
        }
    }

    public void cadastrarCliente(String cpf, String nomeCompleto, String idade) {
        try {
            if (validaCpf(cpf) && validaIdade(idade)) {
                Cliente cliente = new Cliente(cpf.trim().toUpperCase(), nomeCompleto.trim().toUpperCase(),
                        idade.trim().toUpperCase());
                clientes.add(cliente);
                new ClientesDAO().cadastrar(cpf, nomeCompleto, idade);
                atualizarTabela();
                exibirMensagem("Cliente cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                exibirMensagem("Verifique se os dados escritos estão corretos e tente novamente!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception err) {
            exibirMensagem("Erro ao cadastrar cliente. Verifique os dados e tente novamente.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void apagar(String cpf) {
        new ClientesDAO().apagar(cpf);
        atualizarTabela();
        exibirMensagem("Cliente removido!", JOptionPane.ERROR_MESSAGE);
    }

    public void atualizar(String cpf, String nomeCompleto, String idade) {
        try {
            if (validaCpf(cpf) && validaIdade(idade)) {
                new ClientesDAO().atualizar(cpf, nomeCompleto, idade);
                exibirMensagem("Cliente atualizado", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            } else {
                exibirMensagem("Verifique se os dados escritos estão corretos e tente novamente!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception err) {
            exibirMensagem("Erro ao atualizar cliente. Verifique os dados e tente novamente.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean validaCpf(String cpf) {
        return cpf.matches("[0-9]+") && cpf.length() == 11;
    }

    public boolean validaIdade(String idade) {
        return idade.matches("[0-9]+") && Integer.parseInt(idade) > 0;
    }

    private void exibirMensagem(String mensagem, int messageType) {
        JOptionPane.showMessageDialog(null, mensagem, null, messageType);
    }
}

