import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomTimePeriod } from 'app/shared/model/custom-time-period.model';

type EntityResponseType = HttpResponse<ICustomTimePeriod>;
type EntityArrayResponseType = HttpResponse<ICustomTimePeriod[]>;

@Injectable({ providedIn: 'root' })
export class CustomTimePeriodService {
  public resourceUrl = SERVER_API_URL + 'api/custom-time-periods';

  constructor(protected http: HttpClient) {}

  create(customTimePeriod: ICustomTimePeriod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customTimePeriod);
    return this.http
      .post<ICustomTimePeriod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customTimePeriod: ICustomTimePeriod): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customTimePeriod);
    return this.http
      .put<ICustomTimePeriod>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomTimePeriod>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomTimePeriod[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customTimePeriod: ICustomTimePeriod): ICustomTimePeriod {
    const copy: ICustomTimePeriod = Object.assign({}, customTimePeriod, {
      fromDate: customTimePeriod.fromDate && customTimePeriod.fromDate.isValid() ? customTimePeriod.fromDate.toJSON() : undefined,
      thruDate: customTimePeriod.thruDate && customTimePeriod.thruDate.isValid() ? customTimePeriod.thruDate.toJSON() : undefined,
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
      res.body.forEach((customTimePeriod: ICustomTimePeriod) => {
        customTimePeriod.fromDate = customTimePeriod.fromDate ? moment(customTimePeriod.fromDate) : undefined;
        customTimePeriod.thruDate = customTimePeriod.thruDate ? moment(customTimePeriod.thruDate) : undefined;
      });
    }
    return res;
  }
}
