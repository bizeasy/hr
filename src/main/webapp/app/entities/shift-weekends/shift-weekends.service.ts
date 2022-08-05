import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShiftWeekends } from 'app/shared/model/shift-weekends.model';

type EntityResponseType = HttpResponse<IShiftWeekends>;
type EntityArrayResponseType = HttpResponse<IShiftWeekends[]>;

@Injectable({ providedIn: 'root' })
export class ShiftWeekendsService {
  public resourceUrl = SERVER_API_URL + 'api/shift-weekends';

  constructor(protected http: HttpClient) {}

  create(shiftWeekends: IShiftWeekends): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shiftWeekends);
    return this.http
      .post<IShiftWeekends>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(shiftWeekends: IShiftWeekends): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(shiftWeekends);
    return this.http
      .put<IShiftWeekends>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IShiftWeekends>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IShiftWeekends[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(shiftWeekends: IShiftWeekends): IShiftWeekends {
    const copy: IShiftWeekends = Object.assign({}, shiftWeekends, {
      fromDate: shiftWeekends.fromDate && shiftWeekends.fromDate.isValid() ? shiftWeekends.fromDate.format(DATE_FORMAT) : undefined,
      thruDate: shiftWeekends.thruDate && shiftWeekends.thruDate.isValid() ? shiftWeekends.thruDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.thruDate = res.body.thruDate ? moment(res.body.thruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((shiftWeekends: IShiftWeekends) => {
        shiftWeekends.fromDate = shiftWeekends.fromDate ? moment(shiftWeekends.fromDate) : undefined;
        shiftWeekends.thruDate = shiftWeekends.thruDate ? moment(shiftWeekends.thruDate) : undefined;
      });
    }
    return res;
  }
}
