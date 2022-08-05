import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAttendance, Attendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

@Component({
  selector: 'sys-attendance-update',
  templateUrl: './attendance-update.component.html',
})
export class AttendanceUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];

  editForm = this.fb.group({
    id: [],
    punchIn: [],
    punchOut: [],
    employee: [],
  });

  constructor(
    protected attendanceService: AttendanceService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendance }) => {
      if (!attendance.id) {
        const today = moment().startOf('day');
        attendance.punchIn = today;
        attendance.punchOut = today;
      }

      this.updateForm(attendance);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(attendance: IAttendance): void {
    this.editForm.patchValue({
      id: attendance.id,
      punchIn: attendance.punchIn ? attendance.punchIn.format(DATE_TIME_FORMAT) : null,
      punchOut: attendance.punchOut ? attendance.punchOut.format(DATE_TIME_FORMAT) : null,
      employee: attendance.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendance = this.createFromForm();
    if (attendance.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceService.update(attendance));
    } else {
      this.subscribeToSaveResponse(this.attendanceService.create(attendance));
    }
  }

  private createFromForm(): IAttendance {
    return {
      ...new Attendance(),
      id: this.editForm.get(['id'])!.value,
      punchIn: this.editForm.get(['punchIn'])!.value ? moment(this.editForm.get(['punchIn'])!.value, DATE_TIME_FORMAT) : undefined,
      punchOut: this.editForm.get(['punchOut'])!.value ? moment(this.editForm.get(['punchOut'])!.value, DATE_TIME_FORMAT) : undefined,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendance>>): void {
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

  trackById(index: number, item: IParty): any {
    return item.id;
  }
}
