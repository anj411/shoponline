package com.shoponline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoponline.dao.MenuRepository;
import com.shoponline.model.Menu;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	MenuRepository menuRepository;
	
	public List<Menu> getAllMenu(){
		return menuRepository.findAll();
	}
	
	public Menu addMenu(Menu menu) {
		return menuRepository.save(menu);
	}
	
	public Menu getMenuById(int menuId) {
		Optional<Menu> menu = menuRepository.findById(menuId);
		return menu.get();
	}
	
	public void initMenu(){
		Menu men = new Menu();
		men.setMenuName("Men");
		men.setStatus(true);
		addMenu(men);
		
		Menu women = new Menu();
		women.setMenuName("Women");
		women.setStatus(true);
		addMenu(women);
		
		Menu kids = new Menu();
		kids.setMenuName("Kids");
		kids.setStatus(true);
		addMenu(kids);
		
		Menu homeLiving = new Menu();
		homeLiving.setMenuName("Home & Living");
		homeLiving.setStatus(true);
		addMenu(homeLiving);
	}
}
