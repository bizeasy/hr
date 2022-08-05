import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPerfReview, PerfReview } from 'app/shared/model/perf-review.model';
import { PerfReviewService } from './perf-review.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IEmplPosition } from 'app/shared/model/empl-position.model';
import { EmplPositionService } from 'app/entities/empl-position/empl-position.service';

type SelectableEntity = IParty | IEmplPosition;

@Component({
  selector: 'sys-perf-review-update',
  templateUrl: './perf-review-update.component.html',
})
export class PerfReviewUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  emplpositions: IEmplPosition[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    comments: [],
    employee: [],
    manager: [],
    emplPosition: [],
  });

  constructor(
    protected perfReviewService: PerfReviewService,
    protected partyService: PartyService,
    protected emplPositionService: EmplPositionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfReview }) => {
      this.updateForm(perfReview);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.emplPositionService.query().subscribe((res: HttpResponse<IEmplPosition[]>) => (this.emplpositions = res.body || []));
    });
  }

  updateForm(perfReview: IPerfReview): void {
    this.editForm.patchValue({
      id: perfReview.id,
      fromDate: perfReview.fromDate,
      thruDate: perfReview.thruDate,
      comments: perfReview.comments,
      employee: perfReview.employee,
      manager: perfReview.manager,
      emplPosition: perfReview.emplPosition,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfReview = this.createFromForm();
    if (perfReview.id !== undefined) {
      this.subscribeToSaveResponse(this.perfReviewService.update(perfReview));
    } else {
      this.subscribeToSaveResponse(this.perfReviewService.create(perfReview));
    }
  }

  private createFromForm(): IPerfReview {
    return {
      ...new PerfReview(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      manager: this.editForm.get(['manager'])!.value,
      emplPosition: this.editForm.get(['emplPosition'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfReview>>): void {
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
