import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyRole } from 'app/shared/model/party-role.model';

type EntityResponseType = HttpResponse<IPartyRole>;
type EntityArrayResponseType = HttpResponse<IPartyRole[]>;

@Injectable({ providedIn: 'root' })
export class PartyRoleService {
  public resourceUrl = SERVER_API_URL + 'api/party-roles';

  constructor(protected http: HttpClient) {}

  create(partyRole: IPartyRole): Observable<EntityResponseType> {
    return this.http.post<IPartyRole>(this.resourceUrl, partyRole, { observe: 'response' });
  }

  update(partyRole: IPartyRole): Observable<EntityResponseType> {
    return this.http.put<IPartyRole>(this.resourceUrl, partyRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartyRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartyRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
