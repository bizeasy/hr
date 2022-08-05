import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';
import { ContactMechPurposeService } from './contact-mech-purpose.service';
import { ContactMechPurposeDeleteDialogComponent } from './contact-mech-purpose-delete-dialog.component';

@Component({
  selector: 'sys-contact-mech-purpose',
  templateUrl: './contact-mech-purpose.component.html',
})
export class ContactMechPurposeComponent implements OnInit, OnDestroy {
  contactMechPurposes?: IContactMechPurpose[];
  eventSubscriber?: Subscription;

  constructor(
    protected contactMechPurposeService: ContactMechPurposeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.contactMechPurposeService
      .query()
      .subscribe((res: HttpResponse<IContactMechPurpose[]>) => (this.contactMechPurposes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContactMechPurposes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContactMechPurpose): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContactMechPurposes(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactMechPurposeListModification', () => this.loadAll());
  }

  delete(contactMechPurpose: IContactMechPurpose): void {
    const modalRef = this.modalService.open(ContactMechPurposeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactMechPurpose = contactMechPurpose;
  }
}
