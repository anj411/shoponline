package com.shoponline.service;

import java.util.List;

import com.shoponline.model.Menu;

/**
 * This is the abstraction for Menu Service
 * Operations related to Menu such as add Menu, delete Menu, get Menu, etx=c
 * are will be called from here.
 */
public interface MenuService {

	/**
     * this service operation when called returns the
     * List of menu for that Admin user
     * @return
     */
    public List<Menu> getAllMenu();

    /**
     * this service operation will add the passed menu
     * @param menu
     * @return
     */
    public Menu addMenu(Menu menu);

    /**
     * this service operation when called returns
     * a Menu object corresponding to the passed menuID.
     * @param menuId
     * @return
     */
    public Menu getMenuById(int menuId);

    public void initMenu();
}
