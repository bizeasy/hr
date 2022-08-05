import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPostalAddress, PostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from './postal-address.service';
import { IGeo } from 'app/shared/model/geo.model';
import { GeoService } from 'app/entities/geo/geo.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { IGeoPoint } from 'app/shared/model/geo-point.model';
import { GeoPointService } from 'app/entities/geo-point/geo-point.service';

type SelectableEntity = IGeo | IContactMech | IGeoPoint;

@Component({
  selector: 'sys-postal-address-update',
  templateUrl: './postal-address-update.component.html',
})
export class PostalAddressUpdateComponent implements OnInit {
  isSaving = false;
  geos: IGeo[] = [];
  contactmeches: IContactMech[] = [];
  geopoints: IGeoPoint[] = [];

  editForm = this.fb.group({
    id: [],
    toName: [null, [Validators.maxLength(100)]],
    address1: [null, [Validators.maxLength(200)]],
    address2: [null, [Validators.maxLength(200)]],
    city: [null, [Validators.maxLength(60)]],
    landmark: [null, [Validators.maxLength(60)]],
    postalCode: [null, [Validators.maxLength(10)]],
    isDefault: [],
    customAddressType: [null, [Validators.maxLength(25)]],
    stateStr: [null, [Validators.maxLength(40)]],
    countryStr: [null, [Validators.maxLength(40)]],
    isIndianAddress: [],
    note: [null, [Validators.maxLength(255)]],
    directions: [null, [Validators.maxLength(255)]],
    state: [],
    pincode: [],
    country: [],
    contactMech: [],
    geoPoint: [],
  });

  constructor(
    protected postalAddressService: PostalAddressService,
    protected geoService: GeoService,
    protected contactMechService: ContactMechService,
    protected geoPointService: GeoPointService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ postalAddress }) => {
      this.updateForm(postalAddress);

      this.geoService.query().subscribe((res: HttpResponse<IGeo[]>) => (this.geos = res.body || []));

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));

      this.geoPointService.query().subscribe((res: HttpResponse<IGeoPoint[]>) => (this.geopoints = res.body || []));
    });
  }

  updateForm(postalAddress: IPostalAddress): void {
    this.editForm.patchValue({
      id: postalAddress.id,
      toName: postalAddress.toName,
      address1: postalAddress.address1,
      address2: postalAddress.address2,
      city: postalAddress.city,
      landmark: postalAddress.landmark,
      postalCode: postalAddress.postalCode,
      isDefault: postalAddress.isDefault,
      customAddressType: postalAddress.customAddressType,
      stateStr: postalAddress.stateStr,
      countryStr: postalAddress.countryStr,
      isIndianAddress: postalAddress.isIndianAddress,
      note: postalAddress.note,
      directions: postalAddress.directions,
      state: postalAddress.state,
      pincode: postalAddress.pincode,
      country: postalAddress.country,
      contactMech: postalAddress.contactMech,
      geoPoint: postalAddress.geoPoint,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const postalAddress = this.createFromForm();
    if (postalAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.postalAddressService.update(postalAddress));
    } else {
      this.subscribeToSaveResponse(this.postalAddressService.create(postalAddress));
    }
  }

  private createFromForm(): IPostalAddress {
    return {
      ...new PostalAddress(),
      id: this.editForm.get(['id'])!.value,
      toName: this.editForm.get(['toName'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      city: this.editForm.get(['city'])!.value,
      landmark: this.editForm.get(['landmark'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      isDefault: this.editForm.get(['isDefault'])!.value,
      customAddressType: this.editForm.get(['customAddressType'])!.value,
      stateStr: this.editForm.get(['stateStr'])!.value,
      countryStr: this.editForm.get(['countryStr'])!.value,
      isIndianAddress: this.editForm.get(['isIndianAddress'])!.value,
      note: this.editForm.get(['note'])!.value,
      directions: this.editForm.get(['directions'])!.value,
      state: this.editForm.get(['state'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      country: this.editForm.get(['country'])!.value,
      contactMech: this.editForm.get(['contactMech'])!.value,
      geoPoint: this.editForm.get(['geoPoint'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPostalAddress>>): void {
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
