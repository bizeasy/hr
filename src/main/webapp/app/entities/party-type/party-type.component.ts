import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyType } from 'app/shared/model/party-type.model';
import { PartyTypeService } from './party-type.service';
import { PartyTypeDeleteDialogComponent } from './party-type-delete-dialog.component';

@Component({
  selector: 'sys-party-type',
  templateUrl: './party-type.component.html',
})
export class PartyTypeComponent implements OnInit, OnDestroy {
  partyTypes?: IPartyType[];
  eventSubscriber?: Subscription;

  constructor(protected partyTypeService: PartyTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.partyTypeService.query().subscribe((res: HttpResponse<IPartyType[]>) => (this.partyTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyTypeListModification', () => this.loadAll());
  }

  delete(partyType: IPartyType): void {
    const modalRef = this.modalService.open(PartyTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyType = partyType;
  }
}
