package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.ReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.ReservationService;


public class ReservationControllerTest {
	
	private static final String SUCCESS_STATUS = "Success";
	
	private static final String SUCCESS_CODE = "200 OK";
	
	private static final String OK = "OK";
	
	private static final Long RESTAURANT_ID = 1L;
	
	private static final Date DATE = new Date();
	
	private static final Long PERSON = 1L;
	
	private static final Long TURN_ID = 1L;
	
	private static final String LOCATOR = "Burger2";
	
	
	
	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	
	@Mock//inyeccion de dependencias a mockear
	ReservationService reservationService;
	
	@InjectMocks//la clase que se desea mockear
	ReservationController reservationController; 
	
	@Before//antes de ejecutar los tests
	public void init () throws BookingException {
		MockitoAnnotations.initMocks(this);
		//Los cuatro campos que devuelve la respuesta en el JSON en las proximas 4 lineas
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(this.RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
		//Mockeamos el servicio
		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR); //porque el método original devuelve un String localizador BookingResponse<String> createReservation
		}
	
	@Test
	public void createReservationTest() throws BookingException {
		BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);
		//con assertEquals se trata de simular los metodos que setean las propiedades de la respuesta
		assertEquals(response.getStatus(), SUCCESS_STATUS); 
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LOCATOR);
	}
	
}
