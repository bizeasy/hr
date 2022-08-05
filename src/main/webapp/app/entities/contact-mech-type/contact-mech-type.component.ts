import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from './contact-mech-type.service';
import { ContactMechTypeDeleteDialogComponent } from './contact-mech-type-delete-dialog.component';

@Component({
  selector: 'sys-contact-mech-type',
  templateUrl: './contact-mech-type.component.html',
})
export class ContactMechTypeComponent implements OnInit, OnDestroy {
  contactMechTypes?: IContactMechType[];
  eventSubscriber?: Subscription;

  constructor(
    protected contactMechTypeService: ContactMechTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.contactMechTypeService.query().subscribe((res: HttpResponse<IContactMechType[]>) => (this.contactMechTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContactMechTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContactMechType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContactMechTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactMechTypeListModification', () => this.loadAll());
  }

  delete(contactMechType: IContactMechType): void {
    const modalRef = this.modalService.open(ContactMechTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactMechType = contactMechType;
  }
}
