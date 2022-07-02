package danielbastos;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Swing1 {
	public static void main(String[] args) {
		// Define a janela
		JFrame janela = new JFrame("Cadastro de Pedido"); 
		janela.setResizable(false); 
		janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janela.setSize(400, 300); 

		Container caixa = janela.getContentPane();
		caixa.setLayout(null);

		JLabel labelNumProduto = new JLabel("Numero do produto: ");
		JLabel labelCPF = new JLabel("CPF do Cliente: ");
		JLabel labelTitular = new JLabel("Nome do Cliente: ");
		JLabel labelValor = new JLabel("Valor: ");

		labelNumProduto.setBounds(50, 40, 150, 20); // coluna, linha, largura, tamanho
		labelCPF.setBounds(50, 80, 150, 20); 
		labelTitular.setBounds(50, 120, 100, 20); 
		labelValor.setBounds(50, 160, 100, 20); 
		// Define os input box
		JTextField jTextNumProduto = new JTextField();
		JTextField jTextCPF = new JTextField();
		JTextField jTextTitular = new JTextField();
		JTextField jTextValor = new JTextField();
		
	
		jTextNumProduto.setEnabled(true);
		jTextCPF.setEnabled(true);
		jTextTitular.setEnabled(false);
		jTextValor.setEnabled(false);

		jTextNumProduto.setBounds(200, 40, 50, 20);
		jTextCPF.setBounds(200, 80, 50, 20);
		jTextTitular.setBounds(200, 120, 150, 20);
		jTextValor.setBounds(200, 160, 150, 20);

		janela.add(labelNumProduto);
		janela.add(labelCPF);
		janela.add(labelTitular);
		janela.add(labelValor);
		janela.add(jTextNumProduto);
		janela.add(jTextCPF);
		janela.add(jTextTitular);
		janela.add(jTextValor);
		

		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(230, 80, 105, 20);
		janela.add(botaoConsultar);
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(false);
		janela.add(botaoGravar);
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(250, 200, 100, 20);
		janela.add(botaoLimpar);
	
		PedidoNormal pedido = new PedidoNormal();

		botaoConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numProduto = Integer.parseInt(jTextNumProduto.getText());
					int CPF = Integer.parseInt(jTextCPF.getText());
					botaoGravar.setEnabled(true);
					String titular;
					if (!pedido.consultarPedido(numProduto, CPF))
						titular = "";
					else
						titular = pedido.getTitular();
					jTextTitular.setText(titular);
					jTextNumProduto.setEnabled(false);
					jTextCPF.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextTitular.setEnabled(true);
					jTextTitular.requestFocus();
					jTextValor.setEnabled(true);
					jTextValor.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janela,
							"Preencha os campos agência e número da conta corretamente!!");
				}
			}
		});
		botaoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numProduto = Integer.parseInt(jTextNumProduto.getText());
				int CPF = Integer.parseInt(jTextCPF.getText());
				String titular = jTextTitular.getText().trim(); 
				Double saldo = Double.parseDouble(jTextValor.getText());
				if (titular.length()==0) {
					JOptionPane.showMessageDialog(janela, "Preencha o campo titular");
					jTextTitular.requestFocus();
				}
				else {
					if (!pedido.consultarPedido(numProduto, CPF)) {
						if (!pedido.cadastrarPedido(numProduto, CPF, titular,saldo))
							JOptionPane.showMessageDialog(janela, "Erro na inclusão do titular!");
						else
							JOptionPane.showMessageDialog(janela, "Inclusão realizada!");
					} else {
						if (!pedido.atualizarConta(numProduto, CPF, titular))
							JOptionPane.showMessageDialog(janela, "Erro na atualização do titular!");
						else
							JOptionPane.showMessageDialog(janela, "Alteração realizada!");
					}

				}
			}
		});
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextNumProduto.setText(""); 
				jTextCPF.setText(""); 
				jTextTitular.setText(""); 
				jTextValor.setText("");
				jTextNumProduto.setEnabled(true);
				jTextCPF.setEnabled(true);
				jTextTitular.setEnabled(false);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(false);
				jTextNumProduto.requestFocus(); 
			}
		});
	
		janela.setVisible(true); 
	}
}