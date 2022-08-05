import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderItem } from 'app/shared/model/order-item.model';

type EntityResponseType = HttpResponse<IOrderItem>;
type EntityArrayResponseType = HttpResponse<IOrderItem[]>;

@Injectable({ providedIn: 'root' })
export class OrderItemService {
  public resourceUrl = SERVER_API_URL + 'api/order-items';

  constructor(protected http: HttpClient) {}

  create(orderItem: IOrderItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderItem);
    return this.http
      .post<IOrderItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderItem: IOrderItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderItem);
    return this.http
      .put<IOrderItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderItem: IOrderItem): IOrderItem {
    const copy: IOrderItem = Object.assign({}, orderItem, {
      estimatedShipDate:
        orderItem.estimatedShipDate && orderItem.estimatedShipDate.isValid() ? orderItem.estimatedShipDate.toJSON() : undefined,
      estimatedDeliveryDate:
        orderItem.estimatedDeliveryDate && orderItem.estimatedDeliveryDate.isValid() ? orderItem.estimatedDeliveryDate.toJSON() : undefined,
      shipBeforeDate: orderItem.shipBeforeDate && orderItem.shipBeforeDate.isValid() ? orderItem.shipBeforeDate.toJSON() : undefined,
      shipAfterDate: orderItem.shipAfterDate && orderItem.shipAfterDate.isValid() ? orderItem.shipAfterDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.estimatedShipDate = res.body.estimatedShipDate ? moment(res.body.estimatedShipDate) : undefined;
      res.body.estimatedDeliveryDate = res.body.estimatedDeliveryDate ? moment(res.body.estimatedDeliveryDate) : undefined;
      res.body.shipBeforeDate = res.body.shipBeforeDate ? moment(res.body.shipBeforeDate) : undefined;
      res.body.shipAfterDate = res.body.shipAfterDate ? moment(res.body.shipAfterDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderItem: IOrderItem) => {
        orderItem.estimatedShipDate = orderItem.estimatedShipDate ? moment(orderItem.estimatedShipDate) : undefined;
        orderItem.estimatedDeliveryDate = orderItem.estimatedDeliveryDate ? moment(orderItem.estimatedDeliveryDate) : undefined;
        orderItem.shipBeforeDate = orderItem.shipBeforeDate ? moment(orderItem.shipBeforeDate) : undefined;
        orderItem.shipAfterDate = orderItem.shipAfterDate ? moment(orderItem.shipAfterDate) : undefined;
      });
    }
    return res;
  }
}
