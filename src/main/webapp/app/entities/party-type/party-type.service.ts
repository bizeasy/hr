import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyType } from 'app/shared/model/party-type.model';

type EntityResponseType = HttpResponse<IPartyType>;
type EntityArrayResponseType = HttpResponse<IPartyType[]>;

@Injectable({ providedIn: 'root' })
export class PartyTypeService {
  public resourceUrl = SERVER_API_URL + 'api/party-types';

  constructor(protected http: HttpClient) {}

  create(partyType: IPartyType): Observable<EntityResponseType> {
    return this.http.post<IPartyType>(this.resourceUrl, partyType, { observe: 'response' });
  }

  update(partyType: IPartyType): Observable<EntityResponseType> {
    return this.http.put<IPartyType>(this.resourceUrl, partyType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartyType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartyType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
