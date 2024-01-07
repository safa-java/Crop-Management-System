package com.agcltr.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agcltr.cart.external.CropServiceFeignClient;
import com.agcltr.cart.external.UserServiceFeignClient;
import com.agcltr.cart.repo.CartRepository;
import com.agcltr.cart.entity.Cart;
import com.agcltr.cart.entity.CartItem;
import com.agcltr.cart.entity.Crops;
import com.agcltr.cart.entity.User;
import com.agcltr.cart.exception.CartNotFoundException;
import com.agcltr.cart.exception.NotExistsException;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CropServiceFeignClient cropServiceFeignClient;

	@Autowired
	private UserServiceFeignClient userServiceFeignClient;

	@Autowired
	private CartRepository crepo;

	@Override
	public List<Cart> getCartItems() {
		return crepo.findAll();
	}

	@Override
	public Cart addToCart(CartItem item, String cropId, String userName) throws NotExistsException {
		Crops crop = cropServiceFeignClient.getCropById(cropId);
		User user = userServiceFeignClient.fetchByEmail(userName);

		Cart userCart = crepo.findByUserName(user.getUserEmail());

		if (userCart == null) {
			userCart = new Cart();
			String cartId = UUID.randomUUID().toString();
			userCart.setId(cartId);
			userCart.setUserName(user.getUserEmail());
			userCart.setItems(new ArrayList<CartItem>());

		}

		int quantityRequired = item.getQuantity();
		
		
		int stockAvailable = crop.getStockAvailable();
		if (stockAvailable < quantityRequired) {
			throw new NotExistsException("Insufficient quantity of the crop " + crop.getCropName()+ "\nAvailable Quantity=" + stockAvailable);

		} 
		

		boolean itemExists = false;

		List<CartItem> cartItems = userCart.getItems();

		for (CartItem cartItem : cartItems) {

			if (cartItem.getCropId().equals(cropId)) {
				itemExists = true;
				cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
				cartItem.setSubTotal(cartItem.getSubTotal() + (crop.getPricePerKg() * item.getQuantity()));
				break;

			}

		}

		if (!itemExists) {
			item.setCropId(cropId);
			item.setCropName(crop.getCropName());
			item.setSubTotal(crop.getPricePerKg() * quantityRequired);
			cartItems.add(item);

		}
		
		

		crop.setStockAvailable(stockAvailable - quantityRequired);
		cropServiceFeignClient.updateCrops(cropId, crop);

		userCart.setItems(cartItems);
		userCart.setTotalPrice(calculateTotalCost(userCart));
		crepo.save(userCart);
		return userCart;

	}

	
	@Override
	public Cart removeFromCart(String cartId, String cropId, int quantity) throws NotExistsException {
		    Optional<Cart> cart = crepo.findById(cartId);
		    if (cart == null) {
		        throw new NotExistsException("Cart not found for ID: " + cartId);
		    }
		    Cart userCart = cart.get();

		    Crops crop = cropServiceFeignClient.getCropById(cropId);

//		    if (crop == null) {
//		        throw new NotExistsException("Crop not found for ID: " + cropId);
//		    }

		    CartItem cartItemToRemove = null;

		    for (CartItem cartItem : userCart.getItems()) {
		        if (cartItem.getCropId().equals(cropId)) {
		            cartItemToRemove = cartItem;
		            break;
		        }
		    }

		    if (cartItemToRemove == null) {
		        throw new NotExistsException("Item not found in the cart.");
		    }

		    int currentQuantity = cartItemToRemove.getQuantity();

		    if (quantity >= currentQuantity) {
		        // Remove the cart item if the requested quantity is greater than or equal to the current quantity
		        userCart.getItems().remove(cartItemToRemove);
		    } else {
		        // Reduce the quantity, update the subtotal, and calculate the new total price
		        cartItemToRemove.setQuantity(currentQuantity - quantity);
		        cartItemToRemove.setSubTotal(cartItemToRemove.getSubTotal() - (crop.getPricePerKg()* quantity));
		    }

		    // Update the stock of the crop
		    crop.setStockAvailable(crop.getStockAvailable() + quantity);
		    cropServiceFeignClient.updateCrops(cropId, crop);

		    
		    userCart.setTotalPrice(calculateTotalCost(userCart));

		    // Save the updated cart
		    crepo.save(userCart);

		    return userCart;
		


	}

	public double calculateTotalCost(Cart cart) {

		double total = 0.0;
		for (CartItem item : cart.getItems()) {
			total += item.getSubTotal();
		}
		return total;
	}

	@Override
	public Cart viewCart(String id) throws CartNotFoundException {

		Optional<Cart> c = crepo.findById(id);
		if (c.isEmpty()) {
			throw new CartNotFoundException("Cart does not exists");
		}
		Cart cart = c.get();
		return cart;
	}

	@Override
	public String deleteCart(String id) throws CartNotFoundException {

		Optional<Cart> c = crepo.findById(id);
		if (c.isEmpty()) {
			throw new CartNotFoundException("Cart does not exists");
		}

		crepo.deleteById(id);
		return "Cart successfully deleted";
	}

	@Override
	public Cart updateCart(String id, Cart cart) throws CartNotFoundException {
		
		Optional<Cart> c = crepo.findById(id);
		if(c.isEmpty()) {
			throw new CartNotFoundException("Cart does not exists");
		}
		Cart c1 = c.get();
		c1.setItems(cart.getItems());
		c1.setTotalPrice(calculateTotalCost(cart));
		crepo.save(c1);
		return c1;
	}

	@Override
	public List<Cart> viewAllCarts() {
		
		List<Cart> cartsList = crepo.findAll();
		return cartsList;
	}
	@Override
	public Cart viewByUsername(String username) throws CartNotFoundException {

		Cart cart = crepo.findByUserName(username);
		if (cart != null) {
			return cart;
		}
		throw new CartNotFoundException("Cart does not exists");
	}

	

}
