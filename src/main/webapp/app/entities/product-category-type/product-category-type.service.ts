import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductCategoryType } from 'app/shared/model/product-category-type.model';

type EntityResponseType = HttpResponse<IProductCategoryType>;
type EntityArrayResponseType = HttpResponse<IProductCategoryType[]>;

@Injectable({ providedIn: 'root' })
export class ProductCategoryTypeService {
  public resourceUrl = SERVER_API_URL + 'api/product-category-types';

  constructor(protected http: HttpClient) {}

  create(productCategoryType: IProductCategoryType): Observable<EntityResponseType> {
    return this.http.post<IProductCategoryType>(this.resourceUrl, productCategoryType, { observe: 'response' });
  }

  update(productCategoryType: IProductCategoryType): Observable<EntityResponseType> {
    return this.http.put<IProductCategoryType>(this.resourceUrl, productCategoryType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductCategoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductCategoryType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
