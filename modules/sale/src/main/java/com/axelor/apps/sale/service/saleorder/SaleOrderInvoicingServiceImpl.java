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
package com.axelor.apps.sale.service.saleorder;

import java.util.Set;

import com.axelor.sale.db.Invoice;
import com.axelor.sale.db.InvoiceLine;
import com.axelor.sale.db.SaleOrder;
import com.axelor.sale.db.SaleOrderLine;
import com.axelor.sale.db.repo.InvoiceRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class SaleOrderInvoicingServiceImpl implements SaleOrderInvoicingService {

	protected final InvoiceRepository InvoiceRepo;

	@Inject
	public SaleOrderInvoicingServiceImpl(InvoiceRepository InvoiceRepo) {
		this.InvoiceRepo = InvoiceRepo;
	}

	@Override
	@Transactional
	public Invoice processInvoicing(SaleOrder saleOrder) {

		Invoice invoice = new Invoice();
		invoice.setClientPartner(saleOrder.getClientPartner());
		invoice.setTotalPrice(saleOrder.getTotalPrice());

		fillInvoiceSeq(saleOrder, invoice);

		invoice.addSaleOrderSetItem(saleOrder);

		saleOrder.getSaleOrderLineList().stream().map(this::createInvoiceLine).forEach(invoice::addInvoiceLineListItem);

		return InvoiceRepo.save(invoice);
	}

	protected void fillInvoiceSeq(SaleOrder saleOrder, Invoice invoice) {
		Set<Invoice> invoiceSet = saleOrder.getInvoiceSet();
		int invoiceNbr = invoiceSet != null ? invoiceSet.size() + 1 : 1;
		String invoiceNbrStr = String.format("%03d", invoiceNbr);
		invoice.setInvoiceSeq(saleOrder.getSaleOrderSeq() + "-" + invoiceNbrStr);
	}

	protected InvoiceLine createInvoiceLine(SaleOrderLine saleOrderLine) {
		InvoiceLine invoiceLine = new InvoiceLine();
		invoiceLine.setSequence(saleOrderLine.getSequence());
		invoiceLine.setTitle(saleOrderLine.getTitle());
		invoiceLine.setProduct(saleOrderLine.getProduct());
		invoiceLine.setQty(saleOrderLine.getQty());
		invoiceLine.setUnitPrice(saleOrderLine.getUnitPrice());
		invoiceLine.setTotalPrice(saleOrderLine.getTotalPrice());
		invoiceLine.setSaleOrderLine(saleOrderLine);
		return invoiceLine;
	}
}
