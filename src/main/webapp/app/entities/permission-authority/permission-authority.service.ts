import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPermissionAuthority } from 'app/shared/model/permission-authority.model';

type EntityResponseType = HttpResponse<IPermissionAuthority>;
type EntityArrayResponseType = HttpResponse<IPermissionAuthority[]>;

@Injectable({ providedIn: 'root' })
export class PermissionAuthorityService {
  public resourceUrl = SERVER_API_URL + 'api/permission-authorities';

  constructor(protected http: HttpClient) {}

  create(permissionAuthority: IPermissionAuthority): Observable<EntityResponseType> {
    return this.http.post<IPermissionAuthority>(this.resourceUrl, permissionAuthority, { observe: 'response' });
  }

  update(permissionAuthority: IPermissionAuthority): Observable<EntityResponseType> {
    return this.http.put<IPermissionAuthority>(this.resourceUrl, permissionAuthority, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPermissionAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPermissionAuthority[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
