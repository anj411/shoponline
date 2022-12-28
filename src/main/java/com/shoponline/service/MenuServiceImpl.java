package com.shoponline.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoponline.dao.MenuRepository;
import com.shoponline.model.Category;
import com.shoponline.model.Menu;

@Service
public class MenuServiceImpl implements MenuService {
	
	private Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class.getName());
	
	@Autowired
	MenuRepository menuRepository;
	
	public List<Menu> getAllMenu(){
		logger.info("getting all menus from DB");
		List<Menu> menuList = new ArrayList<Menu>();
		try {
			menuList = menuRepository.findAll();
			
		} catch (Exception ex) {
			logger.error("Exception occured while getting menus list from DB, Error : " + ex.getMessage());
		}

		return menuList;
	}
	
	public Menu addMenu(Menu menu) {
		logger.info("adding all menus in DB");
		Menu menus = new Menu();
		try {
			menus = menuRepository.save(menu);
			
		} catch(Exception ex) {
			logger.error("Exception occured while adding menus in DB, Error : " + ex.getMessage());
		}
		return menus;
	}
	
	public Menu getMenuById(int menuId) {
		logger.info("getting menus by menuId from DB");
		Menu menu = null;
		try {
			menu = menuRepository.findById(menuId).get();
			
		} catch(Exception ex) {
			logger.error("Exception occured while getting menu by menuId from DB, Error : " + ex.getMessage());
		}
		return menu;
	}
	
	public void initMenu(){
		logger.info("initiallizing menus");				
		Menu men = new Menu();
		
		try {
			men.setMenuName("Men");
			men.setStatus(true);
			addMenu(men);
			
		} catch(Exception ex) {
			logger.error("Exception occured while initiallizing men menus, Error : " + ex.getMessage());
		}
				
		Menu women = new Menu();
		
		try {
			women.setMenuName("Women");
			women.setStatus(true);
			addMenu(women);
			
		} catch(Exception ex) {
			logger.error("Exception occured while initiallizing women menus, Error : " + ex.getMessage());
		}
		
		
		Menu kids = new Menu();
		
		try {
			kids.setMenuName("Kids");
			kids.setStatus(true);
			addMenu(kids);
			
		} catch(Exception ex) {
			logger.error("Exception occured while initiallizing kids menus, Error : " + ex.getMessage());
		}
		
		
		Menu homeLiving = new Menu();
		
		try {
			homeLiving.setMenuName("Home & Living");
			homeLiving.setStatus(true);
			addMenu(homeLiving);
			
		} catch(Exception ex) {
			logger.error("Exception occured while initiallizing homeLiving menus, Error : " + ex.getMessage());
		}
		
	}
}
