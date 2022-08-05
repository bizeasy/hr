import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQualification } from 'app/shared/model/qualification.model';
import { QualificationService } from './qualification.service';
import { QualificationDeleteDialogComponent } from './qualification-delete-dialog.component';

@Component({
  selector: 'sys-qualification',
  templateUrl: './qualification.component.html',
})
export class QualificationComponent implements OnInit, OnDestroy {
  qualifications?: IQualification[];
  eventSubscriber?: Subscription;

  constructor(
    protected qualificationService: QualificationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.qualificationService.query().subscribe((res: HttpResponse<IQualification[]>) => (this.qualifications = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQualifications();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQualification): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQualifications(): void {
    this.eventSubscriber = this.eventManager.subscribe('qualificationListModification', () => this.loadAll());
  }

  delete(qualification: IQualification): void {
    const modalRef = this.modalService.open(QualificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.qualification = qualification;
  }
}
