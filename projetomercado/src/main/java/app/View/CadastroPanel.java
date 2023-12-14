package app.View;

import javax.swing.*;

import app.Connection.ClientesDAO;

import java.awt.*;



public class CadastroPanel extends JPanel {

    private JButton cadastraCliente;
    private JTextField inputCpf, inputNome, inputIdade;

    public CadastroPanel() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(new JLabel("Cadastro de Clientes VIP"));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputCpf = new JTextField(7);
        inputNome = new JTextField(4);
        inputIdade = new JTextField(12);

        inputPanel.add(new JLabel("Digite o CPF do cliente:"));
        inputPanel.add(inputCpf);
        inputPanel.add(new JLabel("Digite o nome completo do cliente:"));
        inputPanel.add(inputNome);
        inputPanel.add(new JLabel("Digite a idade do cliente:"));
        inputPanel.add(inputIdade);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cadastraCliente = new JButton("Cadastrar");
        buttonPanel.add(cadastraCliente);

        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        cadastraCliente.addActionListener(e -> {
            if (!inputCpf.getText().isEmpty() && !inputNome.getText().isEmpty()
                    && !inputIdade.getText().isEmpty()) {
                new ClientesDAO().cadastrar(inputCpf.getText(), inputNome.getText(), inputIdade.getText());
                JOptionPane.showMessageDialog(null, "Cliente cadastrado!");
            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha os campos corretamente para cadastrar um cliente!!", null,
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
