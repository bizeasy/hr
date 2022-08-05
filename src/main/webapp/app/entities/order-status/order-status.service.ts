import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderStatus } from 'app/shared/model/order-status.model';

type EntityResponseType = HttpResponse<IOrderStatus>;
type EntityArrayResponseType = HttpResponse<IOrderStatus[]>;

@Injectable({ providedIn: 'root' })
export class OrderStatusService {
  public resourceUrl = SERVER_API_URL + 'api/order-statuses';

  constructor(protected http: HttpClient) {}

  create(orderStatus: IOrderStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderStatus);
    return this.http
      .post<IOrderStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderStatus: IOrderStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderStatus);
    return this.http
      .put<IOrderStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderStatus: IOrderStatus): IOrderStatus {
    const copy: IOrderStatus = Object.assign({}, orderStatus, {
      statusDateTime: orderStatus.statusDateTime && orderStatus.statusDateTime.isValid() ? orderStatus.statusDateTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.statusDateTime = res.body.statusDateTime ? moment(res.body.statusDateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderStatus: IOrderStatus) => {
        orderStatus.statusDateTime = orderStatus.statusDateTime ? moment(orderStatus.statusDateTime) : undefined;
      });
    }
    return res;
  }
}
