import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductPricePurpose } from 'app/shared/model/product-price-purpose.model';

type EntityResponseType = HttpResponse<IProductPricePurpose>;
type EntityArrayResponseType = HttpResponse<IProductPricePurpose[]>;

@Injectable({ providedIn: 'root' })
export class ProductPricePurposeService {
  public resourceUrl = SERVER_API_URL + 'api/product-price-purposes';

  constructor(protected http: HttpClient) {}

  create(productPricePurpose: IProductPricePurpose): Observable<EntityResponseType> {
    return this.http.post<IProductPricePurpose>(this.resourceUrl, productPricePurpose, { observe: 'response' });
  }

  update(productPricePurpose: IProductPricePurpose): Observable<EntityResponseType> {
    return this.http.put<IProductPricePurpose>(this.resourceUrl, productPricePurpose, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductPricePurpose>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductPricePurpose[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
