import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserGroupAuthority } from 'app/shared/model/user-group-authority.model';

type EntityResponseType = HttpResponse<IUserGroupAuthority>;
type EntityArrayResponseType = HttpResponse<IUserGroupAuthority[]>;

@Injectable({ providedIn: 'root' })
export class UserGroupAuthorityService {
  public resourceUrl = SERVER_API_URL + 'api/user-group-authorities';

  constructor(protected http: HttpClient) {}

  create(userGroupAuthority: IUserGroupAuthority): Observable<EntityResponseType> {
    return this.http.post<IUserGroupAuthority>(this.resourceUrl, userGroupAuthority, { observe: 'response' });
  }

  update(userGroupAuthority: IUserGroupAuthority): Observable<EntityResponseType> {
    return this.http.put<IUserGroupAuthority>(this.resourceUrl, userGroupAuthority, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserGroupAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserGroupAuthority[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
