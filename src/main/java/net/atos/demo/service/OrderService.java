package net.atos.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiqiniu.easytrans.demos.wallet.api.WalletPayMoneyService;
import com.yiqiniu.easytrans.demos.wallet.api.WalletPayMoneyService.WalletPayRequestVO;
import com.yiqiniu.easytrans.demos.wallet.api.WalletPayMoneyService.WalletPayResponseVO;

import net.atos.demo.domain.Order;
import net.atos.demo.repository.OrderRepository;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);
    
    private final OrderRepository orderRepository;
    
	@Resource
	private WalletPayMoneyService payService;
	
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable);
    }


    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Order> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
    
    
	@Transactional
	public Optional<Order> buySomething(long userId, long money) {
		log.warn("enter buy service for user: {} with money: {}", userId, money);
		//local transaction
		Order localOrder = new Order();
		localOrder.setUserId(userId);
		localOrder.setMoney(money);
		localOrder.setCreateTime(LocalDate.now());
		Order dbOrder = save(localOrder);
		log.warn("saved order");
		long id = dbOrder.getOrderId();
		log.warn("get orderId from saved order: {}", id);
		
		//prepare TCC VO
		WalletPayRequestVO request = new WalletPayRequestVO();
		request.setUserId(userId);
		request.setPayAmount(money);

		//RPC call via PROXY
		WalletPayResponseVO pay = payService.pay(request);

		// hard coding with: if order Id MOD 3 == 0
		if (id % 3 == 0) {
			throw new RuntimeException("throw unknown exception with wrong order (MOD 3): " + id);
		}

		log.warn("OrderId {} freezed: {}", id, pay.getFreezeAmount());
		
		//warp
		return Optional.of(dbOrder);
		
	}
    
//	@Transactional
//	public String buySomething(int userId, int money) {
//		//local transaction
//		int id = saveOrderRecord(userId, money);
//		WalletPayRequestVO request = new WalletPayRequestVO();
//		request.setUserId(userId);
//		request.setPayAmount(money);
//
//		//RPC call via proxy
//		WalletPayResponseVO pay = payService.pay(request);
//
//		// hard coding with: if order Id MOD 3 == 0
//		if (id % 3 == 0) {
//			throw new RuntimeException("throw unknown exception with wrong order (MOD 3): " + id);
//		}
//
//		return "id:" + id + " freezed:" + pay.getFreezeAmount();
//	}
//	
//	private Integer saveOrderRecord(final int userId, final long money) {
//
//		final String INSERT_SQL = "INSERT INTO biz_order (order_id, user_id, money, create_time) VALUES ( nextval('SEQUENCE_GENERATOR'), ?, ?, ?);";
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		jdbcTemplate.update(
//		    new PreparedStatementCreator() {
//		    	@Override
//		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//		            PreparedStatement ps =
//		                connection.prepareStatement(INSERT_SQL, new String[] {"order_id"});
//		            ps.setInt(1, userId);
//		            ps.setLong(2, money);
//		            ps.setDate(3, new Date(System.currentTimeMillis()));
//		            return ps;
//		        }
//		    },
//		    keyHolder);
//		
//		return keyHolder.getKey().intValue();
//	}
}
