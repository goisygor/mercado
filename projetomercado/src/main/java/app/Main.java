package app;

import javax.swing.JOptionPane;

import app.View.MainFrame;

public class Main {
    
        public void run() {
            String usuario = JOptionPane.showInputDialog(null, "Digite o usuário:");
            String senha = JOptionPane.showInputDialog(null, "Digite a senha:");
    
            if (validarCredenciais(usuario, senha)) {
                exibirOpcoes(usuario);
            } else {
                JOptionPane.showMessageDialog(null, "Credenciais inválidas. Encerrando o programa.");
            }
        }
    
        private boolean validarCredenciais(String usuario, String senha) {
            if ("gerente".equalsIgnoreCase(usuario) && "gerente".equals(senha)) {
                return true; // Credenciais do gerente
            } else if ("funcionario".equalsIgnoreCase(usuario) && "funcionario".equals(senha)) {
                return true; // Credenciais do funcionário
            }
            return false; // Credenciais inválidas
        }
    
        private void exibirOpcoes(String usuario) {
            if ("gerente".equalsIgnoreCase(usuario)) {
                // Abra as opções de gerenciamento para o gerente
                new MainFrame().run();
            } else if ("funcionario".equalsIgnoreCase(usuario)) {
                // Abra as opções de venda para o funcionário
                new Caixa().run();
            }
        }


    public static void main(String[] args) {
           new Main().run();
    }
}
