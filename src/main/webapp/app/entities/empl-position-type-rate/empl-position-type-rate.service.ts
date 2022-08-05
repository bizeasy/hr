import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';

type EntityResponseType = HttpResponse<IEmplPositionTypeRate>;
type EntityArrayResponseType = HttpResponse<IEmplPositionTypeRate[]>;

@Injectable({ providedIn: 'root' })
export class EmplPositionTypeRateService {
  public resourceUrl = SERVER_API_URL + 'api/empl-position-type-rates';

  constructor(protected http: HttpClient) {}

  create(emplPositionTypeRate: IEmplPositionTypeRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPositionTypeRate);
    return this.http
      .post<IEmplPositionTypeRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emplPositionTypeRate: IEmplPositionTypeRate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPositionTypeRate);
    return this.http
      .put<IEmplPositionTypeRate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmplPositionTypeRate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmplPositionTypeRate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(emplPositionTypeRate: IEmplPositionTypeRate): IEmplPositionTypeRate {
    const copy: IEmplPositionTypeRate = Object.assign({}, emplPositionTypeRate, {
      fromDate:
        emplPositionTypeRate.fromDate && emplPositionTypeRate.fromDate.isValid()
          ? emplPositionTypeRate.fromDate.format(DATE_FORMAT)
          : undefined,
      thruDate:
        emplPositionTypeRate.thruDate && emplPositionTypeRate.thruDate.isValid()
          ? emplPositionTypeRate.thruDate.format(DATE_FORMAT)
          : undefined,
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
      res.body.forEach((emplPositionTypeRate: IEmplPositionTypeRate) => {
        emplPositionTypeRate.fromDate = emplPositionTypeRate.fromDate ? moment(emplPositionTypeRate.fromDate) : undefined;
        emplPositionTypeRate.thruDate = emplPositionTypeRate.thruDate ? moment(emplPositionTypeRate.thruDate) : undefined;
      });
    }
    return res;
  }
}
