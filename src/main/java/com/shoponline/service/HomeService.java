package com.shoponline.service;

import java.security.Principal;

import com.shoponline.model.ShippingAddress;

/**
 * This is the abstraction for Home Service
 * Operations related to home such as add address or payment etc
 * will be called from here.
 */
public interface HomeService {
	/**
     * this service operation will add the address
     * corresponding to logged in user in the database.
     * @param shippingAddress
     * @param principal
     */
	public void postAddress(ShippingAddress shippingAddress, Principal principal);
	
	/**
     * this service operation will process payment of added products in the cart
     * corresponding to logged in user.
     * @param principal
     */
	public ShippingAddress payment(Principal principal);
}