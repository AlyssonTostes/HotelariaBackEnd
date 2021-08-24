package br.com.guest.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guest.model.Guest;
import br.com.guest.repository.GuestRepository;

/**
 * Classe responsável por manipular hóspedes.
 * @author Alysson Júnio da Silva Tostes
 * @version 1
 * @since 1.0.0
 */
@RestController
@RequestMapping(value="/api")
public class GuestResource {

	/*
	 * Interface que será consumida.
	 */
	@Autowired
	GuestRepository guestRepository;
	
	/**
	 * Método responsável por listar todos os hóspedes salvos no banco de dados.
	 * @return Lista de {@link Guest} salvos no banco de dados
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/hospedes")
	public List<Guest> findAll(){
		return guestRepository.findAll();
	}
	
	/**
	 * Método responsável por buscar um determinado hóspede.
	 * @param id Código do hóspede que será buscado no banco de dados.
	 * @return Hóspede que o banco de dados retornou.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/hospede/{id}")
	public Guest findById(@PathVariable(value="id") long id){
		return guestRepository.findById(id);
	}
	
	/**
	 * Método responsável por buscar um determinado hóspede pelo nome, documento ou telefone.
	 * @param data Dado do hóspede que será buscado no banco de dados.
	 * @return Lista de hóspedes que o banco de dados retornou.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/hospedes/{data}")
	public List<Guest> findByData(@PathVariable(value="data") String data){
		return guestRepository.findByData(data);
	}
	
	/**
	 * Método responsável por buscar os hóspedes que já fizeram check-in mas não estão mais no hotel.
	 * @return Lista de hóspedes que o banco de dados retornou.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/hospedes-passado")
	public List<Guest> findByCheckinOld(){
		return guestRepository.findByCheckinOld();
	}
	
	/**
	 * Método responsável por buscar os hóspedes que estão no hotel.
	 * @return Lista de hóspedes que o banco de dados retornou.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/hospedes-atuais")
	public List<Guest> findByCheckinCurrent(){
		return guestRepository.findByCheckinCurrent();
	}
	
	/**
	 * Método responsável por salvar um hóspede.
	 * @param guest Hóspede que será salvo, caso o id exista, será realizado o update, e caso não exista, será executado insert com id auto incremento.
	 * @return {@link Guest} que foi criado/modificado.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@PostMapping(value="/hospede")
	public Guest save(@RequestBody Guest guest) {
		return guestRepository.save(guest);
	}
	
	/**
	 * Método responsável por deletar um hóspede.
	 * @param guest Hóspede que será deletado.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@DeleteMapping(value="/hospede")
	public void delete(@RequestBody Guest guest) {
		guestRepository.delete(guest);
	}
}
