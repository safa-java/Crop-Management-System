package com.agcltr.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.agcltr.cart.entity.Cart;
import com.agcltr.cart.entity.CartItem;
import com.agcltr.cart.entity.Crops;
import com.agcltr.cart.entity.User;
import com.agcltr.cart.exception.NotExistsException;
import com.agcltr.cart.external.CropServiceFeignClient;
import com.agcltr.cart.external.UserServiceFeignClient;
import com.agcltr.cart.repo.CartRepository;
import com.agcltr.cart.service.CartServiceImpl;

@SpringBootTest
class CartServiceApplicationTests {

	@InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CropServiceFeignClient cropServiceFeignClient;

    @Mock
    private UserServiceFeignClient userServiceFeignClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        

        
    }

    @Test
    public void testAddToCart() throws NotExistsException {
    	// Common test data setup
        CartItem cartItem = new CartItem();
        cartItem.setCropId("crop123");
        cartItem.setQuantity(2);

        Crops crop = new Crops();
        crop.setCropName("TestCrop");
        crop.setPricePerKg(10.0);
        crop.setStockAvailable(10);

        User user = new User();
        user.setUserEmail("test@example.com");
    	
         Mockito.when(cropServiceFeignClient.getCropById("crop123")).thenReturn(crop);
         Mockito.when(userServiceFeignClient.fetchByEmail("test@example.com")).thenReturn(user);
         Mockito.when(cartRepository.findByUserName("test@example.com")).thenReturn(null);
        

    	Cart result = cartService.addToCart(cartItem, "crop123", "test@example.com");

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals("test@example.com", result.getUserName());
        Assertions.assertEquals(1, result.getItems().size());
        Assertions.assertEquals("crop123", result.getItems().get(0).getCropId());
        Assertions.assertEquals(2, result.getItems().get(0).getQuantity());
        Assertions.assertEquals(20.0, result.getItems().get(0).getSubTotal(), 0.001); 
    }

}
