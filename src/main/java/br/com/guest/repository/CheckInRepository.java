package br.com.guest.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.guest.model.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long>{
	
	/**
	 * Método que realizará a busca por um determinado id.
	 * @param id Código do hóspede que será retornado. 
	 * @return Check-in do hóspede que foi buscado pelo id informado nos parâmentros.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	CheckIn findById(long id);
	
	/**
	 * Método responsável por buscar todos os check-ins que atendam ao parâmetro informado, referente ao hóspede.
	 * @param data Valor que será usado para pesquisar, seja ele o nome, documento ou telefone do hóspede.
	 * @return Lista de {@link CheckIn} que foi retornardo do banco.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@Query(value = "SELECT c.id, c.additional_vehicle, c.departure_date, c.entry_date, c.guest "
			+ "FROM tb_check_in c JOIN tb_guest g ON g.id = c.guest "
			+ "WHERE g.name = :data "
			+ "OR g.document = :data "
			+ "OR g.telephone = :data ", nativeQuery = true)
	List<CheckIn> findByNameGuest(@Param("data") String data);
	
	/**
	 * Método responsável por buscar o valor da última reserva de um determinado hóspede.
	 * @param id Código do hóspede que será pesquisado.
	 * @return Valor da última reserva do usuário.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@Query(value = "SELECT total_cost FROM tb_check_in WHERE id = (SELECT MAX(id) FROM tb_check_in WHERE guest = :id) ", nativeQuery = true)
	BigDecimal findLastBookingAmount(@Param("id") long id);
	
	/**
	 * Método responsável por buscar o valor da última reserva de um determinado hóspede.
	 * @param id Código do hóspede que será pesquisado.
	 * @return Valor da última reserva do usuário.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@Query(value = "SELECT SUM(total_cost) FROM tb_check_in WHERE guest = :id ", nativeQuery = true)
	BigDecimal findTotalAmountReservations(@Param("id") long id);
}
