import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { AttendanceDeleteDialogComponent } from './attendance-delete-dialog.component';

@Component({
  selector: 'sys-attendance',
  templateUrl: './attendance.component.html',
})
export class AttendanceComponent implements OnInit, OnDestroy {
  attendances?: IAttendance[];
  eventSubscriber?: Subscription;

  constructor(protected attendanceService: AttendanceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.attendanceService.query().subscribe((res: HttpResponse<IAttendance[]>) => (this.attendances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttendances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttendances(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendanceListModification', () => this.loadAll());
  }

  delete(attendance: IAttendance): void {
    const modalRef = this.modalService.open(AttendanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attendance = attendance;
  }
}
