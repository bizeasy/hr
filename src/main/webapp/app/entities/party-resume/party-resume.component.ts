import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartyResume } from 'app/shared/model/party-resume.model';
import { PartyResumeService } from './party-resume.service';
import { PartyResumeDeleteDialogComponent } from './party-resume-delete-dialog.component';

@Component({
  selector: 'sys-party-resume',
  templateUrl: './party-resume.component.html',
})
export class PartyResumeComponent implements OnInit, OnDestroy {
  partyResumes?: IPartyResume[];
  eventSubscriber?: Subscription;

  constructor(
    protected partyResumeService: PartyResumeService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.partyResumeService.query().subscribe((res: HttpResponse<IPartyResume[]>) => (this.partyResumes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartyResumes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartyResume): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPartyResumes(): void {
    this.eventSubscriber = this.eventManager.subscribe('partyResumeListModification', () => this.loadAll());
  }

  delete(partyResume: IPartyResume): void {
    const modalRef = this.modalService.open(PartyResumeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partyResume = partyResume;
  }
}
