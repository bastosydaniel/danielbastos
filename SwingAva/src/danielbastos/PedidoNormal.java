package danielbastos;


public class PedidoNormal extends Pedido{
	/**
	 * @param valor Valor a subtrair
	 */
	public boolean diminuirValor (double valor) {
		double novoValor = this.saldo - valor;
		if (novoValor < 0) 
			return false;
		this.saldo = novoValor;
		return true;
	}
}
