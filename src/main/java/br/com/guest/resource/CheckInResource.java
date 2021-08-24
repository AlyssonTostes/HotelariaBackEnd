package br.com.guest.resource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guest.model.CheckIn;
import br.com.guest.repository.CheckInRepository;

@RestController
@RequestMapping(value="/api")
public class CheckInResource {

	private BigDecimal dailyWorkingDays = new BigDecimal("120");
	private BigDecimal dailyWeekends = new BigDecimal("150");
	private BigDecimal garageWorkingDays = new BigDecimal("15");
	private BigDecimal garageWeekends = new BigDecimal("20"); 
	
	/*
	 * Interface que será consumida.
	 */
	@Autowired
	CheckInRepository checkInRepository;
	
	/**
	 * Método responsável por listar todos os check-in salvos no banco de dados.
	 * @return Lista de {@link CheckInn} salvos no banco de dados
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/checkins")
	public List<CheckIn> findAll(){
		return checkInRepository.findAll();
	}
	
	/**
	 * Método responsável por buscar um determinado check-in.
	 * @param id Código do check-in que será buscado no banco de dados.
	 * @return {@link CheckIn} que o banco de dados retornou.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/checkin/{id}")
	public CheckIn findById(@PathVariable(value="id") long id){
		return checkInRepository.findById(id);
	}
	
	/**
	 * Método responsável por buscar um determinado check-in.
	 * @param name Nome do hóspede que realizou o check-in.
	 * @return {@link CheckIn} que o banco de dados retornou.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value="/checkin_hospede/{data}")
	public List<CheckIn> findByNameGuest(@PathVariable(value="data") String data) {
		return checkInRepository.findByNameGuest(data);
	}

	/**
	 * Método responsável por salvar um determinado check-in no banco de dados.
	 * @param checkIn {@link CheckIn} que será salvo.
	 * @return Item que foi salvo no banco de dados, null caso nenhum seja salvo.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@PostMapping(value = "/checkin")
	public CheckIn save(@RequestBody CheckIn checkIn) {
		checkIn.setTotalCost(dailyTotals(checkIn));
		return checkInRepository.save(checkIn);
	}

	/**
	 * Método responsável por buscar o valor da última reserva de um determinado hóspede.
	 * @param id Código do hóspede que será consultado.
	 * @return Valor da última reserva.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value = "/valor_ultima_reserva/{id}")
	public BigDecimal findLastBookingAmount(@PathVariable(value="id") long id) {
		return checkInRepository.findLastBookingAmount(id);
	}

	/**
	 * Método responsável por buscar o valor total das reservas de um determinado hóspede.
	 * @param id Código do hóspede que será consultado.
	 * @return Valor total das reservas.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@GetMapping(value = "/total_reservas/{id}")
	public BigDecimal findTotalAmountReservations(@PathVariable(value="id") long id) {
		return checkInRepository.findTotalAmountReservations(id);
	}
	
	/**
	 * Método responsável por retornar o custo das diárias.
	 * @param checkIn check-in que será calculado o custo.
	 * @return Custo total da hospedagem.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 18.0.0
	 */
	private BigDecimal dailyTotals(CheckIn checkIn) {
		int workingDays = 0;																	//QUANTIDADE DE DIAS ÚTEIS DAS DIÁRIAS.
		int weekends = 0;																		//QUANTIDADE DE DIAS DE FINAIS DE SEMANAS DAS DIÁRIAS.
		
		Timestamp startDate = cleanDate(checkIn.getEntryDate());								//DEFINE A DATA INICIAL PARA O LOOP.
		Timestamp finalDate = cleanDate(checkIn.getDepartureDate());							//DEFINE A DATA FINAL PARA O LOOP.
		
		do {
			int dayWeek = getDayWeek(startDate);												//VERIFICA O DIA DA SEMANA.
			workingDays = (dayWeek >= 2 && dayWeek <= 6) ? (workingDays + 1) : workingDays;		//SE O DIA FOR DE SEGUNDA A SEXTA, ADICIONA UM.
			weekends = (dayWeek == 1 || dayWeek == 7) ? (weekends + 1) : weekends;				//SE O DIA FOR SÁBADO OU DOMINGO, ADICIONA UM.
			
			startDate = addTimestamp(startDate, 24, Calendar.HOUR);								//ADICIONA 24 HORAS NA DATA.
		}while(startDate.before(finalDate));													//ENQUANTO A DATA FOR MENOR QUE A SAIDA, FAÇA:
		
		Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));						//CRIA UM CALENDAR. 
		calendar.setTime(checkIn.getDepartureDate());											//SETA A DATA DE SAÍDA NO CALENDÁRIO.
		calendar.set(Calendar.HOUR_OF_DAY, 16);													//SETA A HORA MÁXIMA NO CALENDAR.
		calendar.set(Calendar.MINUTE, 30);														//SETA OS MINUTOS MÁXIMOS NO CALIENDAR.
		calendar.set(Calendar.SECOND, 0);														//SETA OS SEGUNDOS MÁXIMOS NO CALIENDAR.
		calendar.set(Calendar.MILLISECOND, 0);													//SETA OS MILISEGUNDOS MÁXIMOS NO CALIENDAR.		

		if(checkIn.getDepartureDate().after(new Timestamp(calendar.getTimeInMillis()))) {
			startDate = addTimestamp(startDate, 24, Calendar.HOUR);								//ADICIONA 24 HORAS NA DATA.
			int dayWeek = getDayWeek(startDate);												//VERIFICA O DIA DA SEMANA.
			workingDays = (dayWeek >= 2 && dayWeek <= 6) ? (workingDays + 1) : workingDays;		//SE O DIA FOR DE SEGUNDA A SEXTA, ADICIONA UM.
			weekends = (dayWeek == 1 || dayWeek == 7) ? (weekends + 1) : weekends;				//SE O DIA FOR SÁBADO OU DOMINGO, ADICIONA UM.
		}
		
		BigDecimal totalDailyWorkingDays = dailyWorkingDays.multiply(new BigDecimal(workingDays));	//VALOR DAS DIÁRIAS NOS DIAS ÚTEIS
		BigDecimal totalDailyWeekends = dailyWeekends.multiply(new BigDecimal(weekends));		//VALOR DAS DIÁRIAS NOS FINAIS DE SEMANA.
		BigDecimal totalGarageWorkingDays = BigDecimal.ZERO;									//VARIÁVEL PARA TOTAL DA GARAGEM NOS DIAS ÚTEIS
		BigDecimal totalGarageWeekends = BigDecimal.ZERO; 										//VARIÁVEL PARA TOTAL DA GARAGEM NOS FINAIS DE SEMANA
		
		if(checkIn.isAdditionalVehicle()) {
			totalGarageWorkingDays = garageWorkingDays.multiply(new BigDecimal(workingDays));	//CUSTO DA GARAGEM NOS DIAS ÚTEIS
			totalGarageWeekends = garageWeekends.multiply(new BigDecimal(weekends)); 			//CUSTO DA GARAGEM NOS FINAIS DE SEMANA.
		}
		
		return totalDailyWorkingDays.add(totalDailyWeekends).add(totalGarageWorkingDays).add(totalGarageWeekends);	//RETORNA A SOMATÓRIA DOS 4 VALORES.
	}

	/**
	 * Método responsável por pegar uma data e limpar o horário.
	 * @param timestamp Data que será manipulado.
	 * @return Data tratada.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	private Timestamp cleanDate(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));						//CRIA UM CALENDAR.
		calendar.setTime(timestamp);															//SETA A DATA DE ENTRADA NO CALENDÁRIO.
		calendar.set(Calendar.HOUR_OF_DAY, 0);													//SETA A HORA NO CALENDAR.
		calendar.set(Calendar.MINUTE, 0);														//SETA OS MINUTOS NO CALIENDAR.
		calendar.set(Calendar.SECOND, 0);														//SETA OS SEGUNDOS NO CALIENDAR.
		calendar.set(Calendar.MILLISECOND, 0);													//SETA OS MILISEGUNDOS NO CALIENDAR.
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * Método responsável por retornar o dia da semana de uma data.
	 * @param timestamp {@link Date} que será estraido o dia da semana.
	 * @return Dia da semana da data
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	@SuppressWarnings("static-access")
	public int getDayWeek(Timestamp timestamp) {
		if(timestamp == null)
			return -1;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(timestamp);
			return calendar.get(calendar.DAY_OF_WEEK);
		}catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Método responsável por adicionar uma data.
	 * @param timestamp {@link Timestamp} responsável por dar a data que será usada para adicionar, se for passado <code>null</code>, será usado a data atual.
	 * @param quantity {@link Integer} contendo a quantidade que será adicionado à data.
	 * @param item {@link Calendar} que será adicionado, sendo: 
	 * <blockquote>
	 * <br>(<code>Calendar.DAY_OF_MONTH</code>, <code>Calendar.MONTH</code>, <code>Calendar.YEAR</code>, <code>Calendar.HOUR</code>, <code>Calendar.MINUTE</code>, <code>Calendar.SECOND</code>, <code>Calendar.MILLISECOND</code>) 
	 * <br>com os valores respectivos: 
	 * <br>(5, 2, 1, 10, 12, 13, 14)
	 * </blockquote>
	 * @return Data adicionada pela quantidade.
	 * @author Alysson Júnio da Silva Tostes
	 * @version 1
	 * @since 1.0.0
	 */
	public Timestamp addTimestamp(Timestamp timestamp, int quantity, int item){
		if(item == 5 || item == 2 || item == 1 || item == 10 || item == 12 || item == 13) {
			Calendar calendar = Calendar.getInstance();											//CRIA O CALENDAR NA DATA ATUAL.
			calendar.setLenient(false);
			if(timestamp != null)																//SE A DATA FOR DIFERENTE DE NULL, FAÇA
				calendar.setTime(timestamp);													//SETA A DATA DO CALENDAR.
			calendar.add(item, quantity);														//PEGA O ITEM (DAY, MONTH, YEAR ...) A SUBTRAIR, MULTIPLICA POR -1 E SUBTRAI NA DATA.
			return new Timestamp(calendar.getTimeInMillis());									//RETORNA A NOVA DATA.
		}
		return null;
	}

}