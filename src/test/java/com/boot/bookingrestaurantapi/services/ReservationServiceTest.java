package com.boot.bookingrestaurantapi.services;



import static org.junit.Assert.fail;

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

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.exceptions.NotFountException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRespository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;


public class ReservationServiceTest {
	

	
	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	private static final Restaurant RESTAURANT = new Restaurant();
	private static final Reservation RESERVATION = new Reservation();
	
	private static final Turn TURN = new Turn();
	
	private static final List<Turn> TURN_LIST = new ArrayList<>();
	
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT =Optional.of(RESTAURANT);
	
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT_EMPTY = Optional.empty();
	
	private static final Optional<Turn> OPTIONAL_TURN = Optional.of(TURN);
	
	private static final Optional<Turn> OPTIONAL_TURN_EMPTY = Optional.empty();
	
	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();
	

	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION);
	
	
	
	private final static Date DATE = new Date();
	private final static Long PERSON = 30L;
	private final static Long RESTAURANT_ID = 50L;
	private final static Long TURN_ID = 5L;
	
	private final static String NAME = "Burger";
	private final static String DESCRIPTION = "Grandes hamburguesas";
	private final static String ADDRESS ="Av x";
	private final static String IMAGE ="www.image.com";
	
	
	private final static Long RESERVATION_ID = 40L;
	private final static String LOCATOR ="locator";
	private final static String TURNO ="turno";

	
	
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
		
		TURN.setId(TURN_ID);
		TURN.setName(NAME);
		TURN.setRestaurant(RESTAURANT);
		
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
		
		RESERVATION.setId(RESERVATION_ID);
		RESERVATION.setDate(DATE);
		RESERVATION.setLocator(LOCATOR);
		RESERVATION.setTurn(TURNO);
		RESERVATION.setPerson(PERSON);
		RESERVATION.setRestaurant(RESTAURANT);
	}
	
	//Cuando es exitoso, o sea antes de entrar a los orElseThrow()
	@Test
	public void createReservationTest() throws BookingException {
		//para restaurant not found
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		//para turn not found
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		//para finfByTurnAndRestaurantById deben agregarse las dependencias mockito-all y mockito-core en el pom, sino no toma esta parte del codigo, se la salta
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId())).thenReturn(OPTIONAL_RESERVATION_EMPTY);
		//para el try catch donde esta LOGGER
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		
	}
	
	//Cuando falla, o sea dentro de los orElseThrow()
	@Test(expected = BookingException.class)
	public void createReservationFindByIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT_EMPTY); //se le pasa el optional vacio para que falle
        reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
		
	}
	
	//Cuando falla, o sea dentro de los orElseThrow()
	@Test(expected = BookingException.class)
	public void createReservationTurnFindByIdTestError() throws BookingException {
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN_EMPTY);
		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
		
	}
	
	//Esto es para probar la parte	if (reservationRepository.findByTurnAndRestaurantId(turn.getName(), restaurantId.getId()).isPresent()) {throw new NotFountException("RESERVATION_ALREADT_EXIST", "RESERVATION_ALREADT_EXIST");
	@Test(expected = BookingException.class)
	public void createReservationTurnAndRestaurantTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId())).thenReturn(OPTIONAL_RESERVATION);
        reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		fail();
		
	}
	
	//Esto es para probar la parte del try catch
	public void createReservationInternalErrorTest() throws BookingException {
	
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);

		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
	
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId())).thenReturn(OPTIONAL_RESERVATION_EMPTY);
	
		//para q mockito lance una excepcion
		Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
		
	     reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
		
	     fail();
		
	}

	

}
