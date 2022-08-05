import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderContactMech } from 'app/shared/model/order-contact-mech.model';

type EntityResponseType = HttpResponse<IOrderContactMech>;
type EntityArrayResponseType = HttpResponse<IOrderContactMech[]>;

@Injectable({ providedIn: 'root' })
export class OrderContactMechService {
  public resourceUrl = SERVER_API_URL + 'api/order-contact-meches';

  constructor(protected http: HttpClient) {}

  create(orderContactMech: IOrderContactMech): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderContactMech);
    return this.http
      .post<IOrderContactMech>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderContactMech: IOrderContactMech): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderContactMech);
    return this.http
      .put<IOrderContactMech>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderContactMech>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderContactMech[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderContactMech: IOrderContactMech): IOrderContactMech {
    const copy: IOrderContactMech = Object.assign({}, orderContactMech, {
      fromDate: orderContactMech.fromDate && orderContactMech.fromDate.isValid() ? orderContactMech.fromDate.toJSON() : undefined,
      thruDate: orderContactMech.thruDate && orderContactMech.thruDate.isValid() ? orderContactMech.thruDate.toJSON() : undefined,
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
      res.body.forEach((orderContactMech: IOrderContactMech) => {
        orderContactMech.fromDate = orderContactMech.fromDate ? moment(orderContactMech.fromDate) : undefined;
        orderContactMech.thruDate = orderContactMech.thruDate ? moment(orderContactMech.thruDate) : undefined;
      });
    }
    return res;
  }
}
