import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderTermType } from 'app/shared/model/order-term-type.model';

type EntityResponseType = HttpResponse<IOrderTermType>;
type EntityArrayResponseType = HttpResponse<IOrderTermType[]>;

@Injectable({ providedIn: 'root' })
export class OrderTermTypeService {
  public resourceUrl = SERVER_API_URL + 'api/order-term-types';

  constructor(protected http: HttpClient) {}

  create(orderTermType: IOrderTermType): Observable<EntityResponseType> {
    return this.http.post<IOrderTermType>(this.resourceUrl, orderTermType, { observe: 'response' });
  }

  update(orderTermType: IOrderTermType): Observable<EntityResponseType> {
    return this.http.put<IOrderTermType>(this.resourceUrl, orderTermType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderTermType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderTermType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
