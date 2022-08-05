import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductPriceType } from 'app/shared/model/product-price-type.model';

type EntityResponseType = HttpResponse<IProductPriceType>;
type EntityArrayResponseType = HttpResponse<IProductPriceType[]>;

@Injectable({ providedIn: 'root' })
export class ProductPriceTypeService {
  public resourceUrl = SERVER_API_URL + 'api/product-price-types';

  constructor(protected http: HttpClient) {}

  create(productPriceType: IProductPriceType): Observable<EntityResponseType> {
    return this.http.post<IProductPriceType>(this.resourceUrl, productPriceType, { observe: 'response' });
  }

  update(productPriceType: IProductPriceType): Observable<EntityResponseType> {
    return this.http.put<IProductPriceType>(this.resourceUrl, productPriceType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductPriceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductPriceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
