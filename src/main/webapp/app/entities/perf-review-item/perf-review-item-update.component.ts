import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPerfReviewItem, PerfReviewItem } from 'app/shared/model/perf-review-item.model';
import { PerfReviewItemService } from './perf-review-item.service';
import { IPerfReview } from 'app/shared/model/perf-review.model';
import { PerfReviewService } from 'app/entities/perf-review/perf-review.service';
import { IPerfRatingType } from 'app/shared/model/perf-rating-type.model';
import { PerfRatingTypeService } from 'app/entities/perf-rating-type/perf-rating-type.service';
import { IPerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';
import { PerfReviewItemTypeService } from 'app/entities/perf-review-item-type/perf-review-item-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

type SelectableEntity = IPerfReview | IPerfRatingType | IPerfReviewItemType | IParty;

@Component({
  selector: 'sys-perf-review-item-update',
  templateUrl: './perf-review-item-update.component.html',
})
export class PerfReviewItemUpdateComponent implements OnInit {
  isSaving = false;
  perfreviews: IPerfReview[] = [];
  perfratingtypes: IPerfRatingType[] = [];
  perfreviewitemtypes: IPerfReviewItemType[] = [];
  parties: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    comments: [],
    perfReview: [],
    perfRatingType: [],
    type: [],
    employee: [],
  });

  constructor(
    protected perfReviewItemService: PerfReviewItemService,
    protected perfReviewService: PerfReviewService,
    protected perfRatingTypeService: PerfRatingTypeService,
    protected perfReviewItemTypeService: PerfReviewItemTypeService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfReviewItem }) => {
      this.updateForm(perfReviewItem);

      this.perfReviewService.query().subscribe((res: HttpResponse<IPerfReview[]>) => (this.perfreviews = res.body || []));

      this.perfRatingTypeService.query().subscribe((res: HttpResponse<IPerfRatingType[]>) => (this.perfratingtypes = res.body || []));

      this.perfReviewItemTypeService
        .query()
        .subscribe((res: HttpResponse<IPerfReviewItemType[]>) => (this.perfreviewitemtypes = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(perfReviewItem: IPerfReviewItem): void {
    this.editForm.patchValue({
      id: perfReviewItem.id,
      sequenceNo: perfReviewItem.sequenceNo,
      comments: perfReviewItem.comments,
      perfReview: perfReviewItem.perfReview,
      perfRatingType: perfReviewItem.perfRatingType,
      type: perfReviewItem.type,
      employee: perfReviewItem.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfReviewItem = this.createFromForm();
    if (perfReviewItem.id !== undefined) {
      this.subscribeToSaveResponse(this.perfReviewItemService.update(perfReviewItem));
    } else {
      this.subscribeToSaveResponse(this.perfReviewItemService.create(perfReviewItem));
    }
  }

  private createFromForm(): IPerfReviewItem {
    return {
      ...new PerfReviewItem(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      perfReview: this.editForm.get(['perfReview'])!.value,
      perfRatingType: this.editForm.get(['perfRatingType'])!.value,
      type: this.editForm.get(['type'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfReviewItem>>): void {
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
