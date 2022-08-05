import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderItemType } from 'app/shared/model/order-item-type.model';

type EntityResponseType = HttpResponse<IOrderItemType>;
type EntityArrayResponseType = HttpResponse<IOrderItemType[]>;

@Injectable({ providedIn: 'root' })
export class OrderItemTypeService {
  public resourceUrl = SERVER_API_URL + 'api/order-item-types';

  constructor(protected http: HttpClient) {}

  create(orderItemType: IOrderItemType): Observable<EntityResponseType> {
    return this.http.post<IOrderItemType>(this.resourceUrl, orderItemType, { observe: 'response' });
  }

  update(orderItemType: IOrderItemType): Observable<EntityResponseType> {
    return this.http.put<IOrderItemType>(this.resourceUrl, orderItemType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderItemType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderItemType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
