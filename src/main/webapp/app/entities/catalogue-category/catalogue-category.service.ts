import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatalogueCategory } from 'app/shared/model/catalogue-category.model';

type EntityResponseType = HttpResponse<ICatalogueCategory>;
type EntityArrayResponseType = HttpResponse<ICatalogueCategory[]>;

@Injectable({ providedIn: 'root' })
export class CatalogueCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/catalogue-categories';

  constructor(protected http: HttpClient) {}

  create(catalogueCategory: ICatalogueCategory): Observable<EntityResponseType> {
    return this.http.post<ICatalogueCategory>(this.resourceUrl, catalogueCategory, { observe: 'response' });
  }

  update(catalogueCategory: ICatalogueCategory): Observable<EntityResponseType> {
    return this.http.put<ICatalogueCategory>(this.resourceUrl, catalogueCategory, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatalogueCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatalogueCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
