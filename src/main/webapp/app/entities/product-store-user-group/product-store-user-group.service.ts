import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';

type EntityResponseType = HttpResponse<IProductStoreUserGroup>;
type EntityArrayResponseType = HttpResponse<IProductStoreUserGroup[]>;

@Injectable({ providedIn: 'root' })
export class ProductStoreUserGroupService {
  public resourceUrl = SERVER_API_URL + 'api/product-store-user-groups';

  constructor(protected http: HttpClient) {}

  create(productStoreUserGroup: IProductStoreUserGroup): Observable<EntityResponseType> {
    return this.http.post<IProductStoreUserGroup>(this.resourceUrl, productStoreUserGroup, { observe: 'response' });
  }

  update(productStoreUserGroup: IProductStoreUserGroup): Observable<EntityResponseType> {
    return this.http.put<IProductStoreUserGroup>(this.resourceUrl, productStoreUserGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductStoreUserGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductStoreUserGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
