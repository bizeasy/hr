import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacility, Facility } from 'app/shared/model/facility.model';
import { FacilityService } from './facility.service';
import { IFacilityType } from 'app/shared/model/facility-type.model';
import { FacilityTypeService } from 'app/entities/facility-type/facility-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IFacilityGroup } from 'app/shared/model/facility-group.model';
import { FacilityGroupService } from 'app/entities/facility-group/facility-group.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';
import { IGeoPoint } from 'app/shared/model/geo-point.model';
import { GeoPointService } from 'app/entities/geo-point/geo-point.service';
import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { InventoryItemTypeService } from 'app/entities/inventory-item-type/inventory-item-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IFacilityType | IFacility | IParty | IFacilityGroup | IUom | IGeoPoint | IInventoryItemType | IStatus | IUser;

@Component({
  selector: 'sys-facility-update',
  templateUrl: './facility-update.component.html',
})
export class FacilityUpdateComponent implements OnInit {
  isSaving = false;
  facilitytypes: IFacilityType[] = [];
  facilities: IFacility[] = [];
  parties: IParty[] = [];
  facilitygroups: IFacilityGroup[] = [];
  uoms: IUom[] = [];
  geopoints: IGeoPoint[] = [];
  inventoryitemtypes: IInventoryItemType[] = [];
  statuses: IStatus[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
    facilityCode: [null, [Validators.maxLength(20)]],
    facilitySize: [],
    costCenterCode: [null, [Validators.maxLength(25)]],
    facilityType: [],
    parentFacility: [],
    ownerParty: [],
    facilityGroup: [],
    sizeUom: [],
    geoPoint: [],
    inventoryItemType: [],
    status: [],
    usageStatus: [],
    reviewedBy: [],
    approvedBy: [],
  });

  constructor(
    protected facilityService: FacilityService,
    protected facilityTypeService: FacilityTypeService,
    protected partyService: PartyService,
    protected facilityGroupService: FacilityGroupService,
    protected uomService: UomService,
    protected geoPointService: GeoPointService,
    protected inventoryItemTypeService: InventoryItemTypeService,
    protected statusService: StatusService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facility }) => {
      this.updateForm(facility);

      this.facilityTypeService.query().subscribe((res: HttpResponse<IFacilityType[]>) => (this.facilitytypes = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.facilityGroupService.query().subscribe((res: HttpResponse<IFacilityGroup[]>) => (this.facilitygroups = res.body || []));

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));

      this.geoPointService.query().subscribe((res: HttpResponse<IGeoPoint[]>) => (this.geopoints = res.body || []));

      this.inventoryItemTypeService
        .query()
        .subscribe((res: HttpResponse<IInventoryItemType[]>) => (this.inventoryitemtypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(facility: IFacility): void {
    this.editForm.patchValue({
      id: facility.id,
      name: facility.name,
      description: facility.description,
      facilityCode: facility.facilityCode,
      facilitySize: facility.facilitySize,
      costCenterCode: facility.costCenterCode,
      facilityType: facility.facilityType,
      parentFacility: facility.parentFacility,
      ownerParty: facility.ownerParty,
      facilityGroup: facility.facilityGroup,
      sizeUom: facility.sizeUom,
      geoPoint: facility.geoPoint,
      inventoryItemType: facility.inventoryItemType,
      status: facility.status,
      usageStatus: facility.usageStatus,
      reviewedBy: facility.reviewedBy,
      approvedBy: facility.approvedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facility = this.createFromForm();
    if (facility.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityService.update(facility));
    } else {
      this.subscribeToSaveResponse(this.facilityService.create(facility));
    }
  }

  private createFromForm(): IFacility {
    return {
      ...new Facility(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      facilityCode: this.editForm.get(['facilityCode'])!.value,
      facilitySize: this.editForm.get(['facilitySize'])!.value,
      costCenterCode: this.editForm.get(['costCenterCode'])!.value,
      facilityType: this.editForm.get(['facilityType'])!.value,
      parentFacility: this.editForm.get(['parentFacility'])!.value,
      ownerParty: this.editForm.get(['ownerParty'])!.value,
      facilityGroup: this.editForm.get(['facilityGroup'])!.value,
      sizeUom: this.editForm.get(['sizeUom'])!.value,
      geoPoint: this.editForm.get(['geoPoint'])!.value,
      inventoryItemType: this.editForm.get(['inventoryItemType'])!.value,
      status: this.editForm.get(['status'])!.value,
      usageStatus: this.editForm.get(['usageStatus'])!.value,
      reviewedBy: this.editForm.get(['reviewedBy'])!.value,
      approvedBy: this.editForm.get(['approvedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacility>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
