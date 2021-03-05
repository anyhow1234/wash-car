package com.xiaozhu.washcar.iservice.order;

import com.xiaozhu.washcar.entity.customer.Customer;
import com.xiaozhu.washcar.entity.order.Order;

import java.util.Map;

public interface OrderInterface {
    /**
     * 扫码后页面接口：获取设备是否在使用
     * @return
     */
    Map<String,Object> preCreateOrder();

    /**
     * 创建订单
     * @return
     */
    Order createOrder(Customer customer, String ipAddress);

    /**
     * 支付订单
     * @return
     */
    Map<String,Object> payOrder(String orderNo);

    /**
     * 支付成功回调
     * @return
     */
    Map<String,Object> payOrderSuccess(String orderNo);

    /**
     * 订单时间到，结束
     * @param orderNo
     */
    void orderEnd(String orderNo);
}
