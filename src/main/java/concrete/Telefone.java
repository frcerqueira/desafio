package concrete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Telefone {

	@Id
	@GeneratedValue
	private long id;
	private int ddd;
	private String numero;
	
	@ManyToOne
	private Usuario usuario;
	
	Telefone() {}
	
	public Telefone(Usuario usuario, int ddd, String numero) {
		this.ddd = ddd;
		this.numero = numero;
		this.usuario = usuario;
	}
	
	public long getId() {
		return id;
	}
	
	public int getDDD() {
		return ddd;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}
