package com.boot.bookingrestaurantapi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Board;
import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.services.impl.RestaurantServiceImpl;

public class RestaurantServiceTest {
	
	private static final Long RESTAURANT_ID = 1L;
	
	private static final String NAME = "Burger King";
	
	private static final String DESCRIPTION = "Todo tipo de hamburguesas";
	
	private static final String ADDRESS = "Av. Siempreviva";
	
	private static final String IMAGE = "www.image.com";
	
	//Notar que a diferencia de los controladores donde se simulaban respuestar rest, en los servicios se simulan entidades como por ejemplo Restaurant 
	public static final Restaurant RESTAURANT = new Restaurant();
	public static final List <Turn> TURN_LIST = new ArrayList<>();
	public static final List <Board> BOARD_LIST = new ArrayList<>();
	public static final List <Reservation> RESERVATION_LIST = new ArrayList<>();
	
	@Mock
	RestaurantRepository restaurantRepository;
	
	@InjectMocks
	RestaurantServiceImpl restaurantServiceImpl;
	
	@Before//antes de ejecutar los tests
	public void init () throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);
		RESTAURANT.setBoards(BOARD_LIST);
		RESTAURANT.setReservations(RESERVATION_LIST);
	}
	
	//con esto probamos hasta antes del orElseThrow() en getRestaurantEntity(), es decir probamos cuando la respuesta trae algo
	@Test
	public void getRestaurantByIdTest() throws BookingException { 
		//notar que aunque el metodo getRestaurantById no lleva findById() , getRestaurantEntity() si lo lleva y por tal razón se mockea (en la siguiente linea)
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));//porque el findById en interface RestaurantRepository es un optional
		restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);
	}
	
	//con esto probamos el orElseThrow() en getRestaurantEntity(), es decir probamos cuando la respuesta viene vacia
	@Test (expected = BookingException.class) //para analizar la falla de un test es recomendable escribir la excepcion que se espera que arroje, en este caso expected = BookingException.class, y al final fail ()
	public void getRestaurantByIdTestError() throws BookingException {  //el nombre getRestaurantByIdTestError() da lo mismo
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());// el optional viene vacio
		restaurantServiceImpl.getRestaurantById(RESTAURANT_ID);
		fail(); //que falle
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		final Restaurant restaurant = new Restaurant();
		Mockito.when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant));
		final List<RestaurantRest> response = restaurantServiceImpl.getRestaurants();
		assertNull(response);
		assertFalse(response.isEmpty());
		assertEquals(response.size(), 1);
	}
	
}
