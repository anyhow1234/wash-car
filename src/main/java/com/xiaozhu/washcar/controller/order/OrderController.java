package com.xiaozhu.washcar.controller.order;

import com.xiaozhu.washcar.common.HttpClient;
import com.xiaozhu.washcar.common.Result;
import com.xiaozhu.washcar.entity.order.Order;
import com.xiaozhu.washcar.iservice.order.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/5 11:45
 * @Version 1.0
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderInterface orderInterface;

    /**
     * 检查设备是否在使用
     * @return
     */
    @RequestMapping(value = "preCreateOrder", method = {RequestMethod.POST,RequestMethod.GET})
    public Result preCreateOrder(){
        Map<String, Object> r = orderInterface.preCreateOrder();
        return Result.success(r);
    }

    /**
     * 跳转二维码，准备支付：生成订单
     * TODO : 接入个人收款码支付：https://github.com/yioMe/nodejs_wx_aipay_api
     * @return
     */
    @RequestMapping(value = "createOrderAndPay", method = {RequestMethod.POST,RequestMethod.GET})
    public Result createOrderAndPay(HttpServletRequest request){
        String ipAddress = HttpClient.getIPAddress(request);
        Order order = orderInterface.createOrder(null,ipAddress);
        orderInterface.payOrder(order.getOrderNo());
        // TODO : 等对接完支付去掉下面的支付成功回调
        orderInterface.payOrderSuccess(order.getOrderNo());
        return Result.success();
    }

    /**
     * 支付成功回调：打开wifi，记录
     * @return
     */
    @RequestMapping(value = "paySuccessCallback", method = {RequestMethod.POST,RequestMethod.GET})
    public Result paySuccessCallback(String orderNo){
        orderInterface.payOrderSuccess(orderNo);
        return Result.success();
    }

}
