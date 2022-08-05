import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderTerm } from 'app/shared/model/order-term.model';

type EntityResponseType = HttpResponse<IOrderTerm>;
type EntityArrayResponseType = HttpResponse<IOrderTerm[]>;

@Injectable({ providedIn: 'root' })
export class OrderTermService {
  public resourceUrl = SERVER_API_URL + 'api/order-terms';

  constructor(protected http: HttpClient) {}

  create(orderTerm: IOrderTerm): Observable<EntityResponseType> {
    return this.http.post<IOrderTerm>(this.resourceUrl, orderTerm, { observe: 'response' });
  }

  update(orderTerm: IOrderTerm): Observable<EntityResponseType> {
    return this.http.put<IOrderTerm>(this.resourceUrl, orderTerm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderTerm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderTerm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
