package br.com.guest.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe responsável por persistir hospedes.
 * @author Alysson Júnio da Silva Tostes
 * @version 1
 * @since 1.0.0
 */
@Entity
@Table(name="TB_GUEST")
public class Guest implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name, document, telephone;
	
	/**
	 * Método responsável por retornar o código do hóspede.
	 * @return Código do hospede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public long getId() {
		return id;
	}

	/**
	 * Método responsável por alterar o código do hóspede.
	 * @param id Alterar o código do hóspede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Método responsável por alterar o nome do hóspede.
	 * @return Nome do hóspede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Método responsável por alterar o nome do hóspede.
	 * @param name Novo nome do hóspede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Método responsável por retornar o documento do hóspede.
	 * @return Documento do hóspede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * Método responsável por alterar o documento do hóspede.
	 * @param document Novo documento do hóspede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	/**
	 * Método responsável por retornar o telefone do hóspede.
	 * @return Telephone do hóspede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Método responsável por alterar o telefone do hóspede.
	 * @param telephone Novo telefone do hóspede.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Sobreposição do método responsável por retornar o objeto.
	 * @return Objeto criado e pré-formatado para impressão.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@Override
	public String toString() {
		return "Guest [id=" + id + ", name=" + name + ", document=" + document + ", telephone=" + telephone + "]";
	}
}
