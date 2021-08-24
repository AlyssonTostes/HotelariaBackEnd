package br.com.guest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.guest.model.Guest;

/**
 * Interface responsável por definir os métodos utilizados pelo resource.
 * @author Alysson Júnio da Silva Tostes
 * @version 1
 * @since 1.0.0
 */
public interface GuestRepository extends JpaRepository<Guest, Long>{

	/**
	 * Método que realizará a busca por um determinado id.
	 * @param id Código do hóspede que será retornado. 
	 * @return Hóspede que foi buscado pelo id informado nos parâmentros.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	Guest findById(long id);
	
	/**
	 * Método que realizará a busca por um nome, documento ou telefone.
	 * @param name Nome do hóspede que será consultado.
	 * @return {@link Guest} retornado do banco de dados.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@Query(value = "SELECT id, name, document, telephone "
			+ "FROM tb_guest "
			+ "WHERE name = :data "
			+ "OR document = :data "
			+ "OR telephone = :data ", nativeQuery = true)
	List<Guest> findByData(String data);
	
	/**
	 * Método que realizará a busca de hóspedes que já fizeram reserva mas não estão mais no hotel.
	 * @return {@link Guest} retornado do banco de dados.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@Query(value = "SELECT distinct g.id, g.name, g.document, g.telephone "
			+ "FROM tb_check_in c JOIN tb_guest g ON g.id = c.guest "
			+ "WHERE c.departure_date < getdate() ", nativeQuery = true)
	List<Guest> findByCheckinOld();
	
	/**
	 * Método que realizará a busca de hóspedes que estão no hotel.
	 * @return {@link Guest} retornado do banco de dados.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@Query(value = "SELECT distinct g.id, g.name, g.document, g.telephone "
			+ "FROM tb_check_in c JOIN tb_guest g ON g.id = c.guest "
			+ "WHERE c.entry_date < getdate() AND c.departure_date > getdate()", nativeQuery = true)
	List<Guest> findByCheckinCurrent();
}
