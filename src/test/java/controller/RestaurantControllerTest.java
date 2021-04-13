package controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.RestaurantController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.jsons.TurnRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.RestaurantService;

public class RestaurantControllerTest {
	
	private static final Long RESTAURANT_ID = 1L;
	
	private static final String NAME = "Burger King";
	
	private static final String DESCRIPTION = "Todo tipo de hamburguesas";
	
	private static final String ADDRESS = "Av. Siempreviva";
	
	private static final String IMAGE = "www.image.com";
	
	private static final String SUCCESS_STATUS = "Success";
	
	private static final String SUCCESS_CODE = "200 OK";
	
	private static final String OK = "OK";
	
	public static final List<TurnRest> TURN_LIST  = new ArrayList<>();
	
	public static final RestaurantRest RESTAURANT_REST  = new RestaurantRest();
	
	@Mock//inyeccion de dependencias a mockear
	RestaurantService restaurantService; 
	
	@InjectMocks//la clase que se desea mockear
	RestaurantController restaurantController; 
	
	@Before//antes de ejecutar los tests
	public void init () throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT_REST.setName(NAME);
		RESTAURANT_REST.setDescription(DESCRIPTION);
		RESTAURANT_REST.setAddress(ADDRESS);
		RESTAURANT_REST.setId(RESTAURANT_ID);
		RESTAURANT_REST.setImage(IMAGE);
		RESTAURANT_REST.setTurns(TURN_LIST);
		
		//Mockeamos el servicio
		Mockito.when(restaurantService.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);
		
	}
	
	@Test
	public void getRestaurantByIdTest () throws BookingException{
		final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID); //se simula una respuesta de tipo BookingResponse<RestaurantRest>
		
		assertEquals(response.getStatus(), SUCCESS_STATUS); //response.getStatus() de la clase BookingResponse.java
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESTAURANT_REST);
		
	}
	

}
