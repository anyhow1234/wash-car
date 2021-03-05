package com.xiaozhu.washcar.service.order;

import com.sun.tools.corba.se.idl.constExpr.Or;
import com.xiaozhu.washcar.common.DateUtils;
import com.xiaozhu.washcar.common.EquipmentStatus;
import com.xiaozhu.washcar.common.OrderNoGenerator;
import com.xiaozhu.washcar.common.Switch;
import com.xiaozhu.washcar.dao.order.OrderMapper;
import com.xiaozhu.washcar.entity.customer.Customer;
import com.xiaozhu.washcar.entity.order.Order;
import com.xiaozhu.washcar.iservice.order.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/6 14:32
 * @Version 1.0
 */
@Service
public class OrderService implements OrderInterface {
    @Autowired
    OrderMapper orderMapper;
    /**
     * 扫码后页面接口：获取设备是否在使用
     *
     * @return
     */
    @Override
    public Map<String, Object> preCreateOrder() {
        Map<String,Object> map = new HashMap<>();
        map.put("inuse",EquipmentStatus.getStatus(null));
        return map;
    }

    /**
     * 创建订单
     *
     * @param customer
     * @param ipAddress
     * @return
     */
    @Override
    public Order createOrder(Customer customer, String ipAddress) {
        Order order = new Order();
        order.setOrderNo(OrderNoGenerator.getInstance().create());
        order.setCustomerId(ipAddress);
        order.setCreateTime(new Date());
        order.setOrderAmount(BigDecimal.ZERO);
        orderMapper.insert(order);
        return order;
    }

    /**
     * 支付订单
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> payOrder(String orderNo) {
        // TODO : 返回个人收款二维码

        return null;
    }

    /**
     * 支付成功回调
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> payOrderSuccess(String orderNo) {
        // 先关设备??
        // 更新订单
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        order.setPayTime(new Date());
        order.setStartTime(order.getPayTime());
        order.setEndTime(DateUtils.getAddDateBySecond(order.getStartTime(),60 * 30)); //30分钟使用权
        orderMapper.updateByPrimaryKey(order);
        // TODO : 此处应该塞个消息队列，时间到了就关闭
        // MQ.send(order.getEndTime)
        // 设备置为使用中
        EquipmentStatus.use(null);
        // 打开设备
        Switch.turnOn();
        return null;
    }

    /**
     * 订单时间到，结束
     *
     * @param orderNo
     */
    @Override
    public void orderEnd(String orderNo) {
        // 设备置为使用中
        EquipmentStatus.release(null);
        // 打开设备
        Switch.turnOff();
    }
}
