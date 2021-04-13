package controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.RestaurantController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.responses.BookingResponse;
import com.boot.bookingrestaurantapi.services.RestaurantService;

public class RestaurantControllerTest {
	
	private static final Long RESTAURANT_ID = 1L;
	
	private static final String SUCCESS_STATUS = "Success";
	
	private static final String SUCCESS_CODE = "200 OK";
	
	private static final String OK = "OK";
	
	@Mock//inyeccion de dependencias a mockear
	RestaurantService restaurantService; 
	
	@InjectMocks//la clase que se desea mockear
	RestaurantController restaurantController; 
	
	@Before//antes de ejecutar los tests
	public void init () throws BookingException {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getRestaurantByIdTest () throws BookingException{
		final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID); //se simula una respuesta de tipo BookingResponse<RestaurantRest>
		
		assertEquals(response.getStatus(), SUCCESS_STATUS); //response.getStatus() de la clase BookingResponse.java
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
	}
	

}
