package com.axelor.apps.base.service.partner;

import com.axelor.base.db.Partner;
import com.axelor.base.db.repo.PartnerRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class PartnerServiceImpl implements PartnerService {

  protected final PartnerRepository partnerRepository;

  @Inject
  public PartnerServiceImpl(PartnerRepository partnerRepository) {
    this.partnerRepository = partnerRepository;
  }

  @Override
  @Transactional
  public Partner duplicate(Partner partner) {
    Partner duplicatedPartner = duplicatePartner(partner);
    return partnerRepository.save(duplicatedPartner);
  }

  protected Partner duplicatePartner(Partner partner) {
    Partner duplicatedPartner = new Partner();
    duplicatedPartner.setFirstName(partner.getFirstName());
    duplicatedPartner.setName(partner.getName());
    duplicatedPartner.setPartnerSeq(partner.getPartnerSeq() + "-duplicated");
    duplicatedPartner.setTypeSelect(partner.getTypeSelect());
    return duplicatedPartner;
  }
}
