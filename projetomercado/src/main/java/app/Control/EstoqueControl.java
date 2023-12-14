package app.Control;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.Connection.EstoqueDAO;
import app.Model.Estoque;

public class EstoqueControl {
    
    private List<Estoque> produtos;
    private DefaultTableModel tableModel;
    private JTable table;

    public EstoqueControl(List<Estoque> produtos, DefaultTableModel tableModel, JTable table) {
        this.produtos = produtos;
        this.tableModel = tableModel;
        this.table = table;
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            tableModel.addRow(new Object[] { produto.getId(), produto.getNomeDoProduto(), produto.getPreco(),
                    produto.getQuantidade() });
        }
    }

    public void cadastrarProduto(int id, String nomeDoProduto, String preco, String quantidade) {
        try {
            if (validaPreco(preco) && validaQuantidade(quantidade)) {
                Estoque produto = new Estoque(id, nomeDoProduto.trim().toUpperCase(), preco.trim().toUpperCase(),
                        String.valueOf(quantidade));
                produtos.add(produto);
                new EstoqueDAO().cadastrar(id, nomeDoProduto, preco, quantidade);
                atualizarTabela();
                exibirMensagem("Produto cadastrado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                exibirMensagem("Verifique se os dados escritos estão corretos e tente novamente!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception err) {
            exibirMensagem("Erro ao cadastrar produto. Verifique os dados e tente novamente.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void apagar(int id) {
        new EstoqueDAO().apagar(id);
        atualizarTabela();
        exibirMensagem("Produto removido!", JOptionPane.ERROR_MESSAGE);
    }

    public void atualizar(int id, String nomeDoProduto, String preco, String quantidade) {
        try {
            if (validaPreco(preco) && validaQuantidade(quantidade)) {
                new EstoqueDAO().atualizar(id, nomeDoProduto, preco, quantidade);
                exibirMensagem("Produto atualizado", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            } else {
                exibirMensagem("Verifique se os dados escritos estão corretos e tente novamente!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception err) {
            exibirMensagem("Erro ao atualizar produto. Verifique os dados e tente novamente.", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean validaPreco(String preco) {
        return preco.matches("[0-9]+([.][0-9]{1,2})?");
    }

    public boolean validaQuantidade(String quantidade) {
        return quantidade.matches("[0-9]+") && Integer.parseInt(quantidade) > 0;
    }

    private void exibirMensagem(String mensagem, int messageType) {
        JOptionPane.showMessageDialog(null, mensagem, null, messageType);
    }
}
