package com.CQUPT.wanlitakeout.dto;

import com.CQUPT.wanlitakeout.entity.OrderDetail;
import com.CQUPT.wanlitakeout.entity.Orders;
import lombok.Data;

import java.util.List;
@Data
public class OrdersDto extends Orders {
    private List<OrderDetail> orderDetails;

}
