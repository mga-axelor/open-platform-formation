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
import com.axelor.base.db.Partner;
import com.axelor.db.JPA;
import com.axelor.db.Model;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;
import com.axelor.sale.db.Invoice;
import com.axelor.sale.db.Product;
import com.axelor.sale.db.SaleOrder;
import com.axelor.sale.db.repo.SaleOrderRepository;
import java.math.BigDecimal;
import java.util.Map;
import javax.validation.constraints.NotNull;

public class SaleOrderController {

  public void invoice(ActionRequest request, ActionResponse response) {

    Context context = request.getContext();

    SaleOrder ctxSaleOrder = context.asType(SaleOrder.class);

    Long id = ctxSaleOrder.getId();

    SaleOrder managedSaleOrder = Beans.get(SaleOrderRepository.class).find(id);

    Invoice invoice = Beans.get(SaleOrderInvoicingService.class).processInvoicing(managedSaleOrder);

    response.setReload(true);

    response.setView(
        ActionView.define("Invoice")
            .model("com.axelor.sale.db.Invoice")
            .add("form", "invoice-form")
            .context("_showRecord", invoice.getId())
            .map());
  }

  public void showDummyValues(ActionRequest request, ActionResponse response) {

    Context context = request.getContext();
    Object ctxPartner = context.get("_xPartner");
    if (ctxPartner == null) {
      response.setError("Empty partner");
      return;
    }
    Partner partner = findObject(Partner.class, ctxPartner);
    Object ctxQty = context.get("_xQty");
    if (ctxQty == null) {
      response.setError("Empty qty");
      return;
    }
    BigDecimal qty = new BigDecimal((String) ctxQty);

    Object ctxProduct = context.get("_xProduct");
    if (ctxProduct == null) {
      response.setError("Empty product");
      return;
    }
    Product product = findObject(Product.class, ctxProduct);
    // In details:
    //	Object object = context.get("_xProduct");
    ////	 {
    ////		 id -> 41
    ////		 name -> Produit test
    ////		 unitPrice -> 44.00
    ////	 }
    //
    //	Map<String, Object> produitSousFormeDeMap = (Map<String, Object>) object;
    //
    //	Object idNonFormatté = produitSousFormeDeMap.get("id");
    //
    //	long id = Long.parseLong(idNonFormatté.toString());
    //
    //    Product product = Beans.get(ProductRepository.class).find(id);

    response.setAlert("" + product + "<br/><br/>" + partner + "<br/><br/>" + qty);
  }

  public static <T extends Model> T findObject(@NotNull Class<T> tClass, @NotNull Object object) {
    if (((Map<String, Object>) object).get("id") == null) {
      return null;
    }
    return JPA.find(tClass, Long.parseLong(((Map<String, Object>) object).get("id").toString()));
  }
}
