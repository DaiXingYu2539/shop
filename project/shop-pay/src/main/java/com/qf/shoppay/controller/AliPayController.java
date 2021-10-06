package com.qf.shoppay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Order;
import com.qf.feign.api.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/aliPayController")
public class AliPayController {

    private String appId = "2021000117650770";

    private String serverUrl = "https://openapi.alipaydev.com/gateway.do";

    private String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnAV7fLgqnJmWuu2jIRBljmIdDPizkL8w18wJHBQ3dXnNbfDjKHGkOdTQRXPUaZpO3hU6eepZ3OiCQ5/cw03quIaWQ/3k1XhjVtiPvL7hwXIeaBY69r/3AgEx4MbzaWtnlhkU19Bw/Z9nX8I4JvP4Qo9NGl0TXXw4J2ZiXwMKcOkfWCsqgD4yQKMkYsWcpO0618uPJldHw3oGj4dVDSieSxljHcazafK0AP8/Zmba/WE8ZTxDWu97E8TPWurGOcq2hxWlLUod8Btbdf/Par7eS5eHoUOiJvdStNId/OV8z/+xI6Gi+umh8owY0rIUUUntmdQuiCv500iNQs0b+yr7fAgMBAAECggEAOGNOFS0Gi8l7K7Q7fOMBb49gnGOx5Jn+6XhTnIcoV0Y8zPb0dcmB70/DdNleKLvZxG1OTlQ12avWsxafb+z3zzOtI3gfBNXwrLgDCg+ixs13O5oEyCdUFls7dpnUmiVyzPy5Aa6amw4Fj9O5IbCeWB/4hi0tIUsHAmxmCVccXoMBrLs/NzpojCB4/zfZCd6/jtgcRImUIJnSfEC6+0bOcpkMjN8wCq9ArGt9WNHTMiKC2zG9P2J8uIDrG24AajI1fCh49Ibc7jYaG1OsEa4YoeZIpIb+UjufHVsDAVNVgXmmQkdOogmH4A92D6+jyOoKKRl4lxNT1w3eTL3y7RxggQKBgQDy5P5iXNyOB8nKiRyoUH8km/ObN+2o7TknOty5DPOm7ro9k2kt3tkc41pT3WMK4E3AHJPBsKwZ2x3fuCbYiU9FY9z71JlbgsxQ3J6vUa9wcw2ewqWL/5oQRg16esq0Fggv9Hp5KFQK6xX05qyIBSCOm9oHCSVIUEn7O01kWPsh2wKBgQCwBCZebF3Bjr7RxOGnUYS1g2sSL9qX9ZoGrC0CWAGcqv6afKTCnfvpYiuGq+M8NQyJdewURbe0T9h4QdPByYES7Wkzj4eL+/hYRBlE1+xaMLOst4lE7K+BeeqEc8rFiArlp8E490zkNsPJrdF8rsfTBomZOulJjRipWV/3QGGwTQKBgQCVtYL9iZL/j9lWlpAtRgA3xk+uGhjIB/80KNmmzU7YU0hDb7piZXqYjaE9E7VaHMqPYI3J0mUhQqb1H4IB8RAFCvpH1c/4UXQxWuoL0WM7Ag8hDYQP+CkzYpooJcsOmI3PUPaz+qFiYWeVHSC2bcKVY4ZM09xA6AvRZ8nZ0eZoBQKBgBRfxpkzbf7TqR7a7UvS0cu49JHcXC8uzK9tCqC00yrxWBtFxkSIyPoQ1Pz3tIvpkBPxRGxsTYFfpKz24a4SxxALzxuax5PajLqa11nyrHccIBAkBo2l4ALau9ytEpGe17QRoPoZFhtsg2k6537JNHQm05KbERnkbTBdGhCisSwZAoGBAKX2lSKClPNv6chSNTVy8Ikg7GaZnsF0ahVtefpQQ4sgkbhhLaehFo4Y5uMbzMDDMfSewbsbeTgTdlt/L+0UPW7hWpZNboiEYNJA66DLLxDMn+Tc7Tjda9Yaf1M3Ec9tqvyDtqtI3tSwu5LuS3W0xtMYe4+tRhOs0a8BsqqjgylZ";
         //商家的公钥
    private String FORMAT = "JSON";

