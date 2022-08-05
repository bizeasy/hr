import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductStoreType } from 'app/shared/model/product-store-type.model';

type EntityResponseType = HttpResponse<IProductStoreType>;
type EntityArrayResponseType = HttpResponse<IProductStoreType[]>;

@Injectable({ providedIn: 'root' })
export class ProductStoreTypeService {
  public resourceUrl = SERVER_API_URL + 'api/product-store-types';

  constructor(protected http: HttpClient) {}

  create(productStoreType: IProductStoreType): Observable<EntityResponseType> {
    return this.http.post<IProductStoreType>(this.resourceUrl, productStoreType, { observe: 'response' });
  }

  update(productStoreType: IProductStoreType): Observable<EntityResponseType> {
    return this.http.put<IProductStoreType>(this.resourceUrl, productStoreType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductStoreType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductStoreType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
