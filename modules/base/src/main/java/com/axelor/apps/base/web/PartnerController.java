package com.axelor.apps.base.web;

import com.axelor.apps.base.service.partner.PartnerService;
import com.axelor.base.db.Partner;
import com.axelor.inject.Beans;
import com.axelor.meta.schema.actions.ActionView;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;

public class PartnerController {

  public void duplicate(ActionRequest request, ActionResponse response) {
    Context context = request.getContext();
    Partner partner = context.asType(Partner.class);

    Partner duplicatedPartner = Beans.get(PartnerService.class).duplicate(partner);


    response.setView(
        ActionView.define("Duplicated Partner")
            .model("com.axelor.base.db.Partner")
            .add("form", "partner-form")
            .context("_showRecord", duplicatedPartner.getId())
            .map());
  }
}
