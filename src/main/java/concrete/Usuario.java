package concrete;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table
public class Usuario {

	@Id
	@GeneratedValue
	private long id;
	
	private String email;
	private String nome;
	private String password;

	@OneToMany(mappedBy = "usuario")
	private Set<Telefone> telefones = new HashSet<>();

	@CreatedDate
	@Column(insertable=true, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@LastModifiedDate
	@Column(insertable=true, updatable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@Column(name="last_login", insertable=true, updatable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	
	@Column(insertable=true, updatable=false)
	private String token;
	
	Usuario() {}
	
	public Usuario(String email, String nome, String password) {
		this.email = email;
		this.nome = nome;
		this.password = password;
	}

	public long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Set<Telefone> getTelefones() {
		return telefones;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public Date getModified() {
		return modified;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(Date loginDate) {
		lastLogin = loginDate;
	}
	
	public String getToken() {
		return token;
	}
	
	@PrePersist
	protected void onCreate() {
		lastLogin = modified = created = new Date();
		token = UUID.randomUUID().toString();
	}
	
	@PreUpdate
	protected void onUpdate() {
		modified = new Date();
	}
}
