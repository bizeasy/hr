import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContactMech, ContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from './contact-mech.service';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from 'app/entities/contact-mech-type/contact-mech-type.service';

@Component({
  selector: 'sys-contact-mech-update',
  templateUrl: './contact-mech-update.component.html',
})
export class ContactMechUpdateComponent implements OnInit {
  isSaving = false;
  contactmechtypes: IContactMechType[] = [];

  editForm = this.fb.group({
    id: [],
    infoString: [null, [Validators.maxLength(255)]],
    contactMechType: [],
  });

  constructor(
    protected contactMechService: ContactMechService,
    protected contactMechTypeService: ContactMechTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactMech }) => {
      this.updateForm(contactMech);

      this.contactMechTypeService.query().subscribe((res: HttpResponse<IContactMechType[]>) => (this.contactmechtypes = res.body || []));
    });
  }

  updateForm(contactMech: IContactMech): void {
    this.editForm.patchValue({
      id: contactMech.id,
      infoString: contactMech.infoString,
      contactMechType: contactMech.contactMechType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contactMech = this.createFromForm();
    if (contactMech.id !== undefined) {
      this.subscribeToSaveResponse(this.contactMechService.update(contactMech));
    } else {
      this.subscribeToSaveResponse(this.contactMechService.create(contactMech));
    }
  }

  private createFromForm(): IContactMech {
    return {
      ...new ContactMech(),
      id: this.editForm.get(['id'])!.value,
      infoString: this.editForm.get(['infoString'])!.value,
      contactMechType: this.editForm.get(['contactMechType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContactMech>>): void {
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
