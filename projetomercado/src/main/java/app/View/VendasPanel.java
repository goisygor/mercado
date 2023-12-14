package app.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import app.Connection.VendasDAO;
import app.Model.Venda;

public class VendasPanel extends JPanel {

    
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Venda> vendas = new ArrayList<>();
    private JScrollPane scrollPane;

    public VendasPanel() {
        super();

        setLayout(new BorderLayout());
      

        // Panel de entrada de dados
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
       

    

        // Tabela
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"id", "Cliente (CPF)", "Total", "Quantidade", "Data"});
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        // Adicionando os componentes ao painel principal
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
      

        new VendasDAO().criaTabela();
        atualizarTabela();
       
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Venda venda : vendas) {
            tableModel.addRow(new Object[]{venda.getId(), venda.getCliente(), venda.getValor(),
                    venda.getQuantidadeDeProdutos(), venda.getData()});
        }
    }
}
