import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IStatusValidChange, StatusValidChange } from 'app/shared/model/status-valid-change.model';
import { StatusValidChangeService } from './status-valid-change.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

@Component({
  selector: 'sys-status-valid-change-update',
  templateUrl: './status-valid-change-update.component.html',
})
export class StatusValidChangeUpdateComponent implements OnInit {
  isSaving = false;
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    transitionName: [null, [Validators.maxLength(60)]],
    rules: [],
    status: [],
    statusTo: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected statusValidChangeService: StatusValidChangeService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusValidChange }) => {
      this.updateForm(statusValidChange);

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(statusValidChange: IStatusValidChange): void {
    this.editForm.patchValue({
      id: statusValidChange.id,
      transitionName: statusValidChange.transitionName,
      rules: statusValidChange.rules,
      status: statusValidChange.status,
      statusTo: statusValidChange.statusTo,
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
    const statusValidChange = this.createFromForm();
    if (statusValidChange.id !== undefined) {
      this.subscribeToSaveResponse(this.statusValidChangeService.update(statusValidChange));
    } else {
      this.subscribeToSaveResponse(this.statusValidChangeService.create(statusValidChange));
    }
  }

  private createFromForm(): IStatusValidChange {
    return {
      ...new StatusValidChange(),
      id: this.editForm.get(['id'])!.value,
      transitionName: this.editForm.get(['transitionName'])!.value,
      rules: this.editForm.get(['rules'])!.value,
      status: this.editForm.get(['status'])!.value,
      statusTo: this.editForm.get(['statusTo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusValidChange>>): void {
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

  trackById(index: number, item: IStatus): any {
    return item.id;
  }
}
