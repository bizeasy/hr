import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartyClassificationType } from 'app/shared/model/party-classification-type.model';

type EntityResponseType = HttpResponse<IPartyClassificationType>;
type EntityArrayResponseType = HttpResponse<IPartyClassificationType[]>;

@Injectable({ providedIn: 'root' })
export class PartyClassificationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/party-classification-types';

  constructor(protected http: HttpClient) {}

  create(partyClassificationType: IPartyClassificationType): Observable<EntityResponseType> {
    return this.http.post<IPartyClassificationType>(this.resourceUrl, partyClassificationType, { observe: 'response' });
  }

  update(partyClassificationType: IPartyClassificationType): Observable<EntityResponseType> {
    return this.http.put<IPartyClassificationType>(this.resourceUrl, partyClassificationType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartyClassificationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartyClassificationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
