import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyClassificationGroup } from 'app/shared/model/party-classification-group.model';

type EntityResponseType = HttpResponse<IPartyClassificationGroup>;
type EntityArrayResponseType = HttpResponse<IPartyClassificationGroup[]>;

@Injectable({ providedIn: 'root' })
export class PartyClassificationGroupService {
  public resourceUrl = SERVER_API_URL + 'api/party-classification-groups';

  constructor(protected http: HttpClient) {}

  create(partyClassificationGroup: IPartyClassificationGroup): Observable<EntityResponseType> {
    return this.http.post<IPartyClassificationGroup>(this.resourceUrl, partyClassificationGroup, { observe: 'response' });
  }

  update(partyClassificationGroup: IPartyClassificationGroup): Observable<EntityResponseType> {
    return this.http.put<IPartyClassificationGroup>(this.resourceUrl, partyClassificationGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartyClassificationGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartyClassificationGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
