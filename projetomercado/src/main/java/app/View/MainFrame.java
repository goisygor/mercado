package app.View;

import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javafx.stage.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Supermercado");
        setDefaultCloseOperation(2);
        // ---------------------*
        // Aplicativo principal:
        JTabbedPane abas = new JTabbedPane();
        abas.add("Estoque", new EstoquePanel()); // Adiciona o painel de estoque ao TabbedPane
        abas.add("Relatório de Clientes", new ClientesPanel()); // Adiciona o painel de cliente ao TabbedPane
        abas.add("Relatório de Vendas", new VendasPanel()); // Adiciona o painel de vendas ao TabbedPane
        add(abas);
        // ---------------------*

        // ---------------------*
     
    }

    public void run() {
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}