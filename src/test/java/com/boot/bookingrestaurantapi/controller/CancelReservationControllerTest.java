package com.boot.bookingrestaurantapi.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.CancelReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.CancelReservationService;

public class CancelReservationControllerTest {

	private static final String SUCCESS_STATUS = "Success";

	private static final String SUCCESS_CODE = "200 OK";

	private static final String OK = "OK";

	private static final String RESERVATION_DELETED = "LOCATOR_DELETED";
	private static final String LOCATOR = "Burger 7";

	@Mock // En el controlador se mockean los servicios
	CancelReservationService cancelReservationService;

	@InjectMocks
	CancelReservationController cancelReservationController;

	@Before // antes de ejecutar los tests
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		// en ThenReturn va siempre lo que devuelve el metodo de la clase que implementa el servicio, en este caso clase CancelReservationServiceImpl
		// se devuelve un RESERVATION_DELETED porque la clase CancelReservationServiceImpl el metodo deleteReservation devuelve un marcador "LOCATOR_DELETED"
		Mockito.when(cancelReservationService.deleteReservation(LOCATOR)).thenReturn(RESERVATION_DELETED); //aca es global de la clase esto tambien podria haber ido en el metodo y entonces seria local
		

	}

	@Test
	public void deleteReservationTest() throws BookingException {
		final BookingResponse<String> response = cancelReservationController.deleteReservation(LOCATOR);
		// con assertEquals se trata de simular los metodos que setean las propiedades
		// de la respuesta
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESERVATION_DELETED);

	}

}
