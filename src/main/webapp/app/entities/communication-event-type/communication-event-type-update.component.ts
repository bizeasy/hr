import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommunicationEventType, CommunicationEventType } from 'app/shared/model/communication-event-type.model';
import { CommunicationEventTypeService } from './communication-event-type.service';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from 'app/entities/contact-mech-type/contact-mech-type.service';

@Component({
  selector: 'sys-communication-event-type-update',
  templateUrl: './communication-event-type-update.component.html',
})
export class CommunicationEventTypeUpdateComponent implements OnInit {
  isSaving = false;
  contactmechtypes: IContactMechType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    contactMechType: [],
  });

  constructor(
    protected communicationEventTypeService: CommunicationEventTypeService,
    protected contactMechTypeService: ContactMechTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communicationEventType }) => {
      this.updateForm(communicationEventType);

      this.contactMechTypeService.query().subscribe((res: HttpResponse<IContactMechType[]>) => (this.contactmechtypes = res.body || []));
    });
  }

  updateForm(communicationEventType: ICommunicationEventType): void {
    this.editForm.patchValue({
      id: communicationEventType.id,
      name: communicationEventType.name,
      description: communicationEventType.description,
      contactMechType: communicationEventType.contactMechType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const communicationEventType = this.createFromForm();
    if (communicationEventType.id !== undefined) {
      this.subscribeToSaveResponse(this.communicationEventTypeService.update(communicationEventType));
    } else {
      this.subscribeToSaveResponse(this.communicationEventTypeService.create(communicationEventType));
    }
  }

  private createFromForm(): ICommunicationEventType {
    return {
      ...new CommunicationEventType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      contactMechType: this.editForm.get(['contactMechType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommunicationEventType>>): void {
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

  trackById(index: number, item: IContactMechType): any {
    return item.id;
  }
}
