import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICommunicationEvent, CommunicationEvent } from 'app/shared/model/communication-event.model';
import { CommunicationEventService } from './communication-event.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICommunicationEventType } from 'app/shared/model/communication-event-type.model';
import { CommunicationEventTypeService } from 'app/entities/communication-event-type/communication-event-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from 'app/entities/contact-mech-type/contact-mech-type.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

type SelectableEntity = ICommunicationEventType | IStatus | IContactMechType | IContactMech | IParty;

@Component({
  selector: 'sys-communication-event-update',
  templateUrl: './communication-event-update.component.html',
})
export class CommunicationEventUpdateComponent implements OnInit {
  isSaving = false;
  communicationeventtypes: ICommunicationEventType[] = [];
  statuses: IStatus[] = [];
  contactmechtypes: IContactMechType[] = [];
  contactmeches: IContactMech[] = [];
  parties: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    entryDate: [],
    subject: [],
    content: [],
    fromString: [],
    toString: [],
    ccString: [],
    message: [],
    dateStarted: [],
    dateEnded: [],
    info: [],
    communicationEventType: [],
    status: [],
    contactMechType: [],
    contactMechFrom: [],
    contactMechTo: [],
    fromParty: [],
    toParty: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected communicationEventService: CommunicationEventService,
    protected communicationEventTypeService: CommunicationEventTypeService,
    protected statusService: StatusService,
    protected contactMechTypeService: ContactMechTypeService,
    protected contactMechService: ContactMechService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communicationEvent }) => {
      if (!communicationEvent.id) {
        const today = moment().startOf('day');
        communicationEvent.entryDate = today;
        communicationEvent.dateStarted = today;
        communicationEvent.dateEnded = today;
      }

      this.updateForm(communicationEvent);

      this.communicationEventTypeService
        .query()
        .subscribe((res: HttpResponse<ICommunicationEventType[]>) => (this.communicationeventtypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.contactMechTypeService.query().subscribe((res: HttpResponse<IContactMechType[]>) => (this.contactmechtypes = res.body || []));

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(communicationEvent: ICommunicationEvent): void {
    this.editForm.patchValue({
      id: communicationEvent.id,
      entryDate: communicationEvent.entryDate ? communicationEvent.entryDate.format(DATE_TIME_FORMAT) : null,
      subject: communicationEvent.subject,
      content: communicationEvent.content,
      fromString: communicationEvent.fromString,
      toString: communicationEvent.toString,
      ccString: communicationEvent.ccString,
      message: communicationEvent.message,
      dateStarted: communicationEvent.dateStarted ? communicationEvent.dateStarted.format(DATE_TIME_FORMAT) : null,
      dateEnded: communicationEvent.dateEnded ? communicationEvent.dateEnded.format(DATE_TIME_FORMAT) : null,
      info: communicationEvent.info,
      communicationEventType: communicationEvent.communicationEventType,
      status: communicationEvent.status,
      contactMechType: communicationEvent.contactMechType,
      contactMechFrom: communicationEvent.contactMechFrom,
      contactMechTo: communicationEvent.contactMechTo,
      fromParty: communicationEvent.fromParty,
      toParty: communicationEvent.toParty,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hrApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const communicationEvent = this.createFromForm();
    if (communicationEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.communicationEventService.update(communicationEvent));
    } else {
      this.subscribeToSaveResponse(this.communicationEventService.create(communicationEvent));
    }
  }

  private createFromForm(): ICommunicationEvent {
    return {
      ...new CommunicationEvent(),
      id: this.editForm.get(['id'])!.value,
      entryDate: this.editForm.get(['entryDate'])!.value ? moment(this.editForm.get(['entryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      subject: this.editForm.get(['subject'])!.value,
      content: this.editForm.get(['content'])!.value,
      fromString: this.editForm.get(['fromString'])!.value,
      toString: this.editForm.get(['toString'])!.value,
      ccString: this.editForm.get(['ccString'])!.value,
      message: this.editForm.get(['message'])!.value,
      dateStarted: this.editForm.get(['dateStarted'])!.value
        ? moment(this.editForm.get(['dateStarted'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateEnded: this.editForm.get(['dateEnded'])!.value ? moment(this.editForm.get(['dateEnded'])!.value, DATE_TIME_FORMAT) : undefined,
      info: this.editForm.get(['info'])!.value,
      communicationEventType: this.editForm.get(['communicationEventType'])!.value,
      status: this.editForm.get(['status'])!.value,
      contactMechType: this.editForm.get(['contactMechType'])!.value,
      contactMechFrom: this.editForm.get(['contactMechFrom'])!.value,
      contactMechTo: this.editForm.get(['contactMechTo'])!.value,
      fromParty: this.editForm.get(['fromParty'])!.value,
      toParty: this.editForm.get(['toParty'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommunicationEvent>>): void {
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
