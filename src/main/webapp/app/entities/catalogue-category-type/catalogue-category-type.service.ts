import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';

type EntityResponseType = HttpResponse<ICatalogueCategoryType>;
type EntityArrayResponseType = HttpResponse<ICatalogueCategoryType[]>;

@Injectable({ providedIn: 'root' })
export class CatalogueCategoryTypeService {
  public resourceUrl = SERVER_API_URL + 'api/catalogue-category-types';

  constructor(protected http: HttpClient) {}

  create(catalogueCategoryType: ICatalogueCategoryType): Observable<EntityResponseType> {
    return this.http.post<ICatalogueCategoryType>(this.resourceUrl, catalogueCategoryType, { observe: 'response' });
  }

  update(catalogueCategoryType: ICatalogueCategoryType): Observable<EntityResponseType> {
    return this.http.put<ICatalogueCategoryType>(this.resourceUrl, catalogueCategoryType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICatalogueCategoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICatalogueCategoryType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
