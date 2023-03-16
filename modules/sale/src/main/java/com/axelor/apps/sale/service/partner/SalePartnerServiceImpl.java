/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2023 Axelor (<http://axelor.com>).
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
package com.axelor.apps.sale.service.partner;

import com.axelor.apps.base.service.partner.PartnerServiceImpl;
import com.axelor.base.db.Partner;
import com.axelor.base.db.repo.PartnerRepository;
import com.google.inject.Inject;

public class SalePartnerServiceImpl extends PartnerServiceImpl {

  @Inject
  public SalePartnerServiceImpl(PartnerRepository partnerRepository) {
    super(partnerRepository);
  }

  @Override
  protected Partner duplicatePartner(Partner partner) {
    Partner duplicatedPartner = super.duplicatePartner(partner);
    duplicatedPartner.setPersonnalBalance(partner.getPersonnalBalance());
    return duplicatedPartner;
  }
}
