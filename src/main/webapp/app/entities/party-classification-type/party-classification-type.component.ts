import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyClassificationType } from 'app/shared/model/party-classification-type.model';
import { PartyClassificationTypeService } from './party-classification-type.service';
import { PartyClassificationTypeDeleteDialogComponent } from './party-classification-type-delete-dialog.component';

@Component({
  selector: 'sys-party-classification-type',
  templateUrl: './party-classification-type.component.html',
})
export class PartyClassificationTypeComponent implements OnInit, OnDestroy {
  partyClassificationTypes?: IPartyClassificationType[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyClassificationTypeService: PartyClassificationTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyClassificationTypeService
      .query()
      .subscribe((res: HttpResponse<IPartyClassificationType[]>) => (this.partyClassificationTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyClassificationTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyClassificationType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyClassificationTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyClassificationTypeListModification', () => this.loadAll());
  }

  delete(partyClassificationType: IPartyClassificationType): void {
    const modalRef = this.modalService.open(PartyClassificationTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyClassificationType = partyClassificationType;
  }
}
