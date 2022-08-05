import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPartyResume, PartyResume } from 'app/shared/model/party-resume.model';
import { PartyResumeService } from './party-resume.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

@Component({
  selector: 'sys-party-resume-update',
  templateUrl: './party-resume-update.component.html',
})
export class PartyResumeUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  resumeDateDp: any;

  editForm = this.fb.group({
    id: [],
    text: [],
    resumeDate: [],
    fileAttachment: [],
    fileAttachmentContentType: [],
    attachmentUrl: [],
    mimeType: [],
    party: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected partyResumeService: PartyResumeService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyResume }) => {
      this.updateForm(partyResume);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(partyResume: IPartyResume): void {
    this.editForm.patchValue({
      id: partyResume.id,
      text: partyResume.text,
      resumeDate: partyResume.resumeDate,
      fileAttachment: partyResume.fileAttachment,
      fileAttachmentContentType: partyResume.fileAttachmentContentType,
      attachmentUrl: partyResume.attachmentUrl,
      mimeType: partyResume.mimeType,
      party: partyResume.party,
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
    const partyResume = this.createFromForm();
    if (partyResume.id !== undefined) {
      this.subscribeToSaveResponse(this.partyResumeService.update(partyResume));
    } else {
      this.subscribeToSaveResponse(this.partyResumeService.create(partyResume));
    }
  }

  private createFromForm(): IPartyResume {
    return {
      ...new PartyResume(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      resumeDate: this.editForm.get(['resumeDate'])!.value,
      fileAttachmentContentType: this.editForm.get(['fileAttachmentContentType'])!.value,
      fileAttachment: this.editForm.get(['fileAttachment'])!.value,
      attachmentUrl: this.editForm.get(['attachmentUrl'])!.value,
      mimeType: this.editForm.get(['mimeType'])!.value,
      party: this.editForm.get(['party'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyResume>>): void {
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

  trackById(index: number, item: IParty): any {
    return item.id;
  }
}
