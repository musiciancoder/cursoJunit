package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.NotFountException;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.services.impl.CancelReservationServiceImpl;


public class CancelReservationServiceTest {
	
	private  String LOCATOR = "Burger 7"; //Esto va a reemplazar al String locator de la clase CancelReservationServiceImpl
	private static final String RESERVATION_DELETED = "LOCATOR_DELETED";
	private static final Reservation RESERVATION = new Reservation();
	
	@Mock
	private ReservationRespository reservationRespository;
	
	@InjectMocks
	private CancelReservationServiceImpl cancelReservationServiceImpl;
	
	@Before//antes de ejecutar los tests
	public void init () throws BookingException {
		MockitoAnnotations.initMocks(this);
	}
	
	//prueba para reservationRespository.findByLocator(LOCATOR) exitoso, es decir hasta antes del orElseThrow()
	@Test
	public void deleteReservationOK() throws BookingException{ //Test caso en q elimina bien
		//para mockear el repositorio de un servicio, en thenReturn() debe ir el tipo que se define en la interfaz que extiende JpaRepository
		//en nuestro caso en interfaz ReservationRespository se define un optional 	Optional<Reservation> findByLocator(String locator);
		Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));  
		//el comentario anterior tambien es valido para deleteByLocator
		Mockito.when(reservationRespository.deleteByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));
		//String deleteReservation
		final String response = cancelReservationServiceImpl.deleteReservation(LOCATOR);
		assertEquals(response, RESERVATION_DELETED);
	}
	
	
	//prueba para reservationRespository.findByLocator(LOCATOR) fallido, es decir en el orElseThrow()
	@Test (expected=BookingException.class)
	public void deleteReservationNotFoundError() throws BookingException{ //Test caso en q falla eliminar la reserva
	Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.empty());  //empty para que falle
		
		Mockito.when(reservationRespository.deleteByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));
	
		final String response = cancelReservationServiceImpl.deleteReservation(LOCATOR);
		assertEquals(response, RESERVATION_DELETED);
		fail();
	}
	
	//prueba para dentro del try catch, es decir hay un fallo de servidor para dar la respuesta, por ejemplo
	@Test (expected=BookingException.class)
	public void deleteReservationInternalServerError() throws BookingException{ 
	    Mockito.when(reservationRespository.findByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));  
		Mockito.doThrow(Exception.class).when(reservationRespository.deleteByLocator(LOCATOR)); //doThrow siempre se ocupa para pasar por un try-catch
	    final String response = cancelReservationServiceImpl.deleteReservation(LOCATOR);
		assertEquals(response, RESERVATION_DELETED);
		fail();
	}

}