    private String CHARSET = "utf8";

    private String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh0gqQeq0Ma/nhHL57brLgZ2uYONXX5WqiCqyu87+yi2R1CH+JZii6KEUUlHOfHYkDZSWy/4QsE3dno2Emf5URA/2q4plp5t9Z8o82cOaQDP88mwiPlKeUJ32WnYjS6Nl61aVB04Qcaykx2V8/F4gxqpU4hIyMn1nuA9mgnCS7TuIp9jekV06mKTrzEvpykJ6zuoKE7dgWKhTEA7uJ8DHbusFm+mvLKUBxmi3XZP7KNnDHhMR4T/EneDMuSeBNTUoWSCohoTPHYpg5XH3KZMBpgDlqzwPGSBnjMSRM08/cdni2HmhG4CHJCH78WsJwPN7CVMioMaB77DICAFW5YIz4wIDAQAB";
            //支付宝的公钥
    private String SIGN_TYPE = "RSA2";

    @Autowired
    private IOrderService orderService;

    // 在这个方法里面对接支付宝
    @RequestMapping("/pay")
    public void pay(@RequestParam("orderId") String orderId, HttpServletResponse httpResponse) throws Exception {
        System.out.println("AliPayController.pay");

        // 先根据订单id查询订单对象
        Order order = orderService.getOrderById(orderId);

        // 1.封装请求支付宝的参数
        AlipayClient alipayClient = new DefaultAlipayClient(
                serverUrl, // 支付宝的网关地址,注意我们这里dev测试
                appId, // 商家的appid
                APP_PRIVATE_KEY, // 商家私钥
                FORMAT, // 数据传递的格式
                CHARSET, // 请求数据的编码
                ALIPAY_PUBLIC_KEY, // 支付宝的公钥
                SIGN_TYPE // 签名的类型
        );  //获得初始化的AlipayClient

        // 2.创建一个请求对象
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest(); //创建API对应的request

        // 3.设置同步异步跳转的地址
        alipayRequest.setReturnUrl("http://localhost/shop-home"); // 支付成功后跳转baidu，同步跳转

        alipayRequest.setNotifyUrl("http://uxhrdp.natappfree.cc/shop-pay/aliPayController/updateOrderStatus"); //异步通知商家的订单交易的情况
//wr8ytc.natappfree.cc/
        // 4、订单的信息
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":" + orderId + "," + // 商家生成的订单号
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getTotalPrice() + "," + // 订单的金额
                "    \"subject\":\"2008-shop\"," + // 订单的标题
                "    \"body\":\"具体商品的信息\"," + // 订单的描述
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }"); //填充业务参数


        String form = "";
        try {
            // 获取一个登陆页面
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 把登录页面响应给用户
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form); //直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }

    // 支付宝调用这个接口会传递很多参数
    @RequestMapping("/updateOrderStatus")
    public void updateOrderStatus(HttpServletRequest request) throws Exception {

        // 先做延签

        // 1.先获取异步接收的参数
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 2.准备一个新的map
        Map<String, String> paramsMap = new HashMap<>(); // ...  //将异步通知中收到的所有参数都存放到 map 中

        // 3.把接收到的所有参数重新放入到paramsMap中
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            String[] value = entry.getValue();

            // 设置到新的map中
            paramsMap.put(key, value[0]);
        }

        // 4.完成延签
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);  //调用SDK验证签名
        if (signVerified) {
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            System.out.println("这个请求是支付宝发送的");

            // 修改的订单的状态
            String orderId = paramsMap.get("out_trade_no");
            String orderStatus = paramsMap.get("trade_status");
            if ("TRADE_SUCCESS".equals(orderStatus)) { // 订单支付成功
                // 修改订单的状态为已支付
                Map<String, String> map = new HashMap<>();
                map.put("orderId", orderId);
                map.put("status", "2");
                int update = orderService.updateOrderStatus(map);

            }

        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            System.out.println("这个请求是非法的");
        }

    }
}
