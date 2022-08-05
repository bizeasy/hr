import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffortInventoryProduced, WorkEffortInventoryProduced } from 'app/shared/model/work-effort-inventory-produced.model';
import { WorkEffortInventoryProducedService } from './work-effort-inventory-produced.service';
import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IWorkEffort | IInventoryItem | IStatus;

@Component({
  selector: 'sys-work-effort-inventory-produced-update',
  templateUrl: './work-effort-inventory-produced-update.component.html',
})
export class WorkEffortInventoryProducedUpdateComponent implements OnInit {
  isSaving = false;
  workefforts: IWorkEffort[] = [];
  inventoryitems: IInventoryItem[] = [];
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [],
    workEffort: [],
    inventoryItem: [],
    status: [],
  });

  constructor(
    protected workEffortInventoryProducedService: WorkEffortInventoryProducedService,
    protected workEffortService: WorkEffortService,
    protected inventoryItemService: InventoryItemService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortInventoryProduced }) => {
      this.updateForm(workEffortInventoryProduced);

      this.workEffortService.query().subscribe((res: HttpResponse<IWorkEffort[]>) => (this.workefforts = res.body || []));

      this.inventoryItemService.query().subscribe((res: HttpResponse<IInventoryItem[]>) => (this.inventoryitems = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(workEffortInventoryProduced: IWorkEffortInventoryProduced): void {
    this.editForm.patchValue({
      id: workEffortInventoryProduced.id,
      quantity: workEffortInventoryProduced.quantity,
      workEffort: workEffortInventoryProduced.workEffort,
      inventoryItem: workEffortInventoryProduced.inventoryItem,
      status: workEffortInventoryProduced.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffortInventoryProduced = this.createFromForm();
    if (workEffortInventoryProduced.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortInventoryProducedService.update(workEffortInventoryProduced));
    } else {
      this.subscribeToSaveResponse(this.workEffortInventoryProducedService.create(workEffortInventoryProduced));
    }
  }

  private createFromForm(): IWorkEffortInventoryProduced {
    return {
      ...new WorkEffortInventoryProduced(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      workEffort: this.editForm.get(['workEffort'])!.value,
      inventoryItem: this.editForm.get(['inventoryItem'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffortInventoryProduced>>): void {
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
