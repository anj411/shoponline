package com.shoponline.service;

import java.security.Principal;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoponline.model.ShippingAddress;
import com.shoponline.model.User;

@Service
public class HomeServiceImpl implements HomeService 
{
	@Autowired
	UserService userService;

	@Override
	public void postAddress(ShippingAddress shippingAddress, Principal principal) {
		JSONObject json = new JSONObject();

		json.put("name", shippingAddress.getName());
		json.put("mobileNumber", shippingAddress.getMobileNumber());
		json.put("pinCode", shippingAddress.getPinCode());
		json.put("address", shippingAddress.getAddress());
		json.put("city", shippingAddress.getCity());
		json.put("state", shippingAddress.getState());

		String username = principal.getName();
		User user = userService.findUserByUserName(username);
		user.setUserAddress(json.toString());	
		userService.saveUser(user);		  
	}

	@Override
	public ShippingAddress payment(Principal principal) {
		String username = principal.getName();
		User user = userService.findUserByUserName(username);

		JSONParser parser = new JSONParser();
		ShippingAddress shippingAddress = new ShippingAddress();

		try {
			JSONObject json = (JSONObject) parser.parse(user.getUserAddress());

			shippingAddress.setName(json.get("name").toString());
			shippingAddress.setMobileNumber(json.get("mobileNumber").toString());
			shippingAddress.setPinCode(json.get("pinCode").toString());
			shippingAddress.setAddress(json.get("address").toString());
			shippingAddress.setCity(json.get("city").toString());
			shippingAddress.setState(json.get("state").toString());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return shippingAddress;
	}
	
	
	
}
