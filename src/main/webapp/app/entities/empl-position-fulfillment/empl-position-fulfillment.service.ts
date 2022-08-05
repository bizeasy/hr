import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';

type EntityResponseType = HttpResponse<IEmplPositionFulfillment>;
type EntityArrayResponseType = HttpResponse<IEmplPositionFulfillment[]>;

@Injectable({ providedIn: 'root' })
export class EmplPositionFulfillmentService {
  public resourceUrl = SERVER_API_URL + 'api/empl-position-fulfillments';

  constructor(protected http: HttpClient) {}

  create(emplPositionFulfillment: IEmplPositionFulfillment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPositionFulfillment);
    return this.http
      .post<IEmplPositionFulfillment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emplPositionFulfillment: IEmplPositionFulfillment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPositionFulfillment);
    return this.http
      .put<IEmplPositionFulfillment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmplPositionFulfillment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmplPositionFulfillment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(emplPositionFulfillment: IEmplPositionFulfillment): IEmplPositionFulfillment {
    const copy: IEmplPositionFulfillment = Object.assign({}, emplPositionFulfillment, {
      fromDate:
        emplPositionFulfillment.fromDate && emplPositionFulfillment.fromDate.isValid()
          ? emplPositionFulfillment.fromDate.format(DATE_FORMAT)
          : undefined,
      thruDate:
        emplPositionFulfillment.thruDate && emplPositionFulfillment.thruDate.isValid()
          ? emplPositionFulfillment.thruDate.format(DATE_FORMAT)
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
      res.body.forEach((emplPositionFulfillment: IEmplPositionFulfillment) => {
        emplPositionFulfillment.fromDate = emplPositionFulfillment.fromDate ? moment(emplPositionFulfillment.fromDate) : undefined;
        emplPositionFulfillment.thruDate = emplPositionFulfillment.thruDate ? moment(emplPositionFulfillment.thruDate) : undefined;
      });
    }
    return res;
  }
}
