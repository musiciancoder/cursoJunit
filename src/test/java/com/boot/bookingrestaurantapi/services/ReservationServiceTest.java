package com.boot.bookingrestaurantapi.services;



import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;


public class ReservationServiceTest {
	

	
	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	
	private final static Date DATE = new Date();
	private final static Long PERSON = 30L;
	private final static Long RESTAURANT_ID = 50L;
	private final static Long TURN_ID = 5L;
	
	//En los servicios se mockean los repositorios, a diferencia de los controllers donde se mockean los servicios
	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private TurnRepository turnRepository;

	@Mock
	private ReservationRespository reservationRepository;
	
	@InjectMocks
	private ReservationServiceImpl reservationServiceImpl;

	
	
	@Before//antes de ejecutar los tests
	public void init () throws BookingException {
		MockitoAnnotations.initMocks(this);
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
	}
	
	@Test
	public void createReservationTest() throws BookingException {
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		
	}
	

}
