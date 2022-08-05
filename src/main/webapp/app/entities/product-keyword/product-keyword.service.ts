import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductKeyword } from 'app/shared/model/product-keyword.model';

type EntityResponseType = HttpResponse<IProductKeyword>;
type EntityArrayResponseType = HttpResponse<IProductKeyword[]>;

@Injectable({ providedIn: 'root' })
export class ProductKeywordService {
  public resourceUrl = SERVER_API_URL + 'api/product-keywords';

  constructor(protected http: HttpClient) {}

  create(productKeyword: IProductKeyword): Observable<EntityResponseType> {
    return this.http.post<IProductKeyword>(this.resourceUrl, productKeyword, { observe: 'response' });
  }

  update(productKeyword: IProductKeyword): Observable<EntityResponseType> {
    return this.http.put<IProductKeyword>(this.resourceUrl, productKeyword, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductKeyword>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductKeyword[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
