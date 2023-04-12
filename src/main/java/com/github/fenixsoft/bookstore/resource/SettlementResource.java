/*
 * Copyright 2012-2020. the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. More information from:
 *
 *        https://github.com/fenixsoft
 */

package com.github.fenixsoft.bookstore.resource;

import com.github.fenixsoft.bookstore.applicaiton.payment.PaymentApplicationService;
import com.github.fenixsoft.bookstore.applicaiton.payment.dto.Settlement;
import com.github.fenixsoft.bookstore.domain.auth.Role;
import com.github.fenixsoft.bookstore.domain.payment.Payment;
import com.github.fenixsoft.bookstore.domain.payment.validation.SufficientStock;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 结算清单相关的资源
 *
 * @author icyfenix@gmail.com
 * @date 2020/3/12 11:23
 **/
@Path("/settlements")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SettlementResource {

    @Inject
    private PaymentApplicationService service;

    /**
     * 提交一张交易结算单，根据结算单中的物品，生成支付单
     *
     * {"items":[{"amount":1,"id":2},{"amount":1,"id":5}],"purchase":{"name":"周志明","telephone":"18888888888","delivery":true,
     * "address":{"province":"广东省","city":"广州市","area":"海珠区"},"location":"广东省  广州市 海珠区 唐家湾港湾大道科技一路3号远光软件股份有限公司","pay":"wechat","id":1,"username":"icyfenix",
     * "avatar":"https://www.gravatar.com/avatar/1563e833e42fb64b41eed34c9b66d723?d=mp","email":"icyfenix@gmail.com"}}
     */
    @POST
    @RolesAllowed(Role.USER)
    public Payment executeSettlement(@Valid @SufficientStock Settlement settlement) {
        return service.executeBySettlement(settlement);
    }

}
