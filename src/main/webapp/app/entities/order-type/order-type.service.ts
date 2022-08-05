import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderType } from 'app/shared/model/order-type.model';

type EntityResponseType = HttpResponse<IOrderType>;
type EntityArrayResponseType = HttpResponse<IOrderType[]>;

@Injectable({ providedIn: 'root' })
export class OrderTypeService {
  public resourceUrl = SERVER_API_URL + 'api/order-types';

  constructor(protected http: HttpClient) {}

  create(orderType: IOrderType): Observable<EntityResponseType> {
    return this.http.post<IOrderType>(this.resourceUrl, orderType, { observe: 'response' });
  }

  update(orderType: IOrderType): Observable<EntityResponseType> {
    return this.http.put<IOrderType>(this.resourceUrl, orderType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
