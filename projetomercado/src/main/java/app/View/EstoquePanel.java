package app.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import app.Connection.EstoqueDAO;
import app.Control.EstoqueControl;
import app.Model.Estoque;

public class EstoquePanel extends JPanel {

    private JButton cadastraProduto, editaProduto, apagaProduto;
    private JTextField inputNomeProduto, inputPreco, inputQuantidade, inputId;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane scrollPane;

    public EstoquePanel() {
        super();

        setLayout(new BorderLayout());

        // Panel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputId = new JTextField(10);
        inputNomeProduto = new JTextField(10);
        inputPreco = new JTextField(10);
        inputQuantidade = new JTextField(10);

        inputPanel.add(new JLabel("Código de barras:"));
        inputPanel.add(inputId);
        inputPanel.add(new JLabel("Nome do Produto:"));
        inputPanel.add(inputNomeProduto);
        inputPanel.add(new JLabel("Preço:"));
        inputPanel.add(inputPreco);
        inputPanel.add(new JLabel("Quantidade:"));
        inputPanel.add(inputQuantidade);

        // Panel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        cadastraProduto = new JButton("Cadastrar");
        editaProduto = new JButton("Editar");
        apagaProduto = new JButton("Excluir");

        buttonPanel.add(cadastraProduto);
        buttonPanel.add(editaProduto);
        buttonPanel.add(apagaProduto);

        // Tabela
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Código", "Nome do Produto", "Preço (R$)", "Quantidade"});
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        // Adicionando os componentes ao painel principal
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Estilização dos botões
        apagaProduto.setBackground(Color.red);
        cadastraProduto.setBackground(Color.green);
        editaProduto.setBackground(Color.yellow);

        // Tratamento de eventos
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputId.setText(String.valueOf(table.getValueAt(linhaSelecionada, 0)));
                    inputNomeProduto.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputPreco.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputQuantidade.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        EstoqueControl control = new EstoqueControl(produtos, tableModel, table);

        // Cadastrar um produto
        cadastraProduto.addActionListener(e -> {
            if (!inputNomeProduto.getText().isEmpty() && !inputPreco.getText().isEmpty()
                    && !inputQuantidade.getText().isEmpty() && !inputId.getText().isEmpty()) {

                control.cadastrarProduto(Integer.parseInt(inputId.getText()), inputNomeProduto.getText(),
                        inputPreco.getText(),
                        inputQuantidade.getText());

                inputNomeProduto.setText("");
                inputPreco.setText("");
                inputQuantidade.setText("");
            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha os campos corretamente para cadastrar um produto!!", null,
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Editar um produto
        editaProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja atualizar as informações deste produto?",
                        "Editar", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    control.atualizar(Integer.parseInt(inputId.getText()), inputNomeProduto.getText(),
                            inputPreco.getText(),
                            inputQuantidade.getText());
                }
            }
        });

        // Apagar um produto
        apagaProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja excluir este produto?",
                        "Excluir", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    control.apagar(Integer.parseInt(inputId.getText()));
                    inputNomeProduto.setText("");
                    inputPreco.setText("");
                    inputQuantidade.setText("");
                }
            }
        });

        // Atualiza a tabela
        atualizarTabela();
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            tableModel.addRow(new Object[]{produto.getId(), produto.getNomeDoProduto(), produto.getPreco(),
                    produto.getQuantidade()});
        }
    }
}

