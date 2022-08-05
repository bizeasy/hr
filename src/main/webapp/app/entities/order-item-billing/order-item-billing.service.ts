import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderItemBilling } from 'app/shared/model/order-item-billing.model';

type EntityResponseType = HttpResponse<IOrderItemBilling>;
type EntityArrayResponseType = HttpResponse<IOrderItemBilling[]>;

@Injectable({ providedIn: 'root' })
export class OrderItemBillingService {
  public resourceUrl = SERVER_API_URL + 'api/order-item-billings';

  constructor(protected http: HttpClient) {}

  create(orderItemBilling: IOrderItemBilling): Observable<EntityResponseType> {
    return this.http.post<IOrderItemBilling>(this.resourceUrl, orderItemBilling, { observe: 'response' });
  }

  update(orderItemBilling: IOrderItemBilling): Observable<EntityResponseType> {
    return this.http.put<IOrderItemBilling>(this.resourceUrl, orderItemBilling, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderItemBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderItemBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
