package app;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.Connection.ClientesDAO;
import app.Connection.EstoqueDAO;
import app.Connection.VendasDAO;
import app.Model.Cliente;
import app.Model.Estoque;
import app.View.CadastroPanel;

public class Caixa extends JFrame {
    // Atributos
    private JTextField inputCPF, valorFinal, quantidadeDeItens, inputProduto;
    private JButton verificaCPF, cadastrarNovoCliente, fazerCompra, adicionaProduto;
    private JPanel mainPanel, cpfPanel, buttonPanel, produtoPanel, totalPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos;
    private List<Cliente> clientes;
    private List<Estoque> listaDeCompra = new ArrayList<>();
    private JScrollPane jSPane;
    private boolean isClienteVIP;
    private boolean produtoNaoEncontrado = true;
    private int contProduto = 1;
    private int quantidadeTotal = 0;
    private double valorTotal = 0, descontoVip = 0;

    // Construtor
    public Caixa() {
        // Inicializando componentes
        jSPane = new JScrollPane();
        mainPanel = new JPanel();
        totalPanel = new JPanel();
        cpfPanel = new JPanel();
        produtoPanel = new JPanel();
        buttonPanel = new JPanel();
        inputCPF = new JTextField(20);
        inputProduto = new JTextField(20);
        valorFinal = new JTextField();
        quantidadeDeItens = new JTextField();

        verificaCPF = new JButton("Verificar CPF");
        verificaCPF.setBackground(Color.YELLOW);
        cadastrarNovoCliente = new JButton("Cadastrar novo cliente");
        cadastrarNovoCliente.setBackground(Color.gray);
        adicionaProduto = new JButton("Adicionar Produto");
        adicionaProduto.setBackground(Color.cyan);
        fazerCompra = new JButton("Fazer compra");

       
        // Adicionando o mainPanel ao JFrame
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        cpfPanel.setLayout(new GridLayout(1, 4, 5, 4));
        cpfPanel.add(inputCPF);
        cpfPanel.add(verificaCPF);

        mainPanel.add(cpfPanel);

        produtoPanel.setLayout(new GridLayout(1, 2, 4, 5));
        produtoPanel.add(inputProduto);
        produtoPanel.add(adicionaProduto);
        mainPanel.add(produtoPanel);

        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Produto", "Quantidade", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        mainPanel.add(jSPane);

        totalPanel.setLayout(new GridLayout(1, 3));
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(quantidadeDeItens);
        totalPanel.add(valorFinal);
        valorFinal.setEditable(false);
        quantidadeDeItens.setEditable(false);
        atualizaQuantidadeEValorTotal();
        mainPanel.add(totalPanel);

        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(fazerCompra);
        fazerCompra.setBackground(Color.GREEN);
        mainPanel.add(buttonPanel);
         buttonPanel.add(cadastrarNovoCliente);
      

        // Tratamento de eventos
        verificaCPF.addActionListener(e -> {
            isClienteVIP = validaCpf(inputCPF.getText());
            System.out.println(isClienteVIP);
            if (verificaCPF.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Campo vazio");
            }
           
           else if (isClienteVIP == true) {
                JOptionPane.showMessageDialog(null, "Cliente VIP!");
                JOptionPane.showMessageDialog(null, "Cliente VIP recebe um desconto de 20% do valor total da compra!");
                
            }  else{
                 JOptionPane.showMessageDialog(null, "Cliente não cadastrado!");
            }
            atualizaQuantidadeEValorTotal();
        });

        adicionaProduto.addActionListener(e -> {

            if (!inputProduto.getText().isEmpty()) {
                buscarProduto(Integer.parseInt(inputProduto.getText()));
                inputProduto.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "Mercado",
                        JOptionPane.WARNING_MESSAGE);
            }

        });

        cadastrarNovoCliente.addActionListener(e -> {
            cadastraNovoCliente();
        });

        fazerCompra.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new VendasDAO().cadastrar(inputCPF.getText(), valorFinal.getText().replaceAll("R$", "").trim(),
                        "12/12/2023", String.valueOf(quantidadeTotal));
                JOptionPane.showMessageDialog(null, "Venda realizada!", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                listaDeCompra.clear();
                atualizaTabela();
                atualizaQuantidadeEValorTotal();
            }
        });
    }

    public boolean validaCpf(String cpf) {
        clientes = new ClientesDAO().listarTodos();
        for (Cliente cliente : clientes) {
            if (cpf.trim().equals(cliente.getCpf())) {
                return true;
            }
        }
        return false;
    }

    public void buscarProduto(int id) {
        contProduto = 1;
        produtos = new EstoqueDAO().listarTodos();
        
        for (Estoque produto : produtos) {
            if (produto.getId() == id) {
                produtoNaoEncontrado = false;
    
                try {
                    contProduto = Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade do produto:"));
                } catch (Exception err) {
                    System.out.println(err);
                    contProduto = 1;
                }
    
                if (contProduto > 0) {
                    int novaQuantidade = Integer.parseInt(produto.getQuantidade()) - contProduto;
    
                    if (novaQuantidade >= 0) {
                        new EstoqueDAO().atualizarQuantidade(produto.getId(), String.valueOf(novaQuantidade));
                        tableModel.addRow(new Object[] { produto.getNomeDoProduto(), contProduto, produto.getPreco() });
    
                        Estoque produtoComprado = new Estoque(produto.getNomeDoProduto(),
                                Double.parseDouble(produto.getPreco()), contProduto);
                        listaDeCompra.add(produtoComprado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Quantidade insuficiente em estoque", getTitle(),
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Quantidade inválida", getTitle(), JOptionPane.ERROR_MESSAGE);
                }
    
                break; // Não precisa continuar procurando após encontrar o produto
            }
        }
    
        if (produtoNaoEncontrado) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Mercado", JOptionPane.ERROR_MESSAGE);
        }
    
        atualizaQuantidadeEValorTotal();
    }
    
    public void atualizaTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        // Obtém os carros atualizados do banco de dados
        for (Estoque compra : listaDeCompra) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(
                    new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra(), compra.getPrecoCompra() });
        }
    }

    public void cadastraNovoCliente() {
        int res = JOptionPane.showConfirmDialog(null, "Iniciar cadastro do cliente",
                "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            JFrame janela = new JFrame();
            janela.setVisible(true);
            janela.setDefaultCloseOperation(2);
            janela.setBounds(0, 0, 500, 300);

            janela.add(new CadastroPanel());
        }
    }

    public void atualizaQuantidadeEValorTotal() {
        isClienteVIP = validaCpf(inputCPF.getText());
        valorTotal = 0;
        for (Estoque compra : listaDeCompra) {
            double soma = compra.getQuantidadeCompra() * compra.getPrecoCompra();
            valorTotal += soma;
        }
        /* xxxxxx Desconto VIP - 20% xxxxx */
        if (isClienteVIP == true) {
            valorTotal -= (0.2*valorTotal);
        }
        /* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx */
        valorFinal.setText("R$ " + String.valueOf(valorTotal));

        quantidadeTotal = 0;
        for (Estoque compra : listaDeCompra) {
            quantidadeTotal += compra.getQuantidadeCompra();
        }
        quantidadeDeItens.setText(String.valueOf(quantidadeTotal));
    }

    public void run() {
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}

