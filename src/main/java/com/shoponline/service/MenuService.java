package com.shoponline.service;

import java.util.List;

import com.shoponline.model.Menu;

public interface MenuService {

    public List<Menu> getAllMenu();

    public Menu addMenu(Menu menu);

    public Menu getMenuById(int menuId);

    public void initMenu();
}
