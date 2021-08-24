package br.com.guest.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="TB_CHECK_IN")
public class CheckIn {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "guest")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Guest guest;

	@DateTimeFormat(iso = ISO.DATE, fallbackPatterns = { "yyyy-MM-ddTHH:mm:ss", "dd.MM.yyyy" })
	private Timestamp entryDate, departureDate;
	private boolean additionalVehicle;
	private BigDecimal totalCost;

	/**
	 * Método responsável por o código do check-in.
	 * @return Código do check-in.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public long getId() {
		return id;
	}

	/**
	 * Método responsável por retornar o código do check-in.
	 * @param id Código do check-in.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Método responsável por retornar o hóspede que realizará o check-in.
	 * @return Hóspede que realizará o check-in.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * Método responsável por definir o hóspede que realizará o check-in.
	 * @param guest Hóspede que realizará o check-in.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	/**
	 * Método responsável por retornar a data/hora de entrada.
	 * @return Data/hora de entrada.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public Timestamp getEntryDate() {
		return entryDate;
	}

	/**
	 * Método responsável por retornar a data/hora de entrada.
	 * @param entryDate Data/hora de entrada.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * Método responsável por retornar a data/hora de saída.
	 * @return data/hora de saída.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public Timestamp getDepartureDate() {
		return departureDate;
	}

	/**
	 * Método responsável por definir a data/hora de saída.
	 * @param departureDate Data/hora de saída.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * Método responsável por retornar se terá um adicional de veículos.
	 * @return <code>true</code> caso tenha adicional de veículos e <code>false</code> caso contrário.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public boolean isAdditionalVehicle() {
		return additionalVehicle;
	}

	/**
	 * Método responsável por definir se será utilizado um adicional de veículo.
	 * @param adicinalVeiculo <code>true</code> caso tenha adicional de veículos e <code>false</code> caso contrário.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public void setAdditionalVehicle(boolean additionalVehicle) {
		this.additionalVehicle = additionalVehicle;
	}

	/**
	 * Método responsável por retornar o custo total da hospodagem.
	 * @return Custo total da hospedagem.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}

	/**
	 * Método responsável por definir o custo total da hospedagem.
	 * @param totalCost Custo total da hospedagem.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 18.0.0
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
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
		return "CheckIn [id=" + id + ", guest=" + guest + ", entryDate=" + entryDate + ", departureDate="
				+ departureDate + ", additionalVehicle=" + additionalVehicle + ", totalCost=" + totalCost + "]";
	}
}
