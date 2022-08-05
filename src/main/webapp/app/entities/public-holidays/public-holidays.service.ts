import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPublicHolidays } from 'app/shared/model/public-holidays.model';

type EntityResponseType = HttpResponse<IPublicHolidays>;
type EntityArrayResponseType = HttpResponse<IPublicHolidays[]>;

@Injectable({ providedIn: 'root' })
export class PublicHolidaysService {
  public resourceUrl = SERVER_API_URL + 'api/public-holidays';

  constructor(protected http: HttpClient) {}

  create(publicHolidays: IPublicHolidays): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicHolidays);
    return this.http
      .post<IPublicHolidays>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(publicHolidays: IPublicHolidays): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(publicHolidays);
    return this.http
      .put<IPublicHolidays>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPublicHolidays>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPublicHolidays[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(publicHolidays: IPublicHolidays): IPublicHolidays {
    const copy: IPublicHolidays = Object.assign({}, publicHolidays, {
      fromDate: publicHolidays.fromDate && publicHolidays.fromDate.isValid() ? publicHolidays.fromDate.format(DATE_FORMAT) : undefined,
      thruDate: publicHolidays.thruDate && publicHolidays.thruDate.isValid() ? publicHolidays.thruDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((publicHolidays: IPublicHolidays) => {
        publicHolidays.fromDate = publicHolidays.fromDate ? moment(publicHolidays.fromDate) : undefined;
        publicHolidays.thruDate = publicHolidays.thruDate ? moment(publicHolidays.thruDate) : undefined;
      });
    }
    return res;
  }
}
