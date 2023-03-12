/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2020 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.sale.web;

import com.axelor.apps.sale.service.saleorder.SaleOrderInvoicingService;
import com.axelor.i18n.I18n;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;
import com.axelor.sale.db.Invoice;
import com.axelor.sale.db.SaleOrder;
import com.axelor.sale.db.repo.SaleOrderRepository;

public class SaleOrderController {

  public void invoice(ActionRequest request, ActionResponse response) {

    Context context = request.getContext();

    SaleOrder ctxSaleOrder = context.asType(SaleOrder.class);

    SaleOrder managedSaleOrder = Beans.get(SaleOrderRepository.class).find(ctxSaleOrder.getId());

    Invoice invoice = Beans.get(SaleOrderInvoicingService.class).processInvoicing(managedSaleOrder);

    response.setFlash(String.format(I18n.get("Invoice '%s' created."), invoice.getInvoiceSeq()));
    response.setReload(true);
  }
}
