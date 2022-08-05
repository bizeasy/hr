import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employment',
        loadChildren: () => import('./employment/employment.module').then(m => m.HrEmploymentModule),
      },
      {
        path: 'attachment',
        loadChildren: () => import('./attachment/attachment.module').then(m => m.HrAttachmentModule),
      },
      {
        path: 'contact-mech-purpose',
        loadChildren: () => import('./contact-mech-purpose/contact-mech-purpose.module').then(m => m.HrContactMechPurposeModule),
      },
      {
        path: 'contact-mech-type',
        loadChildren: () => import('./contact-mech-type/contact-mech-type.module').then(m => m.HrContactMechTypeModule),
      },
      {
        path: 'contact-mech',
        loadChildren: () => import('./contact-mech/contact-mech.module').then(m => m.HrContactMechModule),
      },
      {
        path: 'content-type',
        loadChildren: () => import('./content-type/content-type.module').then(m => m.HrContentTypeModule),
      },
      {
        path: 'catalogue',
        loadChildren: () => import('./catalogue/catalogue.module').then(m => m.HrCatalogueModule),
      },
      {
        path: 'catalogue-category-type',
        loadChildren: () => import('./catalogue-category-type/catalogue-category-type.module').then(m => m.HrCatalogueCategoryTypeModule),
      },
      {
        path: 'catalogue-category',
        loadChildren: () => import('./catalogue-category/catalogue-category.module').then(m => m.HrCatalogueCategoryModule),
      },
      {
        path: 'communication-event-type',
        loadChildren: () =>
          import('./communication-event-type/communication-event-type.module').then(m => m.HrCommunicationEventTypeModule),
      },
      {
        path: 'communication-event',
        loadChildren: () => import('./communication-event/communication-event.module').then(m => m.HrCommunicationEventModule),
      },
      {
        path: 'equipment',
        loadChildren: () => import('./equipment/equipment.module').then(m => m.HrEquipmentModule),
      },
      {
        path: 'equipment-usage-log',
        loadChildren: () => import('./equipment-usage-log/equipment-usage-log.module').then(m => m.HrEquipmentUsageLogModule),
      },
      {
        path: 'equipment-type',
        loadChildren: () => import('./equipment-type/equipment-type.module').then(m => m.HrEquipmentTypeModule),
      },
      {
        path: 'facility-type',
        loadChildren: () => import('./facility-type/facility-type.module').then(m => m.HrFacilityTypeModule),
      },
      {
        path: 'facility-group-type',
        loadChildren: () => import('./facility-group-type/facility-group-type.module').then(m => m.HrFacilityGroupTypeModule),
      },
      {
        path: 'facility-group',
        loadChildren: () => import('./facility-group/facility-group.module').then(m => m.HrFacilityGroupModule),
      },
      {
        path: 'facility',
        loadChildren: () => import('./facility/facility.module').then(m => m.HrFacilityModule),
      },
      {
        path: 'facility-usage-log',
        loadChildren: () => import('./facility-usage-log/facility-usage-log.module').then(m => m.HrFacilityUsageLogModule),
      },
      {
        path: 'facility-party',
        loadChildren: () => import('./facility-party/facility-party.module').then(m => m.HrFacilityPartyModule),
      },
      {
        path: 'facility-group-member',
        loadChildren: () => import('./facility-group-member/facility-group-member.module').then(m => m.HrFacilityGroupMemberModule),
      },
      {
        path: 'facility-contact-mech',
        loadChildren: () => import('./facility-contact-mech/facility-contact-mech.module').then(m => m.HrFacilityContactMechModule),
      },
      {
        path: 'facility-equipment',
        loadChildren: () => import('./facility-equipment/facility-equipment.module').then(m => m.HrFacilityEquipmentModule),
      },
      {
        path: 'geo-type',
        loadChildren: () => import('./geo-type/geo-type.module').then(m => m.HrGeoTypeModule),
      },
      {
        path: 'geo-assoc-type',
        loadChildren: () => import('./geo-assoc-type/geo-assoc-type.module').then(m => m.HrGeoAssocTypeModule),
      },
      {
        path: 'geo',
        loadChildren: () => import('./geo/geo.module').then(m => m.HrGeoModule),
      },
      {
        path: 'geo-assoc',
        loadChildren: () => import('./geo-assoc/geo-assoc.module').then(m => m.HrGeoAssocModule),
      },
      {
        path: 'geo-point',
        loadChildren: () => import('./geo-point/geo-point.module').then(m => m.HrGeoPointModule),
      },
      {
        path: 'inventory-item-type',
        loadChildren: () => import('./inventory-item-type/inventory-item-type.module').then(m => m.HrInventoryItemTypeModule),
      },
      {
        path: 'invoice-type',
        loadChildren: () => import('./invoice-type/invoice-type.module').then(m => m.HrInvoiceTypeModule),
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.HrInvoiceModule),
      },
      {
        path: 'invoice-item-type',
        loadChildren: () => import('./invoice-item-type/invoice-item-type.module').then(m => m.HrInvoiceItemTypeModule),
      },
      {
        path: 'invoice-item',
        loadChildren: () => import('./invoice-item/invoice-item.module').then(m => m.HrInvoiceItemModule),
      },
      {
        path: 'inventory-item',
        loadChildren: () => import('./inventory-item/inventory-item.module').then(m => m.HrInventoryItemModule),
      },
      {
        path: 'inventory-item-delegate',
        loadChildren: () => import('./inventory-item-delegate/inventory-item-delegate.module').then(m => m.HrInventoryItemDelegateModule),
      },
      {
        path: 'item-issuance',
        loadChildren: () => import('./item-issuance/item-issuance.module').then(m => m.HrItemIssuanceModule),
      },
      {
        path: 'inventory-item-variance',
        loadChildren: () => import('./inventory-item-variance/inventory-item-variance.module').then(m => m.HrInventoryItemVarianceModule),
      },
      {
        path: 'inventory-transfer',
        loadChildren: () => import('./inventory-transfer/inventory-transfer.module').then(m => m.HrInventoryTransferModule),
      },
      {
        path: 'keyword-type',
        loadChildren: () => import('./keyword-type/keyword-type.module').then(m => m.HrKeywordTypeModule),
      },
      {
        path: 'location-type',
        loadChildren: () => import('./location-type/location-type.module').then(m => m.HrLocationTypeModule),
      },
      {
        path: 'lot',
        loadChildren: () => import('./lot/lot.module').then(m => m.HrLotModule),
      },
      {
        path: 'order-type',
        loadChildren: () => import('./order-type/order-type.module').then(m => m.HrOrderTypeModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.HrOrderModule),
      },
      {
        path: 'order-item-type',
        loadChildren: () => import('./order-item-type/order-item-type.module').then(m => m.HrOrderItemTypeModule),
      },
      {
        path: 'order-item',
        loadChildren: () => import('./order-item/order-item.module').then(m => m.HrOrderItemModule),
      },
      {
        path: 'order-contact-mech',
        loadChildren: () => import('./order-contact-mech/order-contact-mech.module').then(m => m.HrOrderContactMechModule),
      },
      {
        path: 'order-status',
        loadChildren: () => import('./order-status/order-status.module').then(m => m.HrOrderStatusModule),
      },
      {
        path: 'order-item-billing',
        loadChildren: () => import('./order-item-billing/order-item-billing.module').then(m => m.HrOrderItemBillingModule),
      },
      {
        path: 'order-term-type',
        loadChildren: () => import('./order-term-type/order-term-type.module').then(m => m.HrOrderTermTypeModule),
      },
      {
        path: 'order-term',
        loadChildren: () => import('./order-term/order-term.module').then(m => m.HrOrderTermModule),
      },
      {
        path: 'party-role',
        loadChildren: () => import('./party-role/party-role.module').then(m => m.HrPartyRoleModule),
      },
      {
        path: 'party-type',
        loadChildren: () => import('./party-type/party-type.module').then(m => m.HrPartyTypeModule),
      },
      {
        path: 'party',
        loadChildren: () => import('./party/party.module').then(m => m.HrPartyModule),
      },
      {
        path: 'party-classification-type',
        loadChildren: () =>
          import('./party-classification-type/party-classification-type.module').then(m => m.HrPartyClassificationTypeModule),
      },
      {
        path: 'party-classification-group',
        loadChildren: () =>
          import('./party-classification-group/party-classification-group.module').then(m => m.HrPartyClassificationGroupModule),
      },
      {
        path: 'party-classification',
        loadChildren: () => import('./party-classification/party-classification.module').then(m => m.HrPartyClassificationModule),
      },
      {
        path: 'permission',
        loadChildren: () => import('./permission/permission.module').then(m => m.HrPermissionModule),
      },
      {
        path: 'permission-authority',
        loadChildren: () => import('./permission-authority/permission-authority.module').then(m => m.HrPermissionAuthorityModule),
      },
      {
        path: 'party-contact-mech',
        loadChildren: () => import('./party-contact-mech/party-contact-mech.module').then(m => m.HrPartyContactMechModule),
      },
      {
        path: 'postal-address',
        loadChildren: () => import('./postal-address/postal-address.module').then(m => m.HrPostalAddressModule),
      },
      {
        path: 'product-category-type',
        loadChildren: () => import('./product-category-type/product-category-type.module').then(m => m.HrProductCategoryTypeModule),
      },
      {
        path: 'product-category',
        loadChildren: () => import('./product-category/product-category.module').then(m => m.HrProductCategoryModule),
      },
      {
        path: 'product-category-member',
        loadChildren: () => import('./product-category-member/product-category-member.module').then(m => m.HrProductCategoryMemberModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.HrProductModule),
      },
      {
        path: 'product-type',
        loadChildren: () => import('./product-type/product-type.module').then(m => m.HrProductTypeModule),
      },
      {
        path: 'product-price-type',
        loadChildren: () => import('./product-price-type/product-price-type.module').then(m => m.HrProductPriceTypeModule),
      },
      {
        path: 'product-price-purpose',
        loadChildren: () => import('./product-price-purpose/product-price-purpose.module').then(m => m.HrProductPricePurposeModule),
      },
      {
        path: 'product-price',
        loadChildren: () => import('./product-price/product-price.module').then(m => m.HrProductPriceModule),
      },
      {
        path: 'product-keyword',
        loadChildren: () => import('./product-keyword/product-keyword.module').then(m => m.HrProductKeywordModule),
      },
      {
        path: 'product-facility',
        loadChildren: () => import('./product-facility/product-facility.module').then(m => m.HrProductFacilityModule),
      },
      {
        path: 'payment-type',
        loadChildren: () => import('./payment-type/payment-type.module').then(m => m.HrPaymentTypeModule),
      },
      {
        path: 'payment-method-type',
        loadChildren: () => import('./payment-method-type/payment-method-type.module').then(m => m.HrPaymentMethodTypeModule),
      },
      {
        path: 'payment-method',
        loadChildren: () => import('./payment-method/payment-method.module').then(m => m.HrPaymentMethodModule),
      },
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.HrPaymentModule),
      },
      {
        path: 'payment-application',
        loadChildren: () => import('./payment-application/payment-application.module').then(m => m.HrPaymentApplicationModule),
      },
      {
        path: 'payment-gateway-config',
        loadChildren: () => import('./payment-gateway-config/payment-gateway-config.module').then(m => m.HrPaymentGatewayConfigModule),
      },
      {
        path: 'payment-gateway-response',
        loadChildren: () =>
          import('./payment-gateway-response/payment-gateway-response.module').then(m => m.HrPaymentGatewayResponseModule),
      },
      {
        path: 'product-store-type',
        loadChildren: () => import('./product-store-type/product-store-type.module').then(m => m.HrProductStoreTypeModule),
      },
      {
        path: 'product-store',
        loadChildren: () => import('./product-store/product-store.module').then(m => m.HrProductStoreModule),
      },
      {
        path: 'product-store-facility',
        loadChildren: () => import('./product-store-facility/product-store-facility.module').then(m => m.HrProductStoreFacilityModule),
      },
      {
        path: 'product-store-product',
        loadChildren: () => import('./product-store-product/product-store-product.module').then(m => m.HrProductStoreProductModule),
      },
      {
        path: 'product-store-user-group',
        loadChildren: () => import('./product-store-user-group/product-store-user-group.module').then(m => m.HrProductStoreUserGroupModule),
      },
      {
        path: 'role-type',
        loadChildren: () => import('./role-type/role-type.module').then(m => m.HrRoleTypeModule),
      },
      {
        path: 'reason-type',
        loadChildren: () => import('./reason-type/reason-type.module').then(m => m.HrReasonTypeModule),
      },
      {
        path: 'reason',
        loadChildren: () => import('./reason/reason.module').then(m => m.HrReasonModule),
      },
      {
        path: 'term-type',
        loadChildren: () => import('./term-type/term-type.module').then(m => m.HrTermTypeModule),
      },
      {
        path: 'term',
        loadChildren: () => import('./term/term.module').then(m => m.HrTermModule),
      },
      {
        path: 'work-effort-type',
        loadChildren: () => import('./work-effort-type/work-effort-type.module').then(m => m.HrWorkEffortTypeModule),
      },
      {
        path: 'work-effort-purpose',
        loadChildren: () => import('./work-effort-purpose/work-effort-purpose.module').then(m => m.HrWorkEffortPurposeModule),
      },
      {
        path: 'work-effort',
        loadChildren: () => import('./work-effort/work-effort.module').then(m => m.HrWorkEffortModule),
      },
      {
        path: 'work-effort-product',
        loadChildren: () => import('./work-effort-product/work-effort-product.module').then(m => m.HrWorkEffortProductModule),
      },
      {
        path: 'work-effort-assoc-type',
        loadChildren: () => import('./work-effort-assoc-type/work-effort-assoc-type.module').then(m => m.HrWorkEffortAssocTypeModule),
      },
      {
        path: 'work-effort-assoc',
        loadChildren: () => import('./work-effort-assoc/work-effort-assoc.module').then(m => m.HrWorkEffortAssocModule),
      },
      {
        path: 'work-effort-inventory-assign',
        loadChildren: () =>
          import('./work-effort-inventory-assign/work-effort-inventory-assign.module').then(m => m.HrWorkEffortInventoryAssignModule),
      },
      {
        path: 'work-effort-inventory-produced',
        loadChildren: () =>
          import('./work-effort-inventory-produced/work-effort-inventory-produced.module').then(m => m.HrWorkEffortInventoryProducedModule),
      },
      {
        path: 'work-effort-status',
        loadChildren: () => import('./work-effort-status/work-effort-status.module').then(m => m.HrWorkEffortStatusModule),
      },
      {
        path: 'supplier-product',
        loadChildren: () => import('./supplier-product/supplier-product.module').then(m => m.HrSupplierProductModule),
      },
      {
        path: 'status-category',
        loadChildren: () => import('./status-category/status-category.module').then(m => m.HrStatusCategoryModule),
      },
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.HrStatusModule),
      },
      {
        path: 'status-valid-change',
        loadChildren: () => import('./status-valid-change/status-valid-change.module').then(m => m.HrStatusValidChangeModule),
      },
      {
        path: 'sales-channel',
        loadChildren: () => import('./sales-channel/sales-channel.module').then(m => m.HrSalesChannelModule),
      },
      {
        path: 'tax-authority',
        loadChildren: () => import('./tax-authority/tax-authority.module').then(m => m.HrTaxAuthorityModule),
      },
      {
        path: 'tax-authority-rate-type',
        loadChildren: () => import('./tax-authority-rate-type/tax-authority-rate-type.module').then(m => m.HrTaxAuthorityRateTypeModule),
      },
      {
        path: 'tax-slab',
        loadChildren: () => import('./tax-slab/tax-slab.module').then(m => m.HrTaxSlabModule),
      },
      {
        path: 'uom',
        loadChildren: () => import('./uom/uom.module').then(m => m.HrUomModule),
      },
      {
        path: 'uom-type',
        loadChildren: () => import('./uom-type/uom-type.module').then(m => m.HrUomTypeModule),
      },
      {
        path: 'user-group',
        loadChildren: () => import('./user-group/user-group.module').then(m => m.HrUserGroupModule),
      },
      {
        path: 'user-group-authority',
        loadChildren: () => import('./user-group-authority/user-group-authority.module').then(m => m.HrUserGroupAuthorityModule),
      },
      {
        path: 'user-group-member',
        loadChildren: () => import('./user-group-member/user-group-member.module').then(m => m.HrUserGroupMemberModule),
      },
      {
        path: 'termination-type',
        loadChildren: () => import('./termination-type/termination-type.module').then(m => m.HrTerminationTypeModule),
      },
      {
        path: 'empl-position-group',
        loadChildren: () => import('./empl-position-group/empl-position-group.module').then(m => m.HrEmplPositionGroupModule),
      },
      {
        path: 'empl-position-type',
        loadChildren: () => import('./empl-position-type/empl-position-type.module').then(m => m.HrEmplPositionTypeModule),
      },
      {
        path: 'empl-position',
        loadChildren: () => import('./empl-position/empl-position.module').then(m => m.HrEmplPositionModule),
      },
      {
        path: 'empl-position-fulfillment',
        loadChildren: () =>
          import('./empl-position-fulfillment/empl-position-fulfillment.module').then(m => m.HrEmplPositionFulfillmentModule),
      },
      {
        path: 'empl-position-reporting-struct',
        loadChildren: () =>
          import('./empl-position-reporting-struct/empl-position-reporting-struct.module').then(m => m.HrEmplPositionReportingStructModule),
      },
      {
        path: 'pay-grade',
        loadChildren: () => import('./pay-grade/pay-grade.module').then(m => m.HrPayGradeModule),
      },
      {
        path: 'rate-type',
        loadChildren: () => import('./rate-type/rate-type.module').then(m => m.HrRateTypeModule),
      },
      {
        path: 'empl-position-type-rate',
        loadChildren: () => import('./empl-position-type-rate/empl-position-type-rate.module').then(m => m.HrEmplPositionTypeRateModule),
      },
      {
        path: 'employment-app-source-type',
        loadChildren: () =>
          import('./employment-app-source-type/employment-app-source-type.module').then(m => m.HrEmploymentAppSourceTypeModule),
      },
      {
        path: 'skill-type',
        loadChildren: () => import('./skill-type/skill-type.module').then(m => m.HrSkillTypeModule),
      },
      {
        path: 'qualification',
        loadChildren: () => import('./qualification/qualification.module').then(m => m.HrQualificationModule),
      },
      {
        path: 'exam-type',
        loadChildren: () => import('./exam-type/exam-type.module').then(m => m.HrExamTypeModule),
      },
      {
        path: 'job-requisition',
        loadChildren: () => import('./job-requisition/job-requisition.module').then(m => m.HrJobRequisitionModule),
      },
      {
        path: 'employment-app',
        loadChildren: () => import('./employment-app/employment-app.module').then(m => m.HrEmploymentAppModule),
      },
      {
        path: 'empl-leave-type',
        loadChildren: () => import('./empl-leave-type/empl-leave-type.module').then(m => m.HrEmplLeaveTypeModule),
      },
      {
        path: 'empl-leave-reason-type',
        loadChildren: () => import('./empl-leave-reason-type/empl-leave-reason-type.module').then(m => m.HrEmplLeaveReasonTypeModule),
      },
      {
        path: 'job-interview-type',
        loadChildren: () => import('./job-interview-type/job-interview-type.module').then(m => m.HrJobInterviewTypeModule),
      },
      {
        path: 'interview-grade',
        loadChildren: () => import('./interview-grade/interview-grade.module').then(m => m.HrInterviewGradeModule),
      },
      {
        path: 'interview-result',
        loadChildren: () => import('./interview-result/interview-result.module').then(m => m.HrInterviewResultModule),
      },
      {
        path: 'job-interview',
        loadChildren: () => import('./job-interview/job-interview.module').then(m => m.HrJobInterviewModule),
      },
      {
        path: 'empl-leave',
        loadChildren: () => import('./empl-leave/empl-leave.module').then(m => m.HrEmplLeaveModule),
      },
      {
        path: 'party-resume',
        loadChildren: () => import('./party-resume/party-resume.module').then(m => m.HrPartyResumeModule),
      },
      {
        path: 'perf-rating-type',
        loadChildren: () => import('./perf-rating-type/perf-rating-type.module').then(m => m.HrPerfRatingTypeModule),
      },
      {
        path: 'perf-review',
        loadChildren: () => import('./perf-review/perf-review.module').then(m => m.HrPerfReviewModule),
      },
      {
        path: 'perf-review-item-type',
        loadChildren: () => import('./perf-review-item-type/perf-review-item-type.module').then(m => m.HrPerfReviewItemTypeModule),
      },
      {
        path: 'perf-review-item',
        loadChildren: () => import('./perf-review-item/perf-review-item.module').then(m => m.HrPerfReviewItemModule),
      },
      {
        path: 'period-type',
        loadChildren: () => import('./period-type/period-type.module').then(m => m.HrPeriodTypeModule),
      },
      {
        path: 'custom-time-period',
        loadChildren: () => import('./custom-time-period/custom-time-period.module').then(m => m.HrCustomTimePeriodModule),
      },
      {
        path: 'deduction-type',
        loadChildren: () => import('./deduction-type/deduction-type.module').then(m => m.HrDeductionTypeModule),
      },
      {
        path: 'deduction',
        loadChildren: () => import('./deduction/deduction.module').then(m => m.HrDeductionModule),
      },
      {
        path: 'payroll-preference',
        loadChildren: () => import('./payroll-preference/payroll-preference.module').then(m => m.HrPayrollPreferenceModule),
      },
      {
        path: 'attendance',
        loadChildren: () => import('./attendance/attendance.module').then(m => m.HrAttendanceModule),
      },
      {
        path: 'time-sheet',
        loadChildren: () => import('./time-sheet/time-sheet.module').then(m => m.HrTimeSheetModule),
      },
      {
        path: 'shift',
        loadChildren: () => import('./shift/shift.module').then(m => m.HrShiftModule),
      },
      {
        path: 'shift-holidays',
        loadChildren: () => import('./shift-holidays/shift-holidays.module').then(m => m.HrShiftHolidaysModule),
      },
      {
        path: 'holiday-type',
        loadChildren: () => import('./holiday-type/holiday-type.module').then(m => m.HrHolidayTypeModule),
      },
      {
        path: 'public-holidays',
        loadChildren: () => import('./public-holidays/public-holidays.module').then(m => m.HrPublicHolidaysModule),
      },
      {
        path: 'shift-weekends',
        loadChildren: () => import('./shift-weekends/shift-weekends.module').then(m => m.HrShiftWeekendsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HrEntityModule {}
