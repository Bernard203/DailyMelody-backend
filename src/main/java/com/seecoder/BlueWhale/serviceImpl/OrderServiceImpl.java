package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.configure.AlipayTools;
import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.*;
import com.seecoder.BlueWhale.repository.*;
import com.seecoder.BlueWhale.service.OrderService;
import com.seecoder.BlueWhale.serviceImpl.strategy.Context;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponGroupRepository couponGroupRepository;

    @Autowired
    AlipayTools alipayTools;

    @Override
    public OrderVO create(OrderVO orderVO) {
        Order order = orderVO.toPO();
        order.setUserId(securityUtil.getCurrentUser().getId());
        order.setStatus(OrderStatusEnum.UNPAID);
        order.setCreateTime(new Date());
        orderRepository.save(order);
        return wrapWithProductId(order.toVO());
    }

    @Override
    public List<OrderVO> getAllOrders() {
        User user=securityUtil.getCurrentUser();
        List<Order> orders = null;
        switch (user.getRole()){
            case CUSTOMER:
                orders = orderRepository.findByUserId(user.getId());
                break;
            case STAFF:
                Integer storeId=user.getStoreId();
                orders = orderRepository.findAll().stream()
                        .filter(x-> storeId.equals(productRepository.findById(x.getProductId()).get().getStoreId()))
                        .collect(Collectors.toList());
                break;
            case MANAGER:
            case CEO:
                orders = orderRepository.findAll();
                break;
        }
        return orders.stream().map(x->wrapWithProductId(x.toVO())).collect(Collectors.toList());
    }

    @Override
    public OrderVO getOrder(Integer id) {
        return wrapWithProductId(orderRepository.findById(id).get().toVO());
    }

    @Override
    public String pay(Integer orderId,Integer couponId) {
        User user=securityUtil.getCurrentUser();
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            throw BlueWhaleException.orderNotExists();
        }
        if(order.getStatus() != OrderStatusEnum.UNPAID){
            throw BlueWhaleException.orderStatusError();
        }
        String tradeName=String.valueOf(order.getId()).concat("-");
        if (couponId!=0){
            Coupon coupon = couponRepository.findById(couponId).get();
            CouponGroup couponGroup=couponGroupRepository.findById(coupon.getGroupId()).get();
            Product product=productRepository.findById(order.getProductId()).get();
            if (coupon.getUsed() || !coupon.getUserId().equals(user.getId()) ||
            couponGroup.getStoreId()>0 && !couponGroup.getStoreId().equals(product.getStoreId())){
                throw BlueWhaleException.couponNotAllowed();
            }
            tradeName=tradeName.concat(String.valueOf(coupon.getId()));
        }else {
            tradeName=tradeName.concat("$");
        }
        String name=productRepository.findById(order.getProductId()).get().getName();
        return alipayTools.pay(tradeName,name,calculate(orderId,couponId));
    }

    @Override
    public Boolean deliver(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            throw BlueWhaleException.orderNotExists();
        }
        if(order.getStatus() != OrderStatusEnum.UNSEND){
            throw BlueWhaleException.orderStatusError();
        }
        Product product = productRepository.findById(order.getProductId()).get();
        if (product.getStock()<order.getAmount()){
            throw BlueWhaleException.stockNotEnough();
        }
        order.setStatus(OrderStatusEnum.UNGET);
        orderRepository.save(order);
        product.setSalesAmount(product.getSalesAmount()+order.getAmount());
        product.setStock(product.getStock()-order.getAmount());
        productRepository.save(product);
        return true;
    }

    @Override
    public Boolean get(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            throw BlueWhaleException.orderNotExists();
        }
        if(order.getStatus() != OrderStatusEnum.UNGET){
            throw BlueWhaleException.orderStatusError();
        }
        order.setStatus(OrderStatusEnum.UNCOMMENT);
        orderRepository.save(order);
        return true;
    }

    @Override
    public Boolean comment(Integer orderId, String comment, Integer rating) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            throw BlueWhaleException.orderNotExists();
        }
        if(order.getStatus() != OrderStatusEnum.UNCOMMENT){
            throw BlueWhaleException.orderStatusError();
        }
        order.setContent(comment);
        order.setRating(rating);
        order.setStatus(OrderStatusEnum.DONE);
        order.setFinishTime(new Date());
        orderRepository.save(order);

        Product product = productRepository.findById(order.getProductId()).get();
        Double currentProductRating = product.getRating();
        product.setRating((currentProductRating*product.getNumber()+rating)/(product.getNumber()+1));
        product.setNumber(product.getNumber()+1);
        productRepository.save(product);

        Store store = storeRepository.findById(product.getStoreId()).get();
        Double currentStoreRating = store.getRating();
        store.setRating((currentStoreRating*store.getNumber()+rating)/(store.getNumber()+1));
        store.setNumber(store.getNumber()+1);
        storeRepository.save(store);
        return true;
    }

    @Override
    public Double calculate(Integer orderId, Integer couponId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            throw BlueWhaleException.orderNotExists();
        }
        Product product = productRepository.findById(order.getProductId()).get();
        Double origin=order.getAmount()*product.getPrice();
        if (couponId==0){
            return origin;
        }
        Coupon coupon = couponRepository.findById(couponId).get();
        CouponGroup couponGroup = couponGroupRepository.findById(coupon.getGroupId()).get();
        Context context=new Context(origin,couponGroup.getType(),couponGroup.getSatisfaction(),couponGroup.getMinus());
        return context.calculate();
    }

    private OrderVO wrapWithProductId(OrderVO orderVO){
        Integer productId=orderVO.getProductId();
        Product product=productRepository.findById(productId).get();
        orderVO.setProductName(product.getName());
        orderVO.setPrice(product.getPrice());
        orderVO.setStoreId(product.getStoreId());
        return orderVO;
    }
}
