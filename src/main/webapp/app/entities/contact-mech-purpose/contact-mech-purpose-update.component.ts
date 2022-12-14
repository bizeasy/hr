import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContactMechPurpose, ContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';
import { ContactMechPurposeService } from './contact-mech-purpose.service';

@Component({
  selector: 'sys-contact-mech-purpose-update',
  templateUrl: './contact-mech-purpose-update.component.html',
})
export class ContactMechPurposeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected contactMechPurposeService: ContactMechPurposeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMechPurpose }) => {
      this.updateForm(contactMechPurpose);
    });
  }

  updateForm(contactMechPurpose: IContactMechPurpose): void {
    this.editForm.patchValue({
      id: contactMechPurpose.id,
      name: contactMechPurpose.name,
      description: contactMechPurpose.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactMechPurpose = this.createFromForm();
    if (contactMechPurpose.id !== undefined) {
      this.subscribeToSaveResponse(this.contactMechPurposeService.update(contactMechPurpose));
    } else {
      this.subscribeToSaveResponse(this.contactMechPurposeService.create(contactMechPurpose));
    }
  }

  private createFromForm(): IContactMechPurpose {
    return {
      ...new ContactMechPurpose(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactMechPurpose>>): void {
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
}
