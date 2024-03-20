package com.anant.testcom.service;

import java.util.List;

import com.anant.testcom.exception.OrderException;
import com.anant.testcom.model.Address;
import com.anant.testcom.model.Order;
import com.anant.testcom.model.User;


public interface OrderService {

	public Order createOrder(User user,Address shipingAddress);
	public Order findOrderById(Long orderId) throws OrderException;
	public List<Order> usersOrderHistory(Long userId);
	public Order placedOrder(Long orderId) throws OrderException;
	public Order confirmedOrder(Long orderId) throws OrderException;
	public Order shippedOrder(Long orderId) throws OrderException;
	public Order deliveredOrder(Long orderId) throws OrderException;
	public Order canceledOrder(Long orderId) throws OrderException;
	public List<Order> getAllOrders();
	public void deleteOrder(Long orderId) throws OrderException;
}
