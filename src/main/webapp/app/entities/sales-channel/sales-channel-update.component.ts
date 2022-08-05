import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISalesChannel, SalesChannel } from 'app/shared/model/sales-channel.model';
import { SalesChannelService } from './sales-channel.service';

@Component({
  selector: 'sys-sales-channel-update',
  templateUrl: './sales-channel-update.component.html',
})
export class SalesChannelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(100)]],
    sequenceNo: [],
  });

  constructor(protected salesChannelService: SalesChannelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesChannel }) => {
      this.updateForm(salesChannel);
    });
  }

  updateForm(salesChannel: ISalesChannel): void {
    this.editForm.patchValue({
      id: salesChannel.id,
      name: salesChannel.name,
      description: salesChannel.description,
      sequenceNo: salesChannel.sequenceNo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salesChannel = this.createFromForm();
    if (salesChannel.id !== undefined) {
      this.subscribeToSaveResponse(this.salesChannelService.update(salesChannel));
    } else {
      this.subscribeToSaveResponse(this.salesChannelService.create(salesChannel));
    }
  }

  private createFromForm(): ISalesChannel {
    return {
      ...new SalesChannel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalesChannel>>): void {
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
