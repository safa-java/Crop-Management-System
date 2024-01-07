package com.agcltr.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agcltr.cart.entity.Cart;
import com.agcltr.cart.entity.CartItem;
import com.agcltr.cart.exception.CartNotFoundException;
import com.agcltr.cart.exception.NotExistsException;
import com.agcltr.cart.service.CartService;
import com.agcltr.cart.service.CartServiceImpl;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
	

    @Autowired
    private CartServiceImpl cartService;

    // Define a controller method for adding items to the cart
    @PostMapping("/add/{cropId}/{userName}")
    public ResponseEntity<Cart> addToCart(
    		@RequestBody CartItem item, 
    		@PathVariable String cropId,
            @PathVariable String userName) throws CartNotFoundException, NotExistsException {
    	Cart c = cartService.addToCart(item, cropId, userName);
    return new ResponseEntity<>(c, HttpStatus.CREATED);
        
		
    }
    
    @GetMapping("/allCarts")
	public ResponseEntity<List<Cart>> viewAllCarts(){
		
		List<Cart> list = cartService.viewAllCarts();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
    
	
    @GetMapping("/view/{cartId}")
	public ResponseEntity<Cart> viewCart(@PathVariable String cartId) throws CartNotFoundException{
		
		Cart c1 = cartService.viewCart(cartId);
		return new ResponseEntity<>(c1, HttpStatus.OK);
	}
	
	@GetMapping("/viewCartByUser/{username}")
	public ResponseEntity<Cart> viewByUsername(@PathVariable String username) throws CartNotFoundException{
		
		Cart list = cartService.viewByUsername(username);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Cart> updateCart(@PathVariable String id, @RequestBody Cart cart) throws CartNotFoundException{
		
		Cart c1 = cartService.updateCart(id, cart);
		return new ResponseEntity<>(c1, HttpStatus.CREATED);
	}
	@DeleteMapping("/remove/{cartId}/{cropId}/{quantity}")
    public ResponseEntity<Cart> removeFromCart(
    		@PathVariable String cartId,
            @PathVariable String cropId, @PathVariable int quantity) throws NotExistsException {
    	Cart c = cartService.removeFromCart(cartId, cropId, quantity);
    return new ResponseEntity<>(c, HttpStatus.OK);
        
		
    }
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCart(@PathVariable String id) throws CartNotFoundException{
		
		String s1 = cartService.deleteCart(id);
		return new ResponseEntity<>(s1, HttpStatus.OK);
	}
    

}
