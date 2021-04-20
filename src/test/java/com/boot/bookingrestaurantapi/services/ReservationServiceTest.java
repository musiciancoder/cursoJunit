package com.boot.bookingrestaurantapi.services;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;


public class ReservationServiceTest {
	

	
	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	private static final Restaurant RESTAURANT = new Restaurant();
	
	private static final List<Turn> TURN_LIST = new ArrayList<>();
	
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT =Optional.of(RESTAURANT);
	
	private final static Date DATE = new Date();
	private final static Long PERSON = 30L;
	private final static Long RESTAURANT_ID = 50L;
	private final static Long TURN_ID = 5L;
	
	private final static String NAME = "Burger";
	private final static String DESCRIPTION = "Grandes hamburguesas";
	private final static String ADDRESS ="Av x";
	private final static String IMAGE ="www.image.com";

	
	
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
		
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
		
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
	}
	
	@Test
	public void createReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		
	}
	

}
