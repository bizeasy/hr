import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { WorkEffortInventoryAssignService } from './work-effort-inventory-assign.service';
import { WorkEffortInventoryAssignDeleteDialogComponent } from './work-effort-inventory-assign-delete-dialog.component';

@Component({
  selector: 'sys-work-effort-inventory-assign',
  templateUrl: './work-effort-inventory-assign.component.html',
})
export class WorkEffortInventoryAssignComponent implements OnInit, OnDestroy {
  workEffortInventoryAssigns?: IWorkEffortInventoryAssign[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected workEffortInventoryAssignService: WorkEffortInventoryAssignService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.workEffortInventoryAssignService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IWorkEffortInventoryAssign[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInWorkEffortInventoryAssigns();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkEffortInventoryAssign): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkEffortInventoryAssigns(): void {
    this.eventSubscriber = this.eventManager.subscribe('workEffortInventoryAssignListModification', () => this.loadPage());
  }

  delete(workEffortInventoryAssign: IWorkEffortInventoryAssign): void {
    const modalRef = this.modalService.open(WorkEffortInventoryAssignDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workEffortInventoryAssign = workEffortInventoryAssign;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IWorkEffortInventoryAssign[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/work-effort-inventory-assign'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.workEffortInventoryAssigns = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}