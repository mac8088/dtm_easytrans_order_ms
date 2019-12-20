package net.atos.demo.web.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.atos.demo.domain.Order;
import net.atos.demo.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping("/buySth")
	@ResponseBody
	public String buySomethingProxy(@RequestParam int userId, @RequestParam int money) {
		Optional<Order> order = orderService.buySomething(userId, money);
		if (order.isPresent()) {
			return order.get().getOrderId() + " freezed " + order.get().getMoney();
		} else {
			return "error occurred!";
		}

	}
}
