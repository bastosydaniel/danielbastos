package danielbastos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * A seguinte classe contém e descreve as informações contidas em um Pedido;
 * @author daniel.bastos
 *
 */
public abstract class Pedido {
	private int numProduto;
	private int CPF;
	private String titular;
	protected double saldo;
//	private int saldo;
	
	Pedido() {
		this.titular = "";
	}

	/**
	 * 
	 * @param numProduto Numero do produto
	 * @param CPF Cpf do cliente
	 */
	Pedido(int numProduto, int CPF) {
		this();
		this.numProduto = numProduto;
		this.CPF = CPF;
	}



	public int getNumProduto() {
		return numProduto;
	}

	public void setNumProduto(int numProduto) {
		this.numProduto = numProduto;
	}

	public int getCPF() {
		return CPF;
	}

	public void setCPF(int cPF) {
		CPF = cPF;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public double getSaldo() {
		return saldo;
	}

	public abstract boolean diminuirValor(double valor);

	/**
	 * 
	 * @param numProduto Numero do Produto
	 * @param CPF		Cpf do cliente
	 * @param nomeCliente	Nome do Cliente
	 * @param saldo			Saldo do Cliente
	 * @return	retorna booleano
	 * @throws SQLException Erro de conexão com o banco de dados
	 */
	public boolean cadastrarPedido(int numProduto, int CPF, String nomeCliente,double saldo) {

		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();

			String sql = "insert into pedido set numProduto=?, CPF=?, titular=?, saldo=?;";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, numProduto); 
			ps.setInt(2, CPF); 
			ps.setString(3, nomeCliente); 
			ps.setDouble(4, saldo);
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro!!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar a pedido: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	/**
	 * 
	 * @param numProduto
	 * @param CPF
	 * @throws Erro ao consultar a pedido
	 * @return Retorna um valor booleano
	 */
	public boolean consultarPedido(int numProduto, int CPF) {
		// Define a conexão
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
	
			String sql = "select * from pedido where numProduto=? and CPF=?";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, numProduto); 
			ps.setInt(2, CPF); 

			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { 
				return false; 
			} else {
				
				while (rs.next()) {
					this.numProduto = rs.getInt("numProduto");
					this.CPF = rs.getInt("CPF");
					this.titular = rs.getString("titular");
					this.saldo = rs.getDouble("saldo");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar a pedido: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	/**
	 * 
	 * @param numProduto Numero do Produto
	 * @param CPF		Cpf do cliente
	 * @param titular	Nome do cliente
	 * @return De acordo com o resultado da atualização será retornado um valor booleano
	 * @throws Não foi feita a atualização
	 * @throws Erro ao atualizar a conta
	 */
	public boolean atualizarConta(int numProduto, int CPF, String titular) {
		if (!consultarPedido(numProduto, CPF))
			return false;
		else {
			// Define a conexão
			Connection conexao = null;
			try {

				conexao = Conexao.conectaBanco();

				String sql = "update pedido set titular=?";

				PreparedStatement ps = conexao.prepareStatement(sql);

				ps.setString(1, titular);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a atualização!");
				else
					System.out.println("Atualização realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar a conta: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
}