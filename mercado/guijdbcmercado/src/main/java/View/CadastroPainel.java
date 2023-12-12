package View;

import Model.ListaClientes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CadastroPainel extends JPanel {

    private JRadioButton clienteRadioButton;
    private JRadioButton funcionarioRadioButton;

    private JLabel nomeLabel;
    private JTextField nomeTextField;
    private JLabel cpfLabel;
    private JTextField cpfTextField;
    private JLabel salarioLabel;
    private JTextField salarioTextField;
    private JButton cadastrarButton;

    // Lista para armazenar clientes
    private ArrayList<ListaClientes> listaClientes;

    public CadastroPainel() {
        super();

        setLayout(new BorderLayout());
        Font font = new Font("Arial Black", Font.PLAIN, 16);

        // Inicializa a lista de clientes
        listaClientes = new ArrayList<>();

        // Adiciona RadioButtons para escolher entre Cliente e Funcionário
        clienteRadioButton = new JRadioButton("Cliente");
        clienteRadioButton.setFont(font);
        ImageIcon icoCliente = new ImageIcon("guijdbcmercado\\src\\main\\resources\\Icons\\Documento.png");
        Image ImageCliente = icoCliente.getImage();
        Image scaledImageCliente = ImageCliente.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
        icoCliente = new ImageIcon(scaledImageCliente);
        clienteRadioButton.setIconTextGap(30);
        clienteRadioButton.setIcon(icoCliente);
        clienteRadioButton.setPreferredSize(new Dimension(150, 100));

        funcionarioRadioButton = new JRadioButton("Funcionário");
        funcionarioRadioButton.setFont(font);
        ImageIcon iconFuncionario = new ImageIcon("guijdbcmercado\\src\\main\\resources\\Icons\\Documento.png");
        Image ImageFuncionario = iconFuncionario.getImage();
        Image scaledImageFuncionario = ImageFuncionario.getScaledInstance(100, -1, Image.SCALE_SMOOTH);
        iconFuncionario = new ImageIcon(scaledImageFuncionario);
        funcionarioRadioButton.setIconTextGap(30);
        funcionarioRadioButton.setIcon(iconFuncionario);
        funcionarioRadioButton.setPreferredSize(new Dimension(150, 100));

        ButtonGroup group = new ButtonGroup();
        group.add(clienteRadioButton);
        group.add(funcionarioRadioButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(clienteRadioButton);
        radioPanel.add(funcionarioRadioButton);

        add(radioPanel, BorderLayout.NORTH);

        // Adiciona listener aos RadioButtons
        clienteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarFormularioCliente();
            }
        });

        funcionarioRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarFormularioFuncionario();
            }
        });

        // Componentes iniciais
        nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField(20);
        cpfLabel = new JLabel("CPF:");
        cpfTextField = new JTextField(20);
        salarioLabel = new JLabel("Salário:");
        salarioTextField = new JTextField(20);
        cadastrarButton = new JButton("Cadastrar");

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(nomeLabel);
        formPanel.add(nomeTextField);
        formPanel.add(cpfLabel);
        formPanel.add(cpfTextField);
        formPanel.add(salarioLabel);
        formPanel.add(salarioTextField);
        formPanel.add(new JLabel()); // Espaço vazio
        formPanel.add(cadastrarButton);

        add(formPanel, BorderLayout.CENTER);

        // Adiciona listener ao botão de cadastrar
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });
    }

    private void atualizarFormularioCliente() {
        // Lógica para atualizar os campos do formulário para um novo cadastro de cliente
        // ...

        // Exemplo:
        // Limpar campos existentes ou adicionar novos campos específicos para clientes
        salarioLabel.setVisible(false);
        salarioTextField.setVisible(false);
        revalidate();
        repaint();
    }

    private void atualizarFormularioFuncionario() {
        // Lógica para atualizar os campos do formulário para um novo cadastro de funcionário
        // ...

        // Exemplo:
        // Limpar campos existentes ou adicionar novos campos específicos para funcionários
        salarioLabel.setVisible(true);
        salarioTextField.setVisible(true);
        revalidate();
        repaint();
    }

    private void cadastrar() {
        // Obtém os dados do formulário
        String nome = nomeTextField.getText();
        String cpf = cpfTextField.getText();
        String salario = salarioTextField.getText();

        // Cria um novo cliente e adiciona à lista
        ListaClientes novoCliente = new ListaClientes(nome, cpf, salario, salario, salario);
        listaClientes.add(novoCliente);

        // Limpa os campos do formulário
        nomeTextField.setText("");
        cpfTextField.setText("");
        salarioTextField.setText("");

        // Exibe uma mensagem de sucesso (ou faça o que for apropriado no seu contexto)
        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
    }

    // ... Outros métodos conforme necessário ...

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new CadastroPainel());
                frame.setSize(400, 300);
                frame.setVisible(true);
            }
        });
    }
}
