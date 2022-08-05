import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyClassificationGroup } from 'app/shared/model/party-classification-group.model';
import { PartyClassificationGroupService } from './party-classification-group.service';
import { PartyClassificationGroupDeleteDialogComponent } from './party-classification-group-delete-dialog.component';

@Component({
  selector: 'sys-party-classification-group',
  templateUrl: './party-classification-group.component.html',
})
export class PartyClassificationGroupComponent implements OnInit, OnDestroy {
  partyClassificationGroups?: IPartyClassificationGroup[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyClassificationGroupService: PartyClassificationGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyClassificationGroupService
      .query()
      .subscribe((res: HttpResponse<IPartyClassificationGroup[]>) => (this.partyClassificationGroups = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyClassificationGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyClassificationGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartyClassificationGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyClassificationGroupListModification', () => this.loadAll());
  }

  delete(partyClassificationGroup: IPartyClassificationGroup): void {
    const modalRef = this.modalService.open(PartyClassificationGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyClassificationGroup = partyClassificationGroup;
  }
}
