import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortProduct } from 'app/shared/model/work-effort-product.model';

type EntityResponseType = HttpResponse<IWorkEffortProduct>;
type EntityArrayResponseType = HttpResponse<IWorkEffortProduct[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortProductService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-products';

  constructor(protected http: HttpClient) {}

  create(workEffortProduct: IWorkEffortProduct): Observable<EntityResponseType> {
    return this.http.post<IWorkEffortProduct>(this.resourceUrl, workEffortProduct, { observe: 'response' });
  }

  update(workEffortProduct: IWorkEffortProduct): Observable<EntityResponseType> {
    return this.http.put<IWorkEffortProduct>(this.resourceUrl, workEffortProduct, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffortProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffortProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
